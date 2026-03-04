# AGENTS.md

## Tech Stack
* **Frontend:** Next.js (Node 18), React.
* **Backend:** FastAPI (Python 3.11), SQLAlchemy 2.0, Uvicorn.
* **Database:** PostgreSQL 15.
* **Infrastructure:** Docker, Docker Compose. Target deployment: AWS ECS/RDS.

## Architectural Constraints
* **Separation of Concerns:** The frontend and backend are completely isolated. You are strictly forbidden from sharing environment variables or code between the `frontend/` and `backend/` directories.
* **Database Access:** Only the FastAPI backend interacts with PostgreSQL. The frontend must only communicate with the backend via REST API calls to `http://localhost:8000`.
* **Containerization:** All services must run via `docker-compose`. Do not instruct me to run local `npm run dev` or `uvicorn` outside of the Docker network.

## Coding Standards
* **Python:** Enforce strict type hinting. Use `Pydantic` v2 for data validation.
* **TypeScript/Next.js:** Use functional components and hooks.