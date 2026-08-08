pipeline {
    agent any

    environment {
        DOCKERHUB_USER   = 'aakanksha0499'
        BACKEND_IMAGE    = "${DOCKERHUB_USER}/flowflix-backend"
        IMAGE_TAG        = "${BUILD_NUMBER}"
        DOCKERHUB_CREDS  = 'dockerhub-credentials'
    }

    stages {

        // ── 1. Checkout ─────────────────────────────────
        stage('Checkout') {
            steps {
                checkout scm
                echo "Building branch: ${env.BRANCH_NAME}, build #${BUILD_NUMBER}"
            }
        }

        // ── 2. Build Backend JAR ─────────────────────────
        stage('Build Backend') {
            steps {
                sh 'mvn clean package -DskipTests -B'
            }
        }

        // ── 3. Test ──────────────────────────────────────
        stage('Test') {
            steps {
                sh 'mvn test -B'
            }
            post {
                always {
                    junit allowEmptyResults: true,
                          testResults: 'target/surefire-reports/*.xml'
                }
            }
        }

        // ── 4. Build Docker Image ────────────────────────
        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${BACKEND_IMAGE}:${IMAGE_TAG} -t ${BACKEND_IMAGE}:latest ."
            }
        }

        // ── 5. Push to Docker Hub ────────────────────────
        stage('Push Image') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: "${DOCKERHUB_CREDS}",
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh "echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin"
                    sh "docker push ${BACKEND_IMAGE}:${IMAGE_TAG}"
                    sh "docker push ${BACKEND_IMAGE}:latest"
                }
            }
        }

        // ── 6. Deploy to Kubernetes ──────────────────────
        stage('Deploy') {
            when {
                branch 'main'
            }
            steps {
                echo "Deploy stage - configure kubectl here when Kubernetes is ready"
            }
        }
    }

    post {
        success {
            echo "Pipeline succeeded! Backend image tagged: ${IMAGE_TAG}"
        }
        failure {
            echo "Pipeline failed. Check the logs above."
        }
        always {
            sh "docker rmi ${BACKEND_IMAGE}:${IMAGE_TAG} || true"
        }
    }
}
