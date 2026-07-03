from typing import Any

from fastapi import APIRouter, Depends, Query

from app.api.deps import get_nhl_service
from app.schemas import PlayerDetail, PlayerSearchResult
from app.services import NHLService

router = APIRouter(tags=["NHL"])


@router.get("/players/search", response_model=list[PlayerSearchResult])
async def search_players(
    q: str = Query(..., min_length=1, description="Player name query"),
    limit: int = Query(10, ge=1, le=50),
    service: NHLService = Depends(get_nhl_service),
) -> list[PlayerSearchResult]:
    results = await service.search_players(q, limit=limit)
    return [PlayerSearchResult.from_search(item) for item in results]


@router.get("/players/{player_id}", response_model=PlayerDetail)
async def get_player(
    player_id: int,
    service: NHLService = Depends(get_nhl_service),
) -> PlayerDetail:
    payload = await service.get_player(player_id)
    return PlayerDetail.from_landing(payload)


@router.get("/teams")
async def list_teams(
    service: NHLService = Depends(get_nhl_service),
) -> dict[str, Any]:
    return await service.list_teams()


@router.get("/teams/{team}/roster")
async def get_roster(
    team: str,
    season: str = Query("current"),
    service: NHLService = Depends(get_nhl_service),
) -> dict[str, Any]:
    return await service.get_roster(team, season=season)


@router.get("/standings")
async def get_standings(
    date: str = Query("now"),
    service: NHLService = Depends(get_nhl_service),
) -> dict[str, Any]:
    return await service.get_standings(date=date)


@router.get("/stats/skater-leaders")
async def get_skater_leaders(
    categories: str | None = Query(None),
    limit: int = Query(5, ge=1, le=50),
    service: NHLService = Depends(get_nhl_service),
) -> dict[str, Any]:
    return await service.get_skater_leaders(categories=categories, limit=limit)
