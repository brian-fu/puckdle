from fastapi import Request

from app.config import get_settings
from app.services import NHLService


def get_nhl_service(request: Request) -> NHLService:
    """Build an NHLService bound to the shared httpx client from app state."""
    return NHLService(request.app.state.nhl_client, get_settings())
