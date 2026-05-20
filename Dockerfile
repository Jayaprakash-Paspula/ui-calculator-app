FROM eclipse-temurin:17-jre-jammy

WORKDIR /appg

COPY target/*.jar calculator.jar

RUN rm -f /usr/bin/pebble || true

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "calculator.jar"]

