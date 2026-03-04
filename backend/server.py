import uvicorn
from app.main import create_app
from app.core.config import get_settings


settings = get_settings()
app = create_app()

if __name__ == "__main__":
    uvicorn.run(
        "app.main:app",  
        host=settings.API_HOST,
        port=settings.API_PORT,
        reload=settings.DEBUG,
        log_level="info",
    )