# 배포 서버에 필요한 자바 버전을 넣어줍니다.
FROM openjdk:8

# 로컬에서 빌드한 jar 파일의 경로를 넣어줍시다.
ARG JAR_FILE=build/libs/*.jar

# 해당 jar 파일을 컨테이너의 app.jar 파일로 복사합니다.
COPY ${JAR_FILE} app.jar

# 컨테이너가 시작되면 실행 될 스크립트입니다.
ENTRYPOINT ["java","-jar","/app.jar"]