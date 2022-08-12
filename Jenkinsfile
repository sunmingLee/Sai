node {
  stage('========== Clone repository ==========') {
    checkout scm
  }
  stage('========== Build image ==========') {
    sh 'cd ./Frontend'
    sh 'pwd'
    app = docker.build("jinjordan/frontend")
  }
  stage('========== Push image ==========') {
    docker.withRegistry('https://registry.hub.docker.com', 'Jinkins_Docker') {
      app.push("${env.BUILD_NUMBER}")
      app.push("latest")
    }
  }
}