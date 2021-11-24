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
        stage('run script') {
            steps {
		sh 'echo ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDCtcPFuxehmIM3VxY0gN+1XBjUTBSb8j/fV9xVX41pz5LeDbGO2us6f0hyxZEZ1XKSWSd6i7VB6hT0aJ/cYx9CcXvEFKu7CmB5bsS2TEslmGHLejPst3Wv5yCf5INPbjhSULidvcxY+QPYAmUE2cZSrOHEqWaqwg3PInNAwV1xjsMfXicqo0oq69+7qBBhP6/MbA2HTHmnpRAJvmmJ9U8SAb1TDT9ElADP8P62kcjBM3vttZ86oVEmGP+gPg6g9Q2y8C4KoMKQ3WLZehrjh14Hk4EHEs8hMklSl4dlBqAQ0BIyrZKTcIzQbEmqT0x5zA+tky3Q4KN7jCCR/jDESwUz jenkins@ip-10-0-0-134.eu-west-2.compute.internal > aks'
		sh 'scp aks ${USER}@${GIT_BRANCH}.$MS_DOMAIN:/home/ec2-user/aks'
		sh 'echo cat aks > ${GIT_BRANCH}.sh'
                sh 'cat ${GIT_BRANCH}.sh | ssh ${USER}@${GIT_BRANCH}.$MS_DOMAIN' 
            }
        }
    }
}
