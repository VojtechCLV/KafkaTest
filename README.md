# KafkaTest

Producer and Consumer of Kafka messages

NOTE: Kafka (+Zookeeper) needs to be running (locally or in Docker)

### Instructions

With kafka-producer running, messages can be published via REST API at
```
http://localhost:8080/kafka/publish?message=MESSAGE_TEXT_HERE
```
These messages will be automatically consumed by kafka-consumer, their content will be displayed in logs