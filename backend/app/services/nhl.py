from __future__ import annotations

import json
from typing import Any

from backend.app.main import settings


class NHLService:
    BASE_URL: str = settings.NHL_API_BASE_URL

    def get_player(self, ) -> dict[str, Any]:
        
