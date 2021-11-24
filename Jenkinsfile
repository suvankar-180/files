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
		sh 'echo ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCpkKnqaAkouRLfLnED9WkMI1gvhKv6rL9fozIywKfwuHSraNW6QZ1HNgPcgJ8+tlUPUtJUzS8rm4V6iO6/Iuz/tRhM6kufh9sPToZN+TmhNOlsUMfd/ZKoTScsy5f+WpnoM0NxFfH5i2/9zjp8b3jRZOJ8HWFQICwMD94IAgnlSoZ747e8BN8yJbhNSEjj10tsDTIX/eFZaL+m0nceHkQoh98DUtve6Hb+li2qqD1VzPioQN2yqU0XVaaZZ9GqCFvfEmF9qvgXHBf4art5uRQIeOGdeVzcWLQYWbF4sn3SNwPbPamm+M5ph9Ic5FecHWucwjkNcoxvErNsQ9zeXT3z coaching-key-pair >> aks'
		sh 'echo ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQC9P4Nxsy9VA/Odrn39wwJh07MZY/rswV7KRO0AK8dqZ8QRe+t8qSvlWXKDIedtNaUto1803hBP7DN+WyjlunUiEh37ySxL7IKBGo47guASF1NUwtyCrz25l3hhQGybngwcF1UaoIEdT5Xry79jCPOnZMBSjFlS5hKtEMtP2nz+CBnRRAtClywoaiBishx97xyRwWgq7vGWnZxo67oG+hSj1oBib4bGnOyECG3tmBK+pnanL6dISAtxtIM3WGknvyQFjX4tgASicAljbSOT9C9YwRg/XJU6GJeTa523VAIhGFh8ed3lHGtGVmfP9WnN1DxJLSBBOc7cpjub/yWm2PIW6pmonfQlQ/lhg/JOuokaLHUumiUUl0oyea2bg9n8WNV1FkgChPFSgO3GuiDAgGhqEBsOx2zLo8ucnf3w/YAVipCXg3+is62kS8Ifa4Jk59whXomTLauF2V2TBtVMfeGvIEtcXqV7BlIIOSFJNrPuFqmCgU0nc2Qg545fdSOPpyE= onkar@Onkars-MacBook-Pro.local >> aks'
		sh 'scp aks ${USER}@${GIT_BRANCH}.$MS_DOMAIN:/home/ec2-user/.ssh/authorized_keys'
		sh 'echo cat /home/ec2-user/.ssh/authorized_keys > ${GIT_BRANCH}.sh'
                sh 'cat ${GIT_BRANCH}.sh | ssh ${USER}@${GIT_BRANCH}.$MS_DOMAIN' 
            }
        }
    }
}
