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
                sh "mvn install -Ddocker -Dbranch=${GIT_BRANCH}"
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
		sh 'echo ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQC9P4Nxsy9VA/Odrn39wwJh07MZY/rswV7KRO0AK8dqZ8QRe+t8qSvlWXKDIedtNaUto1803hBP7DN+WyjlunUiEh37ySxL7IKBGo47guASF1NUwtyCrz25l3hhQGybngwcF1UaoIEdT5Xry79jCPOnZMBSjFlS5hKtEMtP2nz+CBnRRAtClywoaiBishx97xyRwWgq7vGWnZxo67oG+hSj1oBib4bGnOyECG3tmBK+pnanL6dISAtxtIM3WGknvyQFjX4tgASicAljbSOT9C9YwRg/XJU6GJeTa523VAIhGFh8ed3lHGtGVmfP9WnN1DxJLSBBOc7cpjub/yWm2PIW6pmonfQlQ/lhg/JOuokaLHUumiUUl0oyea2bg9n8WNV1FkgChPFSgO3GuiDAgGhqEBsOx2zLo8ucnf3w/YAVipCXg3+is62kS8Ifa4Jk59whXomTLauF2V2TBtVMfeGvIEtcXqV7BlIIOSFJNrPuFqmCgU0nc2Qg545fdSOPpyE= onkar@Onkars-MacBook-Pro.local >> /home/ec2-user/.ssh/authorized_keys'
                sh 'echo \$(${ECR_LOGIN}) > ${GIT_BRANCH}.sh'
                sh 'echo docker pull $REPO:$GIT_BRANCH >> ${GIT_BRANCH}.sh'
                sh 'echo docker rm -f $PROJECT >> ${GIT_BRANCH}.sh'
                sh 'echo docker run -e TZ=$TIMEZONE --net=host -p 8085:8085 -d --name $PROJECT $REPO:$GIT_BRANCH >> ${GIT_BRANCH}.sh'
                sh 'cat ${GIT_BRANCH}.sh | ssh ${USER}@${GIT_BRANCH}.$MS_DOMAIN' 
            }
        }
    }
}
