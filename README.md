# FlowFlix OTT Platform

FlowFlix is a React and Spring Boot OTT platform with MySQL, Docker, Jenkins, and Kubernetes support.

## Local development

```bash
docker compose up --build
```

- Frontend: http://localhost:3000
- Backend: http://localhost:8081
- MySQL: `localhost:3307`

## Jenkins CI/CD

The repository Jenkinsfile provides this flow:

1. Checkout source.
2. Run Spring Boot tests with Maven.
3. Run React tests and build the frontend.
4. Build backend and frontend Docker images.
5. Push versioned and `latest` images to Docker Hub.
6. For builds of the `main` branch, apply Kubernetes manifests and roll out the new images.

Configure these Jenkins credentials:

| Jenkins credential ID | Type | Purpose |
|---|---|---|
| `dockerhub-credentials` | Username with password | Docker Hub login |
| `kubeconfig-credentials` | Secret file | Kubernetes cluster access |

Install Jenkins tools or ensure these binaries are available on the Jenkins agent:

- Git
- Java 17
- Maven
- Node.js 18 and npm
- Docker
- kubectl

Create a Pipeline job with **Pipeline script from SCM**, select this repository, set the script path to `Jenkinsfile`, and use the Jenkins GitHub webhook or polling to trigger builds.

Before enabling Kubernetes deployment, create the `flowflix-secrets` Secret in the target cluster and update `k8s/backend-deployment.yaml` and `k8s/frontend-deployment.yaml` to use your Docker Hub account.
