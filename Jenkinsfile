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
                sh 'echo $REPO'
                sh 'echo $GIT_BRANCH'
                sh 'cp src/main/resources/${APP_DOMAIN}.properties src/main/resources/application.properties'
                sh 'cat src/main/resources/application.properties'
                sh "docker image prune -af"
		sh "docker images"
                sh "mvn install -Ddocker -Dbranch=${GIT_BRANCH}"
		sh "docker images"
            }
        }
        stage('Push to ECR') {
            when { anyOf {
                expression { env.GIT_BRANCH == env.BRANCH_ONE }
                expression { env.GIT_BRANCH == env.BRANCH_TWO }
            } }
            steps {
                sh 'docker tag ${PROJECT}:${GIT_BRANCH} ${REPO}:${GIT_BRANCH}'
                sh '$($ECR_LOGIN)'
                sh "docker push ${REPO}:${GIT_BRANCH}"
            }
        }
        stage('Pull & Run') {
            when { anyOf {
                expression { env.GIT_BRANCH == env.BRANCH_ONE }
                expression { env.GIT_BRANCH == env.BRANCH_TWO }
            } }
            steps {
                sh 'echo \$(${ECR_LOGIN}) > ${GIT_BRANCH}.sh'
                sh 'echo docker pull $REPO:$GIT_BRANCH >> ${GIT_BRANCH}.sh'
                sh 'echo docker rm -f $PROJECT >> ${GIT_BRANCH}.sh'
                sh 'echo docker run -e TZ=$TIMEZONE --net=host -p 8085:8085 -d --name $PROJECT $REPO:$GIT_BRANCH >> ${GIT_BRANCH}.sh'
                sh 'cat ${GIT_BRANCH}.sh | ssh ${USER}@${GIT_BRANCH}.$MS_DOMAIN' 
            }
        }
    }
}
