[![CI](https://github.com/christian-draeger/hexagonal-architecture-example/actions/workflows/build.yml/badge.svg)](https://github.com/christian-draeger/hexagonal-architecture-example/actions/workflows/build.yml)
[![Codecov](https://img.shields.io/codecov/c/github/christian-draeger/hexagonal-architecture-example.svg)](https://app.codecov.io/gh/christian-draeger/hexagonal-architecture-example)

# Hexagonal Architecture Example (with Spring-Boot & Kotlin)
## !!! work in progress

### open TODOs:
* implement product service and use it to add product data to cart items
* clean up

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
