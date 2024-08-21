pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'emailerror/spring:v1.0'
        KUBE_NAMESPACE = ''
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
                    sh './mvnw clean package'
                }
            }
        }

        stage('Docker Build') {
            steps {
                script {
                    // Build Docker image
                    sh 'docker build -t $DOCKER_IMAGE .'
                }
            }
        }

        stage('Docker Push') {
            steps {
                script {
                    withDockerRegistry([credentialsId: 'docker-credentials', url: '']) {
                        sh 'docker push $DOCKER_IMAGE'
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    kubectlApply(KUBECONFIG: '/path/to/kubeconfig', MANIFEST: 'spring-postgres.yaml')
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
