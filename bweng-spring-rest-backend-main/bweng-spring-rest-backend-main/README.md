# Kurzdokumentation

## Kurzbeschreibung
Leichtgewichtige Spring Boot REST-Backend-Anwendung für das BWENG-Template mit MariaDB und MinIO als Container.  
API unter `/api` mit OpenAPI/Swagger-UI.

## Wichtiges (aus dem README.md)
- **Anforderungen:** Docker
- **API Endpunkte:** Basis `/api`
- **Swagger/OpenAPI:** JSON unter `/api`, UI unter `/swagger.html`
- **Statische Bilder:** `src/main/resources/static/images`
- **DB Initialisierung:** `db-init.sql`
- **Security & Auth:** Implementiert (siehe `src/main/java/at/technikum/springrestbackend/config/SecurityConfig.java` und `src/main/java/at/technikum/springrestbackend/controller/AuthController.java`)

## Container / Ports
- **Spring Boot:** Port 8080 (anpassbar in `src/main/resources/application.properties`)
- **MariaDB:** Port 3306
- **MinIO:** Port 9000 (Dashboard: 9001)

## Schnellbefehle (Docker)
```bash
docker compose build
docker compose up
docker compose up --build
docker compose stop
docker compose down
