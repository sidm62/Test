pipeline {
    agent any

    environment {
        PATH        = "C:\\Users\\sidru\\AppData\\Local\\Programs\\DockerDesktop\\resources\\bin;C:\\Users\\sidru\\Downloads\\apache-maven-3.9.16-bin\\apache-maven-3.9.16\\bin;${env.PATH}"
        DOCKER_USER = 'sidiiqm'
        IMAGE_NAME  = 'temperature-converter'
        IMAGE_TAG   = 'latest'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/sidm62/Test.git'
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
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'HUB_USER', passwordVariable: 'HUB_PASS')]) {
                    bat "docker login -u %HUB_USER% -p %HUB_PASS%"
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