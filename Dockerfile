# Stage 1: Build the JAR file
FROM gradle:7.5.1-jdk17 AS build
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew build --no-daemon

# Stage 2: Create the final image with the JAR
FROM openjdk:latest
WORKDIR /app
COPY --from=build /app/build/libs/habit-1.0.jar app.jar
EXPOSE 6000
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
