FROM amazoncorretto:17-alpine
COPY target/ms-clientes-*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app.jar"]