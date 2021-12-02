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
                sh 'rm -rf /var/lib/jenkins/workspace/tt-ms-java-journals'
                sh 'rm -rf /var/lib/jenkins/workspace/tt-ms-java-users'
                sh 'rm -rf /var/lib/jenkins/workspace/tt-ms-java-gateway'
                sh 'rm -rf /var/lib/jenkins/workspace/tt-ms-java-learnings'
                sh 'rm -rf /var/lib/jenkins/workspace/tt-ms-java-notifications'
                sh 'rm -rf /var/lib/jenkins/workspace/tt-ms-java-recommendations'
                sh 'rm -rf /var/lib/jenkins/workspace/tt-ms-java-profiles'
                sh 'rm -rf /var/lib/jenkins/workspace/tt-ms-java-commons'
                sh 'rm -rf /var/lib/jenkins/workspace/tt-ms-nodejs-pdf'
                sh 'rm -rf /var/lib/jenkins/workspace/tt-ms-nodejs-jobs'
                sh 'rm -rf /var/lib/jenkins/workspace/tt-ms-nodejs-contenthub'
                sh 'rm -rf /var/lib/jenkins/workspace/tt-app-react-admin
                sh 'rm -rf /var/lib/jenkins/workspace/tt-app-react-myfamilycoach
                sh 'rm -rf /var/lib/jenkins/workspace/tt-data-amq-rabbitmq'
                sh 'rm -rf /var/lib/jenkins/workspace/tt-data-amq-mongodb'
                sh 'rm -rf /var/lib/jenkins/workspace/tt-data-amq-mariadb'
            }
        }
    }
}
