[![CI](https://github.com/christian-draeger/hexagonal-architecture-example/actions/workflows/build.yml/badge.svg)](https://github.com/christian-draeger/hexagonal-architecture-example/actions/workflows/build.yml)
[![Codecov](https://img.shields.io/codecov/c/github/christian-draeger/hexagonal-architecture-example.svg)](https://app.codecov.io/gh/christian-draeger/hexagonal-architecture-example)

# Hexagonal Architecture Example (with Spring-Boot & Kotlin)
## !!! work in progress

### open TODOs:
* implement product service and use it to add product data to cart items
* add detekt and ktlint for static code analysis
* add circuit breaker (resilience4j)
* add documentation about architectural decisions / project layout
* clean up

## project layout
* **domain**: can not see any other module, has no external dependencies and houses the business models and logic. Pure kotlin code
* **application**: can not see infrastructure module. defines the application ports. Ports can be composed / use each other. It is Describing the use cases of the service
* **infrastructure**: everything tech related like spring boot setup, persistence implementation, metrics, open api doc generation etc etc
* **#/src/test**: including e2e test that spin up the application as well as the database via docker compose during test runtime and is doing Blackbox tests

## development

### how to run
#### start everything via docker compose

    ./gradlew dockerCreateDockerfile
     docker compose up --build

#### start application via gradle

    docker compose up -d database
    ./gradlew bootRun 

#### start via intellij

    docker compose up -d database
    # start Application via IntelliJ

### useful endpoints
* http://localhost:8080/swagger-ui/index.html
* http://localhost:8080/actuator/health
* http://localhost:8080/actuator/prometheus

### Test Setup
* Unit tests - mainly to check the domain models and application ports
* Integration tests via mockMvc to test the application layer interface
* E2E tests that automatically spin-up everything via docker compose and check several endpoints of the applications API etc.
