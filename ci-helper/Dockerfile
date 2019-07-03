FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=../target/jwt-security-0.0.1-SNAPSHOT.jar
ADD ../target/jwt-security-0.0.1-SNAPSHOT.jar jwt-security.jar
ENTRYPOINT ["java", "-jar", "jwt-security.jar"]