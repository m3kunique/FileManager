FROM openjdk:17

WORKDIR /app

COPY target/CloudFileStorage-0.0.1-SNAPSHOT.jar /app/CloudFileStorage-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/app/CloudFileStorage-0.0.1-SNAPSHOT.jar"]