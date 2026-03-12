from functools import lru_cache
from pathlib import Path
from typing import List

from pydantic_settings import BaseSettings, SettingsConfigDict

ROOT_DIR = Path(__file__).resolve().parent.parent.parent
ENV_FILE_PATH = ROOT_DIR / ".env"

class Settings(BaseSettings):
    """Application settings."""

    DATABASE_URL: str
    API_HOST: str 
    API_PORT: int 
    DEBUG: bool 
    SECRET_KEY: str
    CORS_ORIGINS: List[str] 
    ALGORITHM: str 
    ACCESS_TOKEN_EXPIRE_MINUTES: int 

    model_config = SettingsConfigDict(
        env_file=str(ENV_FILE_PATH), 
        case_sensitive=True, 
        extra="ignore"
    )

@lru_cache()
def get_settings() -> Settings:
    """Get cached settings instance."""
    return Settings()
