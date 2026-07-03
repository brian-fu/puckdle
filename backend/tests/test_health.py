from fastapi.testclient import TestClient

from app.main import app

# Note: instantiating TestClient without `with` skips the lifespan, so this is a
# pure unit test that does not require a running database.
client = TestClient(app)


def test_health_check() -> None:
    response = client.get("/health")
    assert response.status_code == 200
    assert response.json() == {"status": "healthy"}
