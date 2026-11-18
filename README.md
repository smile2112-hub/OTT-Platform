# FlowFlix OTT Platform

FlowFlix is a full-stack OTT streaming application built with React, Spring Boot, MySQL, Docker, Jenkins, and Kubernetes.

Users can browse and search movies, register and sign in, manage favorites and watchlists, play videos, track watch history, write reviews, and purchase subscriptions. Administrators can manage movies, users, reviews, subscriptions, payments, banners, and platform analytics.

## Features

- JWT-based authentication and role-based access
- Movie browsing, search, genres, featured, latest, trending, and recommended content
- Video playback, trailers, watch progress, and watch history
- Favorites, watchlists, reviews, and ratings
- UPI-style mock payment and subscription workflows
- Admin dashboard and content management
- MySQL persistence and Spring Boot Actuator health endpoints
- Docker Compose, Jenkins CI/CD, and Kubernetes deployment

## Project Status

FlowFlix is a complete full-stack academic project. Some repository structure and deployment cleanup is still required before it is production-ready:

- Move or configure the Spring source under `backend/src/`, or configure Maven to use repository-root `src/`
- Fix hardcoded frontend API URLs
- Replace mock UPI payment handling with a real payment provider
- Replace local and ephemeral media storage with persistent or object storage
- Replace development secrets and committed Kubernetes Secret values

## Architecture

```text
React Browser
    |
    | HTTP / REST
    v
Spring Boot API :8081
    |                 |
    | JDBC            | Local / persistent media storage
    v                 v
MySQL 8             uploads/

Docker Compose or Kubernetes
    |-- frontend (Nginx)
    |-- backend (Spring Boot)
    `-- mysql
```

## Technology Stack

| Area | Technology |
|---|---|
| Frontend | React 18, React Router, Axios, Bootstrap, React Bootstrap |
| Backend | Spring Boot 2.7, Java 17, Spring Security, Spring Data JPA |
| Database | MySQL 8 |
| Authentication | JWT |
| Containers | Docker and Docker Compose |
| CI/CD | Jenkins |
| Orchestration | Kubernetes |

## Repository Layout

```text
OTT-Platform/
├── src/main/java/com/cdac/flowflix/  # Spring Boot application source
├── src/main/resources/              # application.properties and SQL resources
├── src/test/java/                    # backend tests
├── frontend/                         # React application
├── backend/                          # Maven and backend container files
├── k8s/                              # Kubernetes deployments and secrets
├── docker-compose.yml                # local Docker Compose stack
├── Jenkinsfile                       # Jenkins CI/CD pipeline
├── uploads/                          # uploaded posters, banners, trailers, and videos
└── CONTRIBUTORS.md                   # project contributors
```

The Spring Java source is currently stored at the repository root in `src/`, while Maven metadata is in `backend/`. Run backend tooling from the repository root or align the Maven project layout before using the backend Docker and Jenkins stages.

## Prerequisites

- Java 17
- Maven
- Node.js 18 and npm
- MySQL 8
- Docker and Docker Compose
- Jenkins for CI/CD
- kubectl and access to a Kubernetes cluster

## Quick Start

```bash
git clone https://github.com/smile2112-hub/OTT-Platform.git
cd OTT-Platform
```

## Configuration

### Backend environment variables

| Variable | Default | Purpose |
|---|---|---|
| `SERVER_PORT` | `8081` | Backend HTTP port |
| `SPRING_DATASOURCE_URL` | `jdbc:mysql://localhost:3306/cinemaDB` | MySQL JDBC URL |
| `SPRING_DATASOURCE_USERNAME` | `root` | Database username |
| `SPRING_DATASOURCE_PASSWORD` | `root` | Database password |
| `SPRING_JPA_HBM2DDL` | `update` | Hibernate schema mode |
| `SPRING_JPA_SHOW_SQL` | `false` | Enable SQL logging |
| `JWT_SECRET` | Development fallback | Secret used to sign JWT tokens |
| `PAYMENT_UPI_ID` | Empty | Mock UPI payment identifier |
| `PAYMENT_UPI_NAME` | `FlowFlix` | Mock UPI account name |

