from __future__ import annotations

from typing import Any

from pydantic import BaseModel


class PlayerSearchResult(BaseModel):
    """One entry from the NHL player search (search.d3.nhle.com)."""

    player_id: str
    name: str
    position: str | None = None
    team_abbrev: str | None = None
    active: bool | None = None
    height_in_inches: int | None = None
    weight_in_pounds: int | None = None
    birth_country: str | None = None

    @classmethod
    def from_search(cls, item: dict[str, Any]) -> PlayerSearchResult:
        return cls(
            player_id=item.get("playerId"),
            name=item.get("name"),
            position=item.get("positionCode"),
            team_abbrev=item.get("teamAbbrev"),
            active=item.get("active"),
            height_in_inches=item.get("heightInInches"),
            weight_in_pounds=item.get("weightInPounds"),
            birth_country=item.get("birthCountry"),
        )


class PlayerDetail(BaseModel):
    """Flattened bio from the NHL player landing endpoint."""

    player_id: int
    first_name: str | None = None
    last_name: str | None = None
    position: str | None = None
    sweater_number: int | None = None
    team_abbrev: str | None = None
    birth_date: str | None = None
    birth_country: str | None = None
    height_in_inches: int | None = None
    weight_in_pounds: int | None = None

    @classmethod
    def from_landing(cls, payload: dict[str, Any]) -> PlayerDetail:
        def localized(value: Any) -> str | None:
            return value.get("default") if isinstance(value, dict) else value

        return cls(
            player_id=payload["playerId"],
            first_name=localized(payload.get("firstName")),
            last_name=localized(payload.get("lastName")),
            position=payload.get("position"),
            sweater_number=payload.get("sweaterNumber"),
            team_abbrev=payload.get("currentTeamAbbrev"),
            birth_date=payload.get("birthDate"),
            birth_country=payload.get("birthCountry"),
            height_in_inches=payload.get("heightInInches"),
            weight_in_pounds=payload.get("weightInPounds"),
        )
