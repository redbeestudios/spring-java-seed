FROM openjdk:11.0.5-jdk-slim as builder

COPY . /app
WORKDIR /app
RUN ./gradlew clean build --info --stacktrace --no-daemon
#RUN ./gradlew clean build -x test --info --stacktrace --no-daemon

FROM openjdk:11.0.5-jre-slim

COPY --from=builder /app/build/libs/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-server", "-jar", "/app/app.jar"]
