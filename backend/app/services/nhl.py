from __future__ import annotations

from typing import Any

import httpx

from app.config import Settings


class NHLAPIError(Exception):
    """Raised when an upstream NHL API request fails.

    ``status`` is the HTTP status the router should return to the client.
    """

    def __init__(self, status: int, detail: str) -> None:
        super().__init__(detail)
        self.status = status
        self.detail = detail


class NHLService:
    """Thin async client over the (undocumented) NHL API.

    The NHL API is spread across three hosts, so each method targets the
    appropriate base URL from settings. This is a live passthrough — no caching
    or persistence.
    """

    def __init__(self, client: httpx.AsyncClient, settings: Settings) -> None:
        self._client = client
        self._api_base = settings.NHL_API_BASE_URL
        self._search_base = settings.NHL_SEARCH_API_BASE_URL
        self._stats_base = settings.NHL_STATS_API_BASE_URL

    async def _get(
        self, base: str, path: str, params: dict[str, Any] | None = None
    ) -> Any:
        url = base.rstrip("/") + "/" + path.lstrip("/")
        try:
            response = await self._client.get(url, params=params)
            response.raise_for_status()
        except httpx.HTTPStatusError as exc:
            status = 404 if exc.response.status_code == 404 else 502
            raise NHLAPIError(status, f"NHL API error for {path}") from exc
        except (httpx.TimeoutException, httpx.RequestError) as exc:
            raise NHLAPIError(504, f"NHL API unreachable for {path}") from exc
        return response.json()

    async def search_players(self, query: str, limit: int = 10) -> list[dict[str, Any]]:
        return await self._get(
            self._search_base,
            "search/player",
            {"culture": "en-us", "limit": limit, "q": query},
        )

    async def get_player(self, player_id: int) -> dict[str, Any]:
        return await self._get(self._api_base, f"player/{player_id}/landing")

    async def list_teams(self) -> dict[str, Any]:
        return await self._get(self._stats_base, "team")

    async def get_roster(self, team: str, season: str = "current") -> dict[str, Any]:
        return await self._get(self._api_base, f"roster/{team}/{season}")

    async def get_standings(self, date: str = "now") -> dict[str, Any]:
        return await self._get(self._api_base, f"standings/{date}")

    async def get_skater_leaders(
        self, categories: str | None = None, limit: int = 5
    ) -> dict[str, Any]:
        params: dict[str, Any] = {"limit": limit}
        if categories:
            params["categories"] = categories
        return await self._get(
            self._api_base, "skater-stats-leaders/current", params
        )
