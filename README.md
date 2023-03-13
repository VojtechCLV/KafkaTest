# KafkaTest

Producer and Consumer of Kafka messages, uses Person object (generated from schema) as value

NOTE: Kafka, Zookeeper, and Schema Registry need to be running (locally or in Docker)

### Instructions

With kafka-producer running, messages (JSON containing `String "name"` and `Integer "age"`) can be published via `POST` at
```
http://localhost:8080/kafka/publish
```
It is also possible to trigger automatic sending of messages (10 messages with 10 second delay between each) by hitting `/kafka/start` endpoint
```
http://localhost:8080/kafka/start
```
These messages will be automatically consumed by kafka-consumer, their content will be displayed in logs