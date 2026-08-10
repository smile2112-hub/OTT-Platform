# FlowFlix – Kubernetes Deployment Guide

## Prerequisites
- kubectl configured and pointing to your cluster
- Docker Hub account with images pushed

## Deploy Order

```bash
# 1. Create secrets (edit values first!)
kubectl apply -f k8s/secrets.yaml

# 2. Deploy MySQL
kubectl apply -f k8s/mysql-deployment.yaml

# 3. Wait for MySQL to be ready
kubectl wait --for=condition=ready pod -l app=mysql --timeout=120s

# 4. Deploy Backend
kubectl apply -f k8s/backend-deployment.yaml

# 5. Deploy Frontend
kubectl apply -f k8s/frontend-deployment.yaml

# 6. Check everything
kubectl get pods
kubectl get services
```

## Update an image after a new build
```bash
kubectl set image deployment/flowflix-backend backend=yourdockerhubusername/flowflix-backend:NEW_TAG
kubectl set image deployment/flowflix-frontend frontend=yourdockerhubusername/flowflix-frontend:NEW_TAG
```

## Minikube (local testing)
```bash
minikube start
# Change Service type to NodePort in frontend-deployment.yaml, then:
minikube service flowflix-frontend-service
```
