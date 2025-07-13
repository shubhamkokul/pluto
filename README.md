# pluto
Stock Alerting System

This project is a stock alerting system built using the [Quarkus](https://quarkus.io/) framework. It provides RESTful APIs for user management, authentication, and fetching stock quotes.

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [API Endpoints](#api-endpoints)
- [Prerequisites](#prerequisites)
- [Running the application in dev mode](#running-the-application-in-dev-mode)
- [Packaging and running the application](#packaging-and-running-the-application)
- [Creating a native executable](#creating-a-native-executable)
- [Related Guides](#related-guides)

## Features
- User creation and validation.
- JWT-based authentication token generation and validation.
- Fetching real-time stock quotes (requires authentication).

## Technologies Used
- [Quarkus](https://quarkus.io/)
- Java 21
- Maven
- [RESTEasy Reactive](https://quarkus.io/guides/resteasy-reactive)
- [Hibernate ORM with Panache](https://quarkus.io/guides/hibernate-orm-panache)
- [PostgreSQL](https://www.postgresql.org/)
- [Liquibase](https://www.liquibase.org/) for database migrations
- [SmallRye JWT](https://quarkus.io/guides/security-jwt) for authentication

## API Endpoints

The following endpoints are available:

### Authentication
- `POST /auth/token`: Create an authentication token.
  - **Request Body**: `CreateAuthTokenRequest`
- `GET /auth/token`: Validate an authentication token.
  - **Query Param**: `auth_token`

### User Management
- `POST /user`: Create a new user.
  - **Request Body**: `CreateUserRequest`
- `GET /user`: Validate if a user exists.
  - **Query Params**: `username`, `email`

### Stock Quotes
- `GET /quote/{ticker}`: Get a stock quote for a given ticker.
  - **Path Param**: `ticker`
  - **Note**: This endpoint requires a valid JWT authentication token.

## Prerequisites
- JDK 21+
- Maven 3.9.x+
- A running PostgreSQL instance.

Before running the application, make sure to configure the database connection in `src/main/resources/application.properties`.

```properties
# Example configuration
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=your-user
quarkus.datasource.password=your-password
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/pluto_db

# Enable liquibase
quarkus.liquibase.migrate-at-start=true
```

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/pluto-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Related Guides

- REST ([guide](https://quarkus.io/guides/rest)): A Jakarta REST implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.

## Provided Code

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
