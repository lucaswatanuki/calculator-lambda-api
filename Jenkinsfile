pipeline {
    agent any

    environment {
        AWS_ACCESS_KEY_ID = credentials('aws-access-key')
        AWS_SECRET_ACCESS_KEY = credentials('aws-secret-key')
    }

    tools {
        maven '3.8.6'
    }

	stages {
		stage("Build") {
			steps {
				sh 'mvn clean compile -Dmaven.test.skip=true'
			}
		}
        stage("Unit test") {
            steps {
                echo 'To be configured...'
            }
        }
        stage("Publish") {
            steps {
                sh 'mvn package -Dmaven.test.skip=true'
            }
            post {
                success {
                    archiveArtifacts 'target/*.jar'
                    sh 'aws configure set region sa-east-1'
                    sh 'aws lambda update-function-code --function-name getInstallments --zip-file fileb://target/calculator-0.0.1-SNAPSHOT-aws.jar'
                    cleanWs()
                }
            }
        }
	}
}