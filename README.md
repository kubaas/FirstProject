# IMO Diet Coach (MVP) — Angular + Spring Boot + Camunda + Postgres (bez AWS)

To jest minimalne MVP aplikacji dla **insulinooporności (IMO)**:

- onboarding użytkownika (profil, preferencje, ograniczenia),
- automatyczne wygenerowanie planu posiłków na 7 dni,
- tygodniowy check-in i korekta planu (workflow w Camundzie + decyzja DMN),
- prosta aplikacja web (Angular) jako UI.

## Wymagania

- Java 17+
- Maven 3.9+
- Node 18+ (lub 20+)
- Docker (do Postgresa)

## Uruchomienie (lokalnie)

### 1) Baza danych

W katalogu głównym:

```bash
docker compose up -d
```

Postgres będzie dostępny na `localhost:5432` (login/hasło w `docker-compose.yml`).

### 2) Backend (Spring + Camunda)

```bash
cd backend
mvn spring-boot:run
```

Backend wystartuje na `http://localhost:8080`.

- Panel Camundy: `http://localhost:8080/camunda`
  - login: `demo`
  - hasło: `demo`

### 3) Frontend (Angular)

```bash
cd frontend
npm install
npm start
```

Frontend: `http://localhost:4200`

## Szybki test (API)

Onboarding:

```bash
curl -s -X POST "http://localhost:8080/api/users/onboarding" \
  -H "Content-Type: application/json" \
  -d '{
    "displayName":"Jakub",
    "heightCm":180,
    "weightKg":95,
    "activityLevel":"MODERATE",
    "goal":"FAT_LOSS",
    "mealsPerDay":3,
    "budget":"MEDIUM",
    "cookTimeMinutes":25,
    "dietStyle":"OMNIVORE",
    "allergens":[]
  }' | jq
```

Check-in:

```bash
curl -s -X POST "http://localhost:8080/api/users/<USER_ID>/checkins" \
  -H "Content-Type: application/json" \
  -d '{
    "complianceScore":0.6,
    "hungerEvening":0.8,
    "energyAfterMeals":0.4,
    "sleepScore":0.5,
    "cravings":0.7
  }' | jq
```

## Struktura

- `backend/` — Spring Boot + Hibernate + Camunda (BPMN/DMN)
- `frontend/` — Angular UI
- `docker-compose.yml` — Postgres

