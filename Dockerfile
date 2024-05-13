FROM openjdk:17

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

#COPY target/cloud-tech-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8000-8089

ENTRYPOINT ["java", "-jar", "/app.jar"]
