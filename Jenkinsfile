pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'emailerror/spring:v1.0'
        KUBE_NAMESPACE = 'default'
        DOCKER_USERNAME = 'email.error.421@gmail.com'
        DOCKER_PASSWORD = 'Emailerror@123'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                script {
                    // Build the Spring Boot application
                    bat './mvnw.cmd clean package'
                }
            }
        }

        stage('Docker Build') {
            steps {
                script {
                    // Build Docker image
                    bat 'docker build -t %DOCKER_IMAGE% .'
                }
            }
        }

        stage('Docker Push') {
            steps {
                script {
                    bat 'docker login -u %DOCKER_USERNAME% -p %DOCKER_PASSWORD%'
                        bat 'docker push %DOCKER_IMAGE%'
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                  bat 'kubectl apply -f spring-postgres.yaml --kubeconfig C:\\Users\\Thangamani R\\.kube\\config'                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
