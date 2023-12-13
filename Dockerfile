FROM eclipse-temurin:17-jdk-alpine
LABEL authors="daniyar"
COPY target/*.jar  app.jar
ENTRYPOINT exec java $JAVA_OPTS -Dserver.port=$PORT -jar /app.jar
