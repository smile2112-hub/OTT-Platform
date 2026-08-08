pipeline {
    agent any

    tools {
        maven 'Maven'
        dockerTool 'Docker'
    }

    environment {
        DOCKERHUB_USER  = 'aakanksha0499'
        BACKEND_IMAGE   = "${DOCKERHUB_USER}/flowflix-backend"
        IMAGE_TAG       = "${BUILD_NUMBER}"
        DOCKERHUB_CREDS = 'dockerhub-credentials'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
                echo "Build #${BUILD_NUMBER}"
            }
        }

        stage('Build Backend') {
            steps {
                sh 'mvn clean package -DskipTests -B'
            }
        }

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

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${BACKEND_IMAGE}:${IMAGE_TAG}")
                    docker.build("${BACKEND_IMAGE}:latest")
                }
            }
        }

        stage('Push Image') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', "${DOCKERHUB_CREDS}") {
                        docker.image("${BACKEND_IMAGE}:${IMAGE_TAG}").push()
                        docker.image("${BACKEND_IMAGE}:latest").push()
                    }
                }
            }
        }

        stage('Deploy') {
            when { branch 'main' }
            steps {
                echo "Deploy stage - configure kubectl here when Kubernetes is ready"
            }
        }
    }

    post {
        success { echo "Pipeline succeeded! Image: ${IMAGE_TAG}" }
        failure { echo "Pipeline failed." }
    }
}