Never commit real database passwords, JWT secrets, payment credentials, Docker Hub tokens, or kubeconfig files.

### Frontend API configuration

The active React application is under `frontend/src`. Several API clients currently use `http://127.0.0.1:8081/api` or `http://127.0.0.1:8081`. For container or Kubernetes deployment, configure the frontend API origin consistently and route `/api/` through Nginx or the cluster ingress.

## Local Development

### MySQL

Start MySQL on your host or as a container:

```bash
docker run --name flowflix-mysql \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=cinemaDB \
  -p 3307:3306 \
  -d mysql:8.0
```

### Backend

The current source layout prevents the existing Maven wrapper and Dockerfile from compiling successfully: Java sources are in root `src/`, while `backend/pom.xml` declares the Maven project under `backend/`.

After moving or configuring the source layout, start the backend from the Maven project directory:

```bash
cd backend
mvn spring-boot:run
```

The backend listens on `http://localhost:8081` by default.

Health endpoints include:

- `http://localhost:8081/health`
- `http://localhost:8081/actuator/health`

### Frontend

```bash
cd frontend
npm ci
npm start
```

The frontend development server normally runs on `http://localhost:3000`.

### Docker Compose

```bash
docker compose up --build
```

Services and ports:

| Service | URL or port |
|---|---|
| Frontend | `http://localhost:3000` |
| Backend | `http://localhost:8081` |
| MySQL | Host port `3307` |

Docker Compose mounts a named volume for uploaded media at `/app/uploads`.

## Application Routes

### Public user routes

| Route | Purpose |
|---|---|
| `/` and `/home` | Home page |
| `/login` | Sign in |
| `/register` | Account registration |
| `/movies` and `/search` | Search and browse movies |
| `/movie/:id` | Movie details |

### Protected user routes

| Route | Purpose |
|---|---|
| `/watch/:id` | Video playback |
| `/profile` | User profile |
| `/favorite` and `/favorites` | Favorite movies |
| `/watchlist` and `/my-watchlist` | Watchlist |
| `/subscription` | Subscription plans |
| `/payment` | Payment flow |
| `/my-reviews` | User reviews |

### Admin routes

| Route | Purpose |
|---|---|
| `/admin` | Admin dashboard |
| `/admin/movies` | Manage movies |
| `/admin/add-movie` | Add a movie |
| `/admin/edit-movie/:id` | Edit a movie |
| `/admin/users` | Manage users |
| `/admin/reviews` | Manage reviews |
| `/admin/subscriptions` | Manage subscriptions |

## Backend API Overview

The backend exposes REST APIs under `/api`, with health endpoints outside `/api`.

| Resource | Example endpoints |
|---|---|
| Authentication | `POST /api/login`, `POST /api/auth/login`, `POST /api/logout` |
| Users | `POST /api/user/register`, `GET /api/user/profile`, `PUT /api/user/changePassword` |
| Movies | `GET /api/movie/getAllMovies`, `GET /api/movie/{id}`, `POST /api/movie` |
| Discovery | `GET /api/movie/featured`, `GET /api/movie/latest`, `GET /api/movie/trending` |
| Search | `GET /api/movie/search/{keyword}`, `GET /api/home/search/{keyword}` |
| Recommendations | `GET /api/recommendation/trending`, `GET /api/recommendation/genre/{genre}` |
| Favorites | `POST /api/favorite/add`, `GET /api/favorite/my`, `GET /api/favorite/check/{movieId}` |
| Watchlist | `POST /api/watchlist/add`, `GET /api/watchlist/my`, `DELETE /api/watchlist/remove/{movieId}` |
| Watch history | `POST /api/history/save`, `GET /api/history/continue`, `GET /api/history/my` |
| Reviews | `POST /api/review`, `GET /api/review/movie/{movieId}`, `PUT /api/review/{id}` |
| Subscriptions | `POST /api/subscription/purchase`, `GET /api/subscription/current`, `GET /api/subscription/active` |
| Payments | `POST /api/payment/create`, `POST /api/payment/verify`, `GET /api/payment/my` |
| Media | `GET /api/files/poster/{filename}`, `GET /api/video/stream/{id}` |
| Admin | Dashboard, user, movie, review, subscription, and revenue endpoints under `/api/admin` |

