pipeline {
    agent any
    environment {
        PROJECT = "teamteach-files"
        USER = "ec2-user"
        REGION = "$REGION"
    }
    stages{
        stage('Build') {
            when { anyOf {
                expression { env.GIT_BRANCH == $BRANCH_ONE }
                expression { env.GIT_BRANCH == $BRANCH_TWO }
            } }
            steps {
                sh 'echo $GIT_BRANCH'
                sh "cp src/main/resources/application-${GIT_BRANCH}.properties src/main/resources/application.properties"
                sh "mvn install -Ddocker -Dbranch=${GIT_BRANCH}"
            }
        }
        stage('Push to ECR') {
            when { anyOf {
                expression { $GIT_BRANCH == $BRANCH_ONE }
                expression { $GIT_BRANCH == $BRANCH_TWO }
            } }
            steps {
                sh 'docker tag ${PROJECT}:${GIT_BRANCH} ${AWS_REPO}/${PROJECT}:${GIT_BRANCH}'
                sh '$(aws ecr get-login --no-include-email --region $REGION)'
                sh "docker push ${AWS_REPO}/${PROJECT}:${GIT_BRANCH}"
            }
        }
        stage('Pull & Run') {
            when { anyOf {
                expression { $GIT_BRANCH == $BRANCH_ONE }
                expression { $GIT_BRANCH == $BRANCH_TWO }
            } }
            steps {
                sh 'echo \'$(aws ecr get-login --no-include-email --region $REGION)\' > ${GIT_BRANCH}.sh'
                sh 'echo docker pull $AWS_REPO/$PROJECT:$GIT_BRANCH >> ${GIT_BRANCH}.sh'
                sh 'echo docker rm -f $PROJECT >> ${GIT_BRANCH}.sh'
                sh 'echo docker run -p 8085:8085 -d --name $PROJECT $AWS_REPO/$PROJECT:$GIT_BRANCH >> ${GIT_BRANCH}.sh'
                sh 'cat ${GIT_BRANCH}.sh | ssh ${USER}@$GIT_BRANCH.$DOMAIN' 
            }
        }
        stage('Cleanup') {
            when { anyOf {
                expression { $GIT_BRANCH == $BRANCH_ONE }
                expression { $GIT_BRANCH == $BRANCH_TWO }
            } }
            steps {
                sh 'ssh ${USER}@$GIT_BRANCH.$DOMAIN \'$(aws ecr get-login --no-include-email --region $REGION) ; docker image prune -a \''
            }
        }
    }
}
