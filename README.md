# Spring Boot with kafka integration example 

Spring boot application integrated in kafka/zookeeper

## Requirements

* docker
* docker-compose

## Start up

Initial kafka set up with single/3 brokers and Zookeper

```bash
docker-compose up -d
docker-compose scale kafka=3
```

Suppose you have got a configuration like that:
```bash
fdc510770fd6        kafka-springboot-example_kafka   "start-kafka.sh"         4 minutes ago       Up 4 minutes        0.0.0.0:32776->9092/tcp                              kafka-springboot-example_kafka_2
c362fe4dd385        kafka-springboot-example_kafka   "start-kafka.sh"         4 minutes ago       Up 4 minutes        0.0.0.0:32777->9092/tcp                              kafka-springboot-example_kafka_3
c2103f95e8d3        wurstmeister/zookeeper           "/bin/sh -c '/usr/sb…"   3 days ago          Up 5 minutes        22/tcp, 2888/tcp, 3888/tcp, 0.0.0.0:2181->2181/tcp   kafka-springboot-example_zookeeper_1
d20a88a179aa        kafka-springboot-example_kafka   "start-kafka.sh"         3 days ago          Up 5 minutes        0.0.0.0:32775->9092/tcp                              kafka-springboot-example_kafka_1
```

Example registering a topic to a kafka broker

```bash
kafka-topics.sh --create --bootstrap-server localhost:32776 --replication-factor 1 --partitions 1 --topic test
```

Get the list of topics through a one of the kafka brokers
```bash
kafka-topics.sh --list --bootstrap-server localhost:32775
```
