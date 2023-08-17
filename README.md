# Kudconnect

## Description

Welcome to our project template for Modular Monolith Architecture! This template is designed to help you quickly get started with building modular applications following the Ports and Adapters pattern at module level. It has been carefully crafted with best practices that encourages clean and maintainable code.

### Key Features

- **Modular Monolith:** The application is organized into separate modules, each encapsulating distinct business capabilities. This structure enables clear separation of concerns while maintaining the simplicity of a monolithic application.
- **Ports and Adapters:** Also known as Hexagonal Architecture, this design principle isolates the application's core logic from specific technology implementations (like databases, UI, or third-party integrations). This makes the code more portable and easier to test, as these external concerns can be plugged in or unplugged without affecting the core logic.
- **API-First Approach:** We believe in designing the API before writing the code. This means you can expect a well-documented, intuitive API that forms a contract guaranteeing the behavior of the application.
- **Unit & Acceptance Tests:** Our project template includes examples of both unit tests (which test individual components in isolation) and acceptance tests (which test the system end-to-end). These examples are designed to help you write your own tests and promote a Test-Driven Development (TDD) approach.

> Please Also check [Project Wiki](https://github.com/KudosTechnologies/kudconnect/wiki) for more details and references.

## Getting Started

To get started, simply clone this repository and follow the setup instructions. This template is a living document and will continue to be updated with additional features and improvements. We look forward to your contributions and feedback!

Remember, the purpose of this project is to serve as a guideline and starting point. Feel free to modify it according to the needs of your own project and team. Happy coding!

## Local Setup

### Build Project

- Publish api specification in maven local repository

```bash
cd kudconnect-service-api
./gradlew publishToMavenLocal
```

- Build Service

```bash
cd kudconnect-service
./gradlew clean build 
```

### Start External Services

For now external services are consisted of:
- Postgress Database
- Keycloak

Navigate to `scripts/local-setup` directory and run:

```bash
./local-setup.sh restart db
./local-setup.sh restart keycloak --rebuild
```

As you can observe we start `keycloak` with a `--rebuild` flag. This is because we have extended the default `jboss/keycloak` image with our own custom provider `kudconnect-keycloak-custom-provider`.

### Start Service

- Start `kudconnect-service` by running:

```bash
cd kudconnect-service
./gradlew bootRun
```

Or you can start the service from your IDE by running the Application main class.

- Start `kudconnect-webapp-client` by running:

```bash
cd kudconnect-webapp-client
npm install
npm start
```

Keycloak Admin Console can be accessed with this [link](http://127.0.0.1:9080): login:admin | pass:admin

Keycloak will pre-load `kudconnect` realm with following details:

- clientId: kudconnect-webapp
- clientId: kudconnect-client
- roles: ["admin", "user"]
- users:
    - username: admin@test.com, password: admin
    - username: user@test.com, password: user

Example request for obtaining a token from outside the Docker container:

```bash
curl --location 'http://localhost:9080/realms/kudconnect/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'client_id=kudconnect-webapp' \
--data-urlencode 'username=admin@test.com' \
--data-urlencode 'password=admin
```








