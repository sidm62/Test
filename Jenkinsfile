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
                // Ajetaan kääntö, testit ja JaCoCo-raportointi
                bat 'mvn clean package org.jacoco:jacoco-maven-plugin:report'
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
                bat "docker build --no-cache -t %DOCKER_USER%/%IMAGE_NAME%:%IMAGE_TAG% ."
            }
        }

        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'HUB_USER', passwordVariable: 'HUB_PASS')]) {
                    // Windows-yhteensopiva ja turvallinen tapa kirjautua sisään
                    bat '''
                        echo %HUB_PASS% > temp_pass.txt
                        type temp_pass.txt | docker login -u %HUB_USER% --password-stdin
                        del temp_pass.txt
                    '''
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