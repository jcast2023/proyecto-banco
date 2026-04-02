FROM amazoncorretto:17-alpine
COPY target/ms-productos-*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app.jar"]