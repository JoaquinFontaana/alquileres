FROM docker.io/maven:3.9.9-eclipse-temurin-17 AS compiler
WORKDIR /app/
COPY pom.xml .
COPY src/ src/
RUN mvn clean package

FROM docker.io/eclipse-temurin:17.0.15_6-jre-alpine-3.21 AS runner
COPY --from=compiler /app/target/alquileresMaria-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]
