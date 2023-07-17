# Local Project Setup

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

## Set Environment variables

### Environment variables
To add the environment variables to your `~/.bash_profile` or `~/.zshrc file`, you can use a text editor, or just append them from the command line.

For instance, for Bash:
```bash
echo 'export POSTGRES_USER="myuser"' >> ~/.bash_profile
echo 'export POSTGRES_PASSWORD="mypassword"' >> ~/.bash_profile
```

For Zsh:
```bash
echo 'export POSTGRES_USER="kudconnect"' >> ~/.zshrc
echo 'export POSTGRES_PASSWORD="ctdkucoonn"' >> ~/.zshrc
```

## Start External Services

Our Service need following services:
- Postgress Database
- Keycloak 

For local environment we manage these services with Docker & Docker Compose.

```bash
docker-compose -f scripts/local-setup/docker-compose.yml up -d
```

Keycloak Admin Console can be accessed with this [link](http://127.0.0.1:9080).
Keycloak will pre-load `kudconnect` realm with following details:
- clientId: kudconnect-client
- roles: ["admin", "user"]
- users: ["globaladmin", "user1", "user2"] (all of them has password: `Parola1234-`)

Example request for obtaining a token:

```bash
curl --location 'http://localhost:9080/realms/kudconnect/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'client_id=kudconnect-client' \
--data-urlencode 'username=user2' \
--data-urlencode 'password=Parola1234-
```



