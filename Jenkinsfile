pipeline {
    agent any

    environment {

        DOCKER_USER = 'sidiiqm'
        IMAGE_NAME  = 'temperature-converter'
        IMAGE_TAG   = 'latest'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/sidm62/Test.git'
            }
        }

        stage('Build & Test') {
            steps {

                bat 'mvn clean package jacoco:report'
            }
        }

        stage('Publish Test & Coverage Reports') {
            steps {
                junit '**/target/surefire-reports/*.xml'
                jacoco execPattern: '**/target/jacoco.exec'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat "docker build -t %DOCKER_USER%/%IMAGE_NAME%:%IMAGE_TAG% ."
            }
        }

        stage('Push to Docker Hub') {
            steps {

                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_HUB_USER', passwordVariable: 'DOCKER_HUB_PASS')]) {
                    bat "echo %DOCKER_HUB_PASS% | docker login -u %DOCKER_HUB_USER% --password-stdin"
                    bat "docker push %DOCKER_USER%/%IMAGE_NAME%:%IMAGE_TAG%"
                }
            }
        }
    }

    post {
        always {

            bat 'docker logout'
        }
    }
}