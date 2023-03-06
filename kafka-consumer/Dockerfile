FROM maven:3.9.0-eclipse-temurin-17-alpine AS MAVEN_BUILD
COPY ./ ./
RUN mvn clean package
FROM openjdk:17-jdk-slim
COPY --from=MAVEN_BUILD /target/kafka-consumer-0.0.1-SNAPSHOT.jar /kafka-consumer.jar
CMD ["java", "-jar", "/kafka-consumer.jar"]