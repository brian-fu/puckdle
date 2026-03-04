import uvicorn

from contextlib import asynccontextmanager
from fastapi import FastAPI, Request
from fastapi.middleware.cors import CORSMiddleware

from backend.app.config import get_settings

settings = get_settings()

@asynccontextmanager
async def lifespan(app: FastAPI):
    # Startup execution
    # Initialize database connection pools & ML models 
    host = "localhost" if settings.API_HOST == "0.0.0.0" else settings.API_HOST
    docs_url = f"http://{host}:{settings.API_PORT}/docs"
    
    print("-" * 40)
    print(f"Puckdle API Started")
    print(f"Swagger UI: {docs_url}")
    print("-" * 40)
    
    yield
    # Teardown execution
    # Close database connections
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

    # Include Routers

    # Health Check 
    @app.get("/health", tags=["System"])
    async def health_check():
        return {"status": "healthy"}
    
    return app

app = create_app()

