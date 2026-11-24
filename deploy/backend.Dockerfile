FROM eclipse-temurin:25-jdk AS builder
WORKDIR /workspace

COPY gradle gradle
COPY gradlew build.gradle.kts settings.gradle.kts ./

COPY relay-app relay-app
COPY relay-core relay-core

RUN chmod +x gradlew && ./gradlew :relay-app:bootJar --no-daemon

RUN rm -f /workspace/relay-app/build/libs/*-plain.jar

FROM eclipse-temurin:25-jre
WORKDIR /workspace

COPY --from=builder /workspace/relay-app/build/libs/*.jar /workspace/app.jar

USER 1000

ENTRYPOINT ["java", "-jar", "app.jar"]
