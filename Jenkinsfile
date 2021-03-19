pipeline {
 
    agent any
 
    tools {
        maven 'mvn-v3.6.0'
        jdk 'jdk8'
    }
 
    environment {
        loginServer = credentials('dockerserver')
        dockerusername = credentials('dockerusername')
        dockerpassword = credentials('dockerpassword')
        imageWithTag = "$loginServer/$dockerusername/shoppingcartimage:latest"
    }
 
    stages {
        stage('Build') {
            steps {
                echo 'Building......'
                sh 'mvn clean package -DskipTests=True'
            }
        }
 
        stage ('Docker Build') {
            steps {
                sh 'pwd'
                sh 'ls -lrt'
                echo 'Start - Push image to Docker Hub'
                    sh '''#!/bin/sh
                        docker login $loginServer -u $dockerusername -p $dockerpassword
                    '''
                    script {
                        def image = docker.build imageWithTag
                        image.push()
                        echo 'Done pushing the image.'
                    }
 
            }
        }
 
        stage('deploy') {
            steps{
                withCredentials([kubeconfigFile(credentialsId: 'eks-kubeconfig-fego-dev', variable: 'KUBECONFIG')]){
                    sh '''
                        export KUBECONFIG=$KUBECONFIG
                        kubectl apply -f cicd/kubernetes/configMap.yaml
                        kubectl apply -f cicd/kubernetes/service.yaml
                        kubectl apply -f cicd/kubernetes/deployment.yaml
                    '''
                }
            }
        }
 
    }
 
}
