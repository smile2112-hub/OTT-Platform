# FlowFlix OTT Platform

FlowFlix is a full-stack OTT (Over-the-Top) streaming platform where users can browse movies, manage their accounts, and enjoy a personalized viewing experience.

## Project structure

- `frontend/` — React application for the user interface.
- `backend/` — Spring Boot REST API, authentication, data access, and media-related services.

## Technology

- Frontend: React, React Router, Axios, Bootstrap
- Backend: Java 17, Spring Boot, Spring Security, Spring Data JPA, Maven
- Database: MySQL

## Run locally

Start the backend:

```bash
cd backend
./mvnw spring-boot:run
```

Start the frontend in a second terminal:

```bash
cd frontend
npm install
npm start
```

Configure the database and application settings in `backend/src/main/resources/application.properties` before starting the API.