Controller mappings are defined under `src/main/java/com/cdac/flowflix/controller/`. The final set of enabled endpoints should be verified against the running Spring application because several endpoint variants and legacy mappings exist.

## Testing

Frontend tests:

```bash
cd frontend
CI=true npm test -- --watchAll=false --passWithNoTests
npm run build
```

Backend tests:

```bash
cd backend
mvn test
```

Jenkins publishes backend Surefire XML reports from `backend/target/surefire-reports/*.xml`.

## Jenkins CI/CD

The `Jenkinsfile` defines the following pipeline:

1. Check out the repository.
2. Run backend Maven tests.
3. Run frontend tests and build the React application.
4. Build backend and frontend Docker images.
5. Push build-number tags and `latest` tags to Docker Hub.
6. On `main`, apply Kubernetes manifests and wait for rollouts to complete.

Configure these Jenkins credentials:

| Credential ID | Jenkins type | Purpose |
|---|---|---|
| `dockerhub-credentials` | Username with password | Docker Hub authentication |
| `kubeconfig-credentials` | Secret file | Kubernetes cluster authentication |

The Jenkins agent must have Git, Java 17, Maven, Node.js 18, npm, Docker, and kubectl.

Create a Pipeline job with **Pipeline script from SCM**, set the script path to `Jenkinsfile`, and configure a GitHub webhook or polling trigger.

## Kubernetes Deployment

The `k8s/` directory contains:

- `mysql-deployment.yaml`
- `backend-deployment.yaml`
- `frontend-deployment.yaml`
- `secrets.yaml`

Replace example secrets and Docker Hub image names before deployment.

```bash
kubectl apply -f k8s/secrets.yaml
kubectl apply -f k8s/mysql-deployment.yaml
kubectl wait --for=condition=ready pod -l app=mysql --timeout=120s
kubectl apply -f k8s/backend-deployment.yaml
kubectl apply -f k8s/frontend-deployment.yaml
```

Check resources:

```bash
kubectl get pods
kubectl get services
kubectl rollout status deployment/flowflix-backend
kubectl rollout status deployment/flowflix-frontend
```

Update an image manually:

```bash
kubectl set image deployment/flowflix-backend \
  backend=DOCKERHUB_USER/flowflix-backend:TAG

kubectl set image deployment/flowflix-frontend \
  frontend=DOCKERHUB_USER/flowflix-frontend:TAG
```

For a local cluster:

```bash
minikube start
minikube service flowflix-frontend-service
```

## Security and Deployment Notes

- Replace development database and JWT secrets before deployment.
- Kubernetes Secret values in `k8s/secrets.yaml` are base64 encoded; do not treat them as encrypted.
- Prefer Jenkins credentials, Kubernetes Secrets, or an external secret manager for production.
- Replace or remove the fixed default administrator setup before exposing the application.
- Restrict CORS origins to deployed frontend domains.
- The current Kubernetes media volume is an `emptyDir`; uploaded files are not persistent across rescheduling or pod replacement.
- Use a persistent media volume or object storage for production uploads.
- The payment flow is a mock UPI workflow, not a live payment gateway integration.

## Known Repository Issues

- Backend Java sources are in root `src/`, while Maven and Docker configuration are in `backend/`. This layout must be aligned for Maven/Docker/Jenkins builds.
- The frontend contains duplicate legacy pages and routers that should be reconciled or removed.
- The frontend includes hardcoded local API URLs.
- The frontend currently emits React/ESLint warnings and should be cleaned before CI is configured to treat warnings as errors.
- Media storage is local or ephemeral in Kubernetes.
- Legacy seed SQL and committed media files may require review before production use.

## Contributing

1. Create a feature branch.
2. Make focused backend or frontend changes.
3. Run the relevant tests and build.
4. Add or update documentation.
5. Open a pull request.

See [CONTRIBUTORS.md](CONTRIBUTORS.md) for project contributors.
