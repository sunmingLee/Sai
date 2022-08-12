node {
  stage('========== Clone repository ==========') {
    checkout scm
  }
  stage('========== Build image ==========') {
    sh 'pwd'
    sh 'cd /var/lib/jenkins/workspace/JinkinsTest/FrontEnd'
    sh 'docker build -t jinjordan/frontend .'
  }
  stage('========== Push image ==========') {
    sh 'docker push jinjordan/frontend'
  }
}