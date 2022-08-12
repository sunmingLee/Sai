node {
  stage('========== Clone repository ==========') {
    checkout scm
  }
  stage('========== Build image ==========') {
    sh 'pwd'
    sh 'cd /var/lib/jenkins/workspace/JinkinsTest/Frontend'
    sh 'docker build -f ../Frontend/Dockerfile -t jinjordan/frontend .'
  }
  stage('========== Push image ==========') {
    sh 'docker push jinjordan/frontend'
  }
}