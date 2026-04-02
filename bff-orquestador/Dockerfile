FROM amazoncorretto:17-alpine
COPY target/bff-orquestador-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]