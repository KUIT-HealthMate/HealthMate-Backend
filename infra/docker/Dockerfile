FROM openjdk:17-jdk

# 빌드 시 전달된 JAR_FILE 경로를 ARG로 받음
ARG JAR_FILE=../../build/libs/*.jar

# API_KEY, URL, MODEL 환경 변수를 ARG로 받음
ARG API_KEY
ARG URL
ARG MODEL

# JAR_FILE을 컨테이너 내부의 app.jar로 복사
COPY ${JAR_FILE} app.jar

# API_KEY, URL, MODEL 환경 변수를 ENV로 설정하여 컨테이너 내에서 사용 가능하게 함
ENV API_KEY=${API_KEY}
ENV URL=${URL}
ENV MODEL=${MODEL}

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]
