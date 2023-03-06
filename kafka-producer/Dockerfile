FROM maven:3.9.0-eclipse-temurin-17-alpine AS MAVEN_BUILD
COPY ./ ./
RUN mvn clean package
FROM openjdk:17-jdk-slim
COPY --from=MAVEN_BUILD /target/kafka-producer-0.0.1-SNAPSHOT.jar /kafka-producer.jar
CMD ["java", "-jar", "/kafka-producer.jar"]