pipeline {
    agent any

    environment {
        
        DOCKERHUB_USER   = 'aakanksha0499'
        FRONTEND_IMAGE   = "${DOCKERHUB_USER}/flowflix-frontend"
        BACKEND_IMAGE    = "${DOCKERHUB_USER}/flowflix-backend"
        IMAGE_TAG        = "${BUILD_NUMBER}"

        // ── Credential IDs configured in Jenkins ──
        DOCKERHUB_CREDS  = 'dockerhub-credentials'   // Jenkins credential ID
        KUBECONFIG_CREDS = 'kubeconfig-credentials'  // Jenkins credential ID
    }

    stages {

        // ── 1. Checkout ─────────────────────────────────
        stage('Checkout') {
            steps {
                checkout scm
                echo "Building branch: ${env.BRANCH_NAME}, build #${BUILD_NUMBER}"
            }
        }

        // ── 2. Build & Test Backend ──────────────────────
        stage('Build Backend') {
            steps {
                dir('M/OTT-Platform') {
                    sh 'mvn clean package -DskipTests -B'
                }
            }
        }

        stage('Test Backend') {
            steps {
                dir('M/OTT-Platform') {
                    sh 'mvn test -B'
                }
            }
            post {
                always {
                    junit allowEmptyResults: true,
                          testResults: 'M/OTT-Platform/target/surefire-reports/*.xml'
                }
            }
        }

        // ── 3. Build Docker Images ───────────────────────
        stage('Build Docker Images') {
            parallel {
                stage('Frontend Image') {
                    steps {
                        dir('front-end') {
                            sh "docker build -t ${FRONTEND_IMAGE}:${IMAGE_TAG} -t ${FRONTEND_IMAGE}:latest ."
                        }
                    }
                }
                stage('Backend Image') {
                    steps {
                        dir('M/OTT-Platform') {
                            sh "docker build -t ${BACKEND_IMAGE}:${IMAGE_TAG} -t ${BACKEND_IMAGE}:latest ."
                        }
                    }
                }
            }
        }

        // ── 4. Push Images to Docker Hub ─────────────────
        stage('Push Images') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: "${DOCKERHUB_CREDS}",
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh "echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin"
                    sh "docker push ${FRONTEND_IMAGE}:${IMAGE_TAG}"
                    sh "docker push ${FRONTEND_IMAGE}:latest"
                    sh "docker push ${BACKEND_IMAGE}:${IMAGE_TAG}"
                    sh "docker push ${BACKEND_IMAGE}:latest"
                }
            }
        }

        // ── 5. Deploy to Kubernetes ──────────────────────
        stage('Deploy to Kubernetes') {
            when {
                branch 'main'   // only deploy from main branch
            }
            steps {
                withCredentials([file(
                    credentialsId: "${KUBECONFIG_CREDS}",
                    variable: 'KUBECONFIG'
                )]) {
                    sh """
                        kubectl set image deployment/flowflix-frontend \
                            frontend=${FRONTEND_IMAGE}:${IMAGE_TAG} \
                            --record

                        kubectl set image deployment/flowflix-backend \
                            backend=${BACKEND_IMAGE}:${IMAGE_TAG} \
                            --record

                        kubectl rollout status deployment/flowflix-frontend
                        kubectl rollout status deployment/flowflix-backend
                    """
                }
            }
        }
    }

    post {
        success {
            echo "Pipeline succeeded! Images tagged: ${IMAGE_TAG}"
        }
        failure {
            echo "Pipeline failed. Check the logs above."
        }
        always {
            // Clean up local Docker images to save disk space
            sh "docker rmi ${FRONTEND_IMAGE}:${IMAGE_TAG} || true"
            sh "docker rmi ${BACKEND_IMAGE}:${IMAGE_TAG} || true"
        }
    }
}
