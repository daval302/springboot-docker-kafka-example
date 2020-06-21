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