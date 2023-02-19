FROM eclipse-temurin:17-jdk-focal
WORKDIR /app
ARG JAR_FILE=IntegrationServer/target/IntegrationServer-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]
