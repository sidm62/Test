pipeline {
    agent any

    environment {
        JAVA_HOME        = 'C:\\Program Files\\Eclipse Adoptium\\jdk-21.0.13.9-hotspot'
        PATH             = "C:\\Program Files\\Eclipse Adoptium\\jdk-21.0.13.9-hotspot\\bin;C:\\Users\\sidru\\AppData\\Local\\Programs\\DockerDesktop\\resources\\bin;C:\\Program Files\\Docker\\Docker\\resources\\bin;C:\\Users\\sidru\\Downloads\\apache-maven-3.9.16-bin\\apache-maven-3.9.16\\bin;${env.PATH}"
        Path             = "C:\\Program Files\\Eclipse Adoptium\\jdk-21.0.13.9-hotspot\\bin;C:\\Users\\sidru\\AppData\\Local\\Programs\\DockerDesktop\\resources\\bin;C:\\Program Files\\Docker\\Docker\\resources\\bin;C:\\Users\\sidru\\Downloads\\apache-maven-3.9.16-bin\\apache-maven-3.9.16\\bin;${env.Path}"
        SONARQUBE_SERVER = 'Sidiiq Mohamed'
        DOCKER_USER      = 'sidiiqm'
        IMAGE_NAME       = 'temperature-converter'
        IMAGE_TAG        = 'latest'
    }

    stages {
        stage('Build & Test') {
            steps {
                bat 'mvn clean package org.jacoco:jacoco-maven-plugin:report -B'
            }
        }

        stage('Publish Test & Coverage Reports') {
            steps {
                junit '**/target/surefire-reports/*.xml'
                jacoco execPattern: '**/target/jacoco.exec'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withCredentials([string(credentialsId: 'SONAR_TOKEN', variable: 'SONAR_TOKEN')]) {
                    withSonarQubeEnv("${env.SONARQUBE_SERVER}") {
                        bat """
                            mvn sonar:sonar ^
                            -Dsonar.projectKey=temperature-converter ^
                            -Dsonar.projectName=temperature-converter ^
                            -Dsonar.host.url=http://localhost:9000 ^
                            -Dsonar.token=%SONAR_TOKEN%
                        """
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                bat "docker build --no-cache -t %DOCKER_USER%/%IMAGE_NAME%:%IMAGE_TAG% ."
            }
        }

        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'HUB_USER', passwordVariable: 'HUB_PASS')]) {
                    bat '<nul set /p="%HUB_PASS%" | docker login -u %HUB_USER% --password-stdin'
                    bat 'docker push %DOCKER_USER%/%IMAGE_NAME%:%IMAGE_TAG%'
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