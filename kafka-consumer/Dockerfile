FROM maven:3.9.1-eclipse-temurin-17-alpine AS MAVEN_BUILD
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD pom.xml $HOME
RUN mvn verify --fail-never
ADD . $HOME
RUN mvn clean package

FROM gcr.io/distroless/java17-debian11
COPY --from=MAVEN_BUILD /usr/app/target/kafka-consumer-0.0.1-SNAPSHOT.jar /app/kafka-consumer.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "/app/kafka-consumer.jar"]