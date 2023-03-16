# KafkaTest

Producer and Consumer of Kafka messages, uses Person object (generated from Avro schema) as value

NOTE: Kafka, Zookeeper, and Schema Registry need to be running (locally or in Docker)

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

These messages will be automatically consumed by kafka-consumer, their content will be displayed in logs as they are 
mapped to a repository-friendly entity object and saved to repository