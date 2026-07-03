from contextlib import asynccontextmanager

import httpx
from fastapi import FastAPI, Request
from fastapi.middleware.cors import CORSMiddleware
from fastapi.responses import JSONResponse
from sqlalchemy import text

from app.api.nhl import router as nhl_router
from app.config import get_settings
from app.database import engine
from app.services import NHLAPIError

settings = get_settings()

@asynccontextmanager
async def lifespan(app: FastAPI):
    # Startup execution
    # Verify database connectivity before serving traffic.
    with engine.connect() as conn:
        conn.execute(text("SELECT 1"))

    # Shared HTTP client for the NHL API (connection pooling, reused per request).
    # follow_redirects: the NHL API 307-redirects "now" aliases (e.g. standings/now).
    app.state.nhl_client = httpx.AsyncClient(
        timeout=settings.NHL_API_TIMEOUT, follow_redirects=True
    )

    host = "localhost" if settings.API_HOST == "0.0.0.0" else settings.API_HOST
    docs_url = f"http://{host}:{settings.API_PORT}/docs"

    print("-" * 40)
    print("Puckdle API Started")
    print("Database: connected")
    print(f"Swagger UI: {docs_url}")
    print("-" * 40)

    yield
    # Teardown execution
    # Release pooled connections.
    await app.state.nhl_client.aclose()
    engine.dispose()
    print("Application teardown executing...")

def create_app() -> FastAPI:

    app = FastAPI(
        title="Puckdle",
        description="REST API for Puckdle application",
        version="1.0.0",
        lifespan=lifespan
    )

    # CORS Configuration
    app.add_middleware(
        CORSMiddleware,
        allow_origins=settings.CORS_ORIGINS,
        allow_credentials=True,
        allow_methods=["*"],
        allow_headers=["*"]
    )

    # Map upstream NHL API failures to clean HTTP responses.
    @app.exception_handler(NHLAPIError)
    async def nhl_api_error_handler(request: Request, exc: NHLAPIError):
        return JSONResponse(status_code=exc.status, content={"detail": exc.detail})

    # Include Routers
    app.include_router(nhl_router, prefix="/api")

    # Health Check
    @app.get("/health", tags=["System"])
    async def health_check():
        return {"status": "healthy"}

    return app

app = create_app()

