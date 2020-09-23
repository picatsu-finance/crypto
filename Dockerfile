
FROM openjdk:11

ARG jar

WORKDIR /workspace/app

COPY $jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
