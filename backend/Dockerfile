# 1) Build stage
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app

# 캐시 최적화
COPY build.gradle settings.gradle gradle.properties* gradlew ./
COPY gradle ./gradle

# 소스
COPY src ./src

# 빌드
RUN sed -i 's/\r$//' gradlew \
 && chmod +x gradlew \
 && ./gradlew clean bootJar -x test

# 2) Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
