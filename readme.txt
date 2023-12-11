# E-Commerce Backend

This is a Java project built with Maven. It serves as an e-commerce backend with various controller endpoints. It uses the Lombok library for boilerplate code reduction and has a structured approach to handle different environments with the help of properties files.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

## Prerequisites

- Java 17
- Maven (You can use the provided Maven wrapper)

## Installing

1. Clone the [repository](https://github.com/NY1105/e-commerce)
2. Navigate into the cloned repository
3. Use the Maven wrapper to install dependencies and build the project: `mvn install`
4. Start the server: `mvn spring-boot:run`

## Running the tests

The tests can be run using the following command: `mvn test`

## Controller Endpoints

This project serves as an e-commerce backend with the following endpoints:

- `/products`: Fetch all products
- `/products/{id}`: Fetch a specific product
- `/users`: Fetch all users (Admin Only)
- `/users/{id}`: Fetch a specific user
- `/orders`: Fetch all orders
- `/orders/{id}`: Fetch a specific order

## Built With

- Java
- Maven
- Lombok
- Spring Boot
- Hibernate
- JUnit
- Mockito
- MySql
