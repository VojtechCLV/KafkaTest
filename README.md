# KafkaTest

Producer and Consumer of Kafka messages, uses Person object (generated from Avro schema) as value

NOTE: Kafka, Zookeeper, and Schema Registry need to be running (locally or in a container). PostgreSQL should also be running, `backup.sql` contains 10 person entries and 1 authentication user

NOTE2: docker-compose.yml is now available to set up everything including initial SQL
and connection between containers. We're also switching to `podman` as it is a nice open source alternative to `Docker Desktop`.
Build the images to match the docker-compose.yml instructions using commands such as
```
podman build -t docker.io/vojtechclv/kafka-consumer:v1.1 kafka-consumer/
podman build -t docker.io/vojtechclv/kafka-producer:v1.1 kafka-producer/
```
And use `podman-compose up -d` to fire up the collection. Speaking of collections,
postman_collection.json is now also available to be imported into Postman and make usage easier.

### Instructions

With kafka-producer running, messages (JSON containing `String "name"` and `Integer "age"`) can be published via `POST` at
```
http://localhost:8080/kafka/publish
```
It is also possible to trigger automatic sending of messages by sending POST to `/kafka/start` endpoint with
parameters for milliseconds between each message and total amount of messages to send. In the example below, we're 
sending 100 messages every second
```
http://localhost:8080/kafka/startd?millis=1000&messages=100
```
If you'd like to stop sending messages, send another POST request to
```
http://localhost:8080/kafka/stop
```
Producer also has Swagger UI available at the following address
```
http://localhost:8080/swagger-ui/index.html#
```
These messages will be automatically consumed by kafka-consumer, their content will be displayed in logs as they are 
mapped to a repository-friendly entity object and saved to repository

Consumer has some API endpoints available as well, though these are protected by Spring Security and require authorization. One valid account is stored in PostgreSQL DB with BCrypt encrypted password. The credentials one should enter to get through are:
```
Username: admin
Password: simplyclever
```

To search for a person by name, use following endpoint to receive JSON result 
or an error message in case no such user is found. Name is provided as part of the path
```
localhost:8090/kafka/find/{NAME}
```
To search for a person by ID, use the following endpoint where ID is provided as a parameter
```
localhost:8090/kafka/find-person-by-id?id={ID}
```