# Students Scores Service — Spring Boot + PostgreSQL

A Spring Boot service that accepts **students’ scores in 5 subjects** and produces a report showing each student’s:

- Raw scores (per subject)  
- **Mean** (average)  
- **Median**  
- **Mode**  

The API supports **filtering**, **pagination**, **OpenAPI/Swagger UI**, **data validation (0–100)**, and **Docker Compose** deployment with PostgreSQL.


---

## Architecture

**Tech stack**
- Java 17+ / Spring Boot 3.x
- Spring Web, Spring Data JPA, Validation
- PostgreSQL
- OpenAPI/Swagger (`springdoc-openapi`)
- JUnit 5 + Mockito
- Docker / Docker Compose

**Layers**
- `controller` – REST endpoints  
- `service` – statistics logic (mean/median/mode), pagination orchestration  
- `repository` – JPA repositories  
- `model` – JPA entities  
- `dto` – API-facing models (e.g., `StudentStatistics`)

## Endpoints

Swagger UI:
- **Boot 3 / springdoc 2.x:** `http://localhost:8080/swagger-ui/index.html`

OpenAPI JSON: `http://localhost:8080/v3/api-docs`

### 1) List statistics (paged)
```
GET /api/students/statisticsWithPagination
```

**Query params**
- `studentId` *(optional, integer)* – filter to a single student
- `page` *(default 0)* – zero-based page index
- `size` *(default 20, capped to 100)* – page size
- `sort` *(optional)* – e.g. `studentId,asc`

**Response** – `Page<StudentStatistics>`
```json
{
  "content": [
    { "studentId": 1, "meanScore": 80.40, "medianScore": 82.00, "modeScore": 79.00 },
    { "studentId": 2, "meanScore": 82.40, "medianScore": 81.00, "modeScore": 88.00 }
  ],
  "pageable": { "...": "..." },
  "totalElements": 3,
  "totalPages": 2,
  "size": 20,
  "number": 0
}
```

**cURL**
```bash
curl "http://localhost:8080/api/students/statisticsWithPagination?page=0&size=10&sort=studentId,asc"
curl "http://localhost:8080/api/students/statistics?studentId=1"
```

## Local Setup (Docker Compose)

**docker-compose.yml**
```yaml
services:
  db:
    image: postgres:16
    environment:
      POSTGRES_USER: application
      POSTGRES_PASSWORD: development
      POSTGRES_DB: studentdb
    ports:
      - "5432:5432"
    volumes:
      - pgdata:/var/lib/postgresql/data
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U application -d studentdb"]
      interval: 5s
      timeout: 5s
      retries: 10
volumes:
  pgdata:
```

Start DB:
```bash
docker compose up -d
```

**Application config (`application.yml`)**
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/studentdb
    username: application
    password: development
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
```

> **Timezone tip:** If you see `invalid value for parameter "TimeZone": "US/Pacific"`, run app with  
> `-Duser.timezone=UTC` (or set `JAVA_TOOL_OPTIONS: -Duser.timezone=UTC`).

Run app:
```bash
./mvnw spring-boot:run
# or
mvn spring-boot:run
```

---

## Local Setup (Without Docker)

Install PostgreSQL (e.g., Homebrew or Postgres.app), then:

```bash
createdb studentdb
psql -d studentdb -c "CREATE USER application WITH PASSWORD 'development';"
psql -d studentdb -c "GRANT ALL PRIVILEGES ON DATABASE studentdb TO application;"
```

Configure the same JDBC URL in `application.yml` and run the app.

---

## Running Tests

```bash
./mvnw test
# or
mvn test
```

Unit tests cover:
- Mean / Median (odd & even) / Mode calculations
- `getStudentStatistics` for present/absent students
- Pagination branch logic (filtered vs distinct IDs)

---

## Design Notes

- **Computation strategy**: We flatten each row’s 5 subject scores and compute:
  - **Mean** – `AVG` in Java (`BigDecimal` sum ÷ count, `HALF_UP`)
  - **Median** – sort then take middle (average of two for even counts)
  - **Mode** – frequency map; ties resolved by smallest value
- **Why compute in service (Java)?**  
  Keeps median/mode logic self-contained and testable. SQL percentile + window functions are available, but Java provides clarity and portability.
- **Pagination**: If `studentId` is provided → single “page” with that student’s stats (or empty). Otherwise we page over **distinct student IDs** and map each to `StudentStatistics`.

---

## Assumptions

- Each record represents **one observation** of 5 subject scores for a `student_id`. Multiple rows per student are allowed; we aggregate across all rows per student.
- Score range is **0–100**, enforced at DB-level.  
- For **mode** ties, the **smallest** score is returned.
- Sorting of `/statistics` operates on the DTO fields that exist (e.g., `studentId`). If you need sort by mean/median/mode, add a post-processing sort or persist aggregates.

---

## Future Improvements

- **Batch endpoints**: bulk create/update scores with validation report.
- **Precomputed aggregates**: materialized view or nightly job for very large datasets.
- **Integration tests**: Testcontainers for real Postgres in CI.
- **AuthN/Z**: add JWT/OAuth2 if required.
- **Observability**: metrics & tracing (Micrometer/Prometheus/OpenTelemetry).
- **Microservices split (optional)**:
  - `score-service` (CRUD)  
  - `stats-service` (aggregations)  
  - API gateway / discovery if needed (Spring Cloud)

---

## Quick Commands

```bash
# Start DB
docker compose up -d

# Open psql into the container
docker exec -it $(docker ps -qf name=db) psql -U application -d studentdb

# Seed a few rows (adjust to your table/columns)
INSERT INTO student_score (student_id, subject1score, subject2score, subject3score, subject4score, subject5score)
VALUES (1,80,90,70,60,85),(2,88,76,92,81,75);

# Run app
./mvnw spring-boot:run

# Swagger UI
open http://localhost:8080/swagger-ui/index.html
```

---
