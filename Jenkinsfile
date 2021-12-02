pipeline {
    agent any

    environment {
        PROJECT = "teamteach-files"
        USER = "ec2-user"
        AWS_ACCOUNT = sh(script: 'aws sts get-caller-identity --query Account --output text', , returnStdout: true).trim()
        AWS_REGION = sh(script: 'aws configure get region', , returnStdout: true).trim()
        REPO = "${AWS_ACCOUNT}.dkr.ecr.${AWS_REGION}.amazonaws.com/${PROJECT}"
        ECR_LOGIN = "aws ecr get-login --no-include-email --region $AWS_REGION"
    }
    
    stages{
        stage('Build') {
            when { anyOf {
                expression { env.GIT_BRANCH == env.BRANCH_ONE }
                expression { env.GIT_BRANCH == env.BRANCH_TWO }
            } }
            steps {
                sh 'rm -rf /var/lib/jenkins/workspace'
            }
        }
    }
}
