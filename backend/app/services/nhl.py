from __future__ import annotations

from typing import Any

from app.config import get_settings

settings = get_settings()


class NHLService:
    BASE_URL: str = settings.NHL_API_BASE_URL

    def get_player(self) -> dict[str, Any]:
        raise NotImplementedError("NHLService.get_player is not implemented yet")
