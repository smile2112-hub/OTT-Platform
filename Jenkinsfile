pipeline {
    agent any

    options {
        timestamps()
        disableConcurrentBuilds()
        buildDiscarder(logRotator(daysToKeepStr: '14', numToKeepStr: '30'))
    }

    parameters {
        string(name: 'DOCKERHUB_USER', defaultValue: 'aakanksha0499',
               description: 'Docker Hub organization or username')
    }

    environment {
        DOCKERHUB_USER   = "${params.DOCKERHUB_USER}"
        FRONTEND_IMAGE   = "${DOCKERHUB_USER}/flowflix-frontend"
        BACKEND_IMAGE    = "${DOCKERHUB_USER}/flowflix-backend"
        IMAGE_TAG        = "${BUILD_NUMBER}"
        DOCKERHUB_CREDS  = 'dockerhub-credentials'
        KUBECONFIG_CREDS = 'kubeconfig-credentials'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
                sh 'git --no-pager status --short'
                echo "Building branch: ${env.BRANCH_NAME ?: 'not-multibranch'}, build #${BUILD_NUMBER}"
            }
        }

        stage('Test Backend') {
            steps {
                dir('backend') {
                    sh 'mvn clean test -B'
                }
            }
            post {
                always {
                    junit allowEmptyResults: true,
                          testResults: 'backend/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Test Frontend') {
            steps {
                dir('frontend') {
                    sh 'npm ci'
                    sh 'CI=true npm test -- --watchAll=false --passWithNoTests'
                }
            }
        }

        stage('Build Artifacts') {
            parallel {
                stage('Backend') {
                    steps {
                        dir('backend') {
                            sh 'mvn package -DskipTests -B'
                        }
                    }
                }
                stage('Frontend') {
                    steps {
                        dir('frontend') {
                            sh 'npm ci'
                            sh 'npm run build'
                        }
                    }
                }
            }
        }

        stage('Build and Push Docker Images') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: "${DOCKERHUB_CREDS}",
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh "echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin"
                    sh "docker build -t ${FRONTEND_IMAGE}:${IMAGE_TAG} -t ${FRONTEND_IMAGE}:latest frontend"
                    sh "docker build -t ${BACKEND_IMAGE}:${IMAGE_TAG} -t ${BACKEND_IMAGE}:latest backend"
                    sh "docker push ${FRONTEND_IMAGE}:${IMAGE_TAG}"
                    sh "docker push ${FRONTEND_IMAGE}:latest"
                    sh "docker push ${BACKEND_IMAGE}:${IMAGE_TAG}"
                    sh "docker push ${BACKEND_IMAGE}:latest"
                }
            }
        }

        stage('Deploy to Kubernetes') {
            when {
                allOf {
                    branch 'main'
                    expression { env.CHANGE_ID == null }
                }
            }
            steps {
                withCredentials([file(
                    credentialsId: "${KUBECONFIG_CREDS}",
                    variable: 'KUBECONFIG'
                )]) {
                    sh """
                        kubectl apply -f k8s/mysql-deployment.yaml
                        kubectl apply -f k8s/backend-deployment.yaml
                        kubectl apply -f k8s/frontend-deployment.yaml
                        kubectl set image deployment/flowflix-frontend frontend=${FRONTEND_IMAGE}:${IMAGE_TAG}
                        kubectl set image deployment/flowflix-backend backend=${BACKEND_IMAGE}:${IMAGE_TAG}
                        kubectl rollout status deployment/flowflix-frontend --timeout=5m
                        kubectl rollout status deployment/flowflix-backend --timeout=5m
                    """
                }
            }
        }
    }

    post {
        success {
            echo "Pipeline succeeded. Published ${FRONTEND_IMAGE}:${IMAGE_TAG} and ${BACKEND_IMAGE}:${IMAGE_TAG}"
        }
        failure {
            echo "Pipeline failed. Check the logs above."
        }
        always {
            sh "docker rmi ${FRONTEND_IMAGE}:${IMAGE_TAG} || true"
            sh "docker rmi ${BACKEND_IMAGE}:${IMAGE_TAG} || true"
            sh "docker rmi ${FRONTEND_IMAGE}:latest || true"
            sh "docker rmi ${BACKEND_IMAGE}:latest || true"
        }
    }
}
