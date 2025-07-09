FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/medic-recruitment-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["sh", "-c", "java -jar app.jar --spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]
