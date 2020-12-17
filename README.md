# Example application for acceptance tests with Spring, Kafka and Cucumber
This is an example application accompanying this [blog post](https://digital.interhyp.de/) about Best Practices for acceptance tests with Cucumber

## Setup
This application can be run locally. Therefore, you need a running Kafka instance on localhost:9092 with a topic named "user-example-topic"

## Acceptance tests
The acceptance tests can be executed with the following command:

`gradlew acceptanceTest`
