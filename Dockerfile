FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

COPY target/*.jar app.jar

RUN rm -f /usr/bin/pebble || true

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

