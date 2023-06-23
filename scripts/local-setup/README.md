# Local Project Setup

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
