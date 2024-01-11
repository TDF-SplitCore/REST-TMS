FROM eclipse-temurin:17-jdk-alpine
COPY target/*.jar  app.jar
ENTRYPOINT java -jar -DDB_U=$DB_U -DDB_P=$DB_P -DDB_B=$DB_B app.jar