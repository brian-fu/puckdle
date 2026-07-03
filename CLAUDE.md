# CLAUDE.md

Guidance for Claude Code when working in this repository. See `AGENTS.md` for
hard architectural constraints and coding standards — this file describes *how
the system is put together* and *where new code goes*.

## Working agreement (read first)

These rules apply to **every** change Claude makes in this repo:

1. **Test every change.** Any code change ships with a matching test in
   `backend/tests/` (mirroring the `app/` path), and the suite must be run:
   ```bash
   cd backend && ./.venv/bin/pytest        # runs with --cov=app (see pyproject.toml)
   ```
   New logic must be covered; do not lower overall coverage. Report the result.
   A `Stop` hook also runs this automatically at the end of each turn — treat a
   red suite as a blocker, not a warning.
2. **Keep this file current (self-improvement).** `CLAUDE.md` is the source of
   truth for the architecture. Whenever a change makes it stale — a new
   directory, service, router, dependency, env var, or a shift in how the pieces
   connect — update the relevant section (especially **Current state** below) in
   the *same* change. If reality and this file disagree, fix this file. Prefer a
   small, accurate edit over letting it drift.

## What Puckdle is

A daily NHL guessing game. A Next.js frontend talks over REST to a FastAPI
backend, which owns all data and persists it in PostgreSQL. External hockey data
is pulled from the public NHL API.

## Topology

Three containers, orchestrated by `docker-compose.yml`:

```
frontend (Next.js :3000) ──REST──▶ backend (FastAPI :8000) ──SQLAlchemy──▶ db (Postgres 15 :5432)
                                          │
                                          └──HTTP──▶ NHL API (api-web.nhle.com)
```

- The frontend **only** reaches the backend via `NEXT_PUBLIC_API_URL`
  (`http://localhost:8000/api`). It never touches the database.
- Startup ordering is gated by healthchecks: `db` (healthy) → `backend`
  (healthy) → `frontend`.
- Everything runs via `docker compose up`. Do not run `uvicorn`/`npm run dev`
  outside the Docker network (see `AGENTS.md`).

## Tech stack

| Layer    | Tech                                                    |
| -------- | ------------------------------------------------------- |
| Frontend | Next.js 16 (App Router), React 19, TypeScript 5         |
| Backend  | FastAPI 0.135, SQLAlchemy 2.0, Pydantic v2, Uvicorn     |
| Database | PostgreSQL 15                                           |
| Runtime  | Python 3.11 (prod), Docker + Docker Compose             |

## Backend layout (`backend/`)

Import root is `app.*` (Docker `WORKDIR /backend`; run via `server.py`).

```
backend/
├── server.py            # Uvicorn entrypoint (container CMD)
├── requirements.txt     # all deps (app + ruff + pytest/coverage)
├── pyproject.toml       # ruff, pytest & coverage config
├── .env                 # loaded by app/config.py (git- & docker-ignored)
├── tests/               # pytest suite (mirrors app/ structure)
└── app/
    ├── main.py          # create_app() factory: CORS, lifespan, router wiring
    ├── config.py        # Pydantic Settings, cached via lru_cache
    ├── database.py      # engine, SessionLocal, Base, get_db() dependency
    ├── schemas.py       # Pydantic request/response models
    ├── models/          # SQLAlchemy ORM models (inherit database.Base)
    ├── api/             # APIRouters
    └── services/        # external integrations (e.g. nhl.py → NHLService)
```

### How the pieces connect

- **Config** — `app/config.py` exposes `get_settings()`. Read settings through
  it; never read `os.environ` directly. Add a new field to the `Settings` class
  *and* to `backend/.env`.
- **Database** — `app/database.py` builds the `engine` from
  `settings.DATABASE_URL` and exposes `get_db()`. Inject a session into routes
  with `db: Session = Depends(get_db)`. The `lifespan` in `main.py` verifies
  connectivity (`SELECT 1`) at startup and disposes the pool at shutdown.
- **App assembly** — `create_app()` in `app/main.py` builds the FastAPI
  instance, adds CORS from `settings.CORS_ORIGINS`, and is where routers get
  included.

## Adding features (expansion points)

The backend is scaffolding; most layers are intentionally empty. Typical flow
for a new resource:

1. **Model** — add a class in `app/models/` inheriting `database.Base`.
2. **Schema** — add request/response `BaseModel`s in `app/schemas.py`.
3. **Service** (if external/complex logic) — add to `app/services/`.
4. **Router** — create an `APIRouter` in `app/api/`, then include it in
   `create_app()`:
   ```python
   from app.api.players import router as players_router
   app.include_router(players_router, prefix="/api")
   ```
5. **Migration** — once a migration tool is added, generate a migration; until
   then, DDL/seed SQL goes in `db-init/` (mounted into Postgres on first boot).

### Current state (keep this updated)

- Routes: only `GET /health`. No routers wired in yet.
- `app/models/`, `app/api/`, `app/schemas.py`: empty scaffolds.
- `NHLService.get_player()`: stub (`NotImplementedError`).
- No migration tool (e.g. Alembic) yet.
- Tests: `pytest` + coverage configured; `tests/test_health.py` covers
  `/health` (~61% total coverage, mostly untested stubs).

## Frontend layout (`frontend/`)

Next.js App Router. Functional components + hooks (see `AGENTS.md`).

```
frontend/src/app/
├── layout.tsx    # root layout
├── page.tsx      # home page
└── globals.css
```

## Local dev

Runtime is Docker-only, but a `backend/.venv` (Python) exists for editor
autocomplete, linting, and type-checking:

```bash
cd backend && source .venv/bin/activate   # tooling only, not for running the app
pip install -r requirements.txt            # first time: app + ruff + pytest
ruff check .                              # lint (config in pyproject.toml)
pytest                                    # tests + coverage (config in pyproject.toml)
```

Run the stack:

```bash
docker compose up --build   # watch backend log for "Database: connected"
```

## Conventions

- Backend imports are rooted at `app.*` (not `backend.app.*`).
- Strict type hints; Pydantic v2 for validation (per `AGENTS.md`).
- Settings come from `get_settings()`; the frontend and backend never share
  env vars or code.
