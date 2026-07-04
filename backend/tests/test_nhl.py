import httpx
from fastapi.testclient import TestClient

from app.api.deps import get_nhl_service
from app.config import get_settings
from app.main import app
from app.services import NHLService

client = TestClient(app)

SEARCH_FIXTURE = [
    {
        "playerId": "8478402",
        "name": "Connor McDavid",
        "positionCode": "C",
        "teamAbbrev": "EDM",
        "active": True,
        "heightInInches": 73,
        "weightInPounds": 193,
        "birthCountry": "CAN",
    }
]

LANDING_FIXTURE = {
    "playerId": 8478402,
    "firstName": {"default": "Connor"},
    "lastName": {"default": "McDavid"},
    "position": "C",
    "sweaterNumber": 97,
    "currentTeamAbbrev": "EDM",
    "isActive": True,
    "birthDate": "1997-01-13",
    "birthCountry": "CAN",
    "heightInInches": 73,
    "weightInPounds": 193,
}

STANDINGS_FIXTURE = {
    "standings": [
        {
            "teamAbbrev": {"default": "COL"},
            "teamName": {"default": "Colorado Avalanche"},
            "teamCommonName": {"default": "Avalanche"},
            "conferenceName": "Western",
            "divisionName": "Central",
            "teamLogo": "https://assets.nhle.com/logos/nhl/svg/COL_light.svg",
        }
    ]
}


def _use_handler(handler) -> None:
    """Point the NHL service at an httpx MockTransport driven by `handler`."""

    def factory() -> NHLService:
        mock_client = httpx.AsyncClient(transport=httpx.MockTransport(handler))
        return NHLService(mock_client, get_settings())

    app.dependency_overrides[get_nhl_service] = factory


def teardown_function(_) -> None:
    app.dependency_overrides.clear()


def test_search_players_maps_results() -> None:
    _use_handler(lambda request: httpx.Response(200, json=SEARCH_FIXTURE))

    response = client.get("/api/players/search", params={"q": "mcdavid"})

    assert response.status_code == 200
    body = response.json()
    assert body == [
        {
            "player_id": "8478402",
            "name": "Connor McDavid",
            "position": "C",
            "team_abbrev": "EDM",
            "active": True,
            "height_in_inches": 73,
            "weight_in_pounds": 193,
            "birth_country": "CAN",
        }
    ]


def test_search_active_flag_forwards_upstream_param() -> None:
    captured: dict[str, str | None] = {}

    def handler(request: httpx.Request) -> httpx.Response:
        captured["active"] = request.url.params.get("active")
        return httpx.Response(200, json=SEARCH_FIXTURE)

    _use_handler(handler)

    response = client.get(
        "/api/players/search", params={"q": "mcdavid", "active": True}
    )

    assert response.status_code == 200
    assert captured["active"] == "true"


def test_search_without_active_omits_upstream_param() -> None:
    captured: dict[str, str | None] = {}

    def handler(request: httpx.Request) -> httpx.Response:
        captured["active"] = request.url.params.get("active")
        return httpx.Response(200, json=SEARCH_FIXTURE)

    _use_handler(handler)

    response = client.get("/api/players/search", params={"q": "mcdavid"})

    assert response.status_code == 200
    assert captured["active"] is None


def test_get_player_flattens_landing() -> None:
    _use_handler(lambda request: httpx.Response(200, json=LANDING_FIXTURE))

    response = client.get("/api/players/8478402")

    assert response.status_code == 200
    body = response.json()
    assert body["first_name"] == "Connor"
    assert body["last_name"] == "McDavid"
    assert body["team_abbrev"] == "EDM"
    assert body["sweater_number"] == 97
    assert body["is_active"] is True


def test_current_teams_maps_standings() -> None:
    _use_handler(lambda request: httpx.Response(200, json=STANDINGS_FIXTURE))

    response = client.get("/api/teams/current")

    assert response.status_code == 200
    assert response.json() == [
        {
            "abbrev": "COL",
            "name": "Colorado Avalanche",
            "common_name": "Avalanche",
            "conference": "Western",
            "division": "Central",
            "logo": "https://assets.nhle.com/logos/nhl/svg/COL_light.svg",
        }
    ]


def test_standings_passthrough() -> None:
    payload = {"standings": [{"teamAbbrev": {"default": "EDM"}}]}
    _use_handler(lambda request: httpx.Response(200, json=payload))

    response = client.get("/api/standings")

    assert response.status_code == 200
    assert response.json() == payload


def test_upstream_404_returns_404() -> None:
    _use_handler(lambda request: httpx.Response(404))

    response = client.get("/api/players/1")

    assert response.status_code == 404
    assert "detail" in response.json()


def test_upstream_timeout_returns_504() -> None:
    def handler(request: httpx.Request) -> httpx.Response:
        raise httpx.TimeoutException("timed out", request=request)

    _use_handler(handler)

    response = client.get("/api/players/8478402")

    assert response.status_code == 504
