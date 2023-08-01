package ro.kudostech.kudconnect.keycloakconfigurator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KeycloakInitializerRunner implements CommandLineRunner {

  private static final String KEYCLOAK_SERVER_URL = "http://localhost:9080";
  private static final String REALM_NAME = "kudconnect";
  private static final String CLIENT_ID = "kudconnect-webapp";
  private static final String REDIRECT_URL = "http://localhost:3000/*";
  private static final List<UserPass> USER_LIST =
      Arrays.asList(new UserPass("admin", "admin"), new UserPass("user", "user"));
  private static final String ROLE_USER = "user";
  private static final String ROLE_ADMIN = "admin";

  private record UserPass(String username, String password) {}

  private final Keycloak keycloakAdmin;

  @Override
  public void run(String... args) {
    log.info("Initializing '{}' realm in Keycloak ...", REALM_NAME);

    Optional<RealmRepresentation> representationOptional =
        keycloakAdmin.realms().findAll().stream()
            .filter(r -> r.getRealm().equals(REALM_NAME))
            .findAny();
    if (representationOptional.isPresent()) {
      log.info("Removing already pre-configured '{}' realm", REALM_NAME);
      keycloakAdmin.realm(REALM_NAME).remove();
    }

    // Realm
    RealmRepresentation realmRepresentation = new RealmRepresentation();
    realmRepresentation.setRealm(REALM_NAME);
    realmRepresentation.setEnabled(true);
    realmRepresentation.setRegistrationAllowed(true);

    // Client
    ClientRepresentation clientRepresentation = new ClientRepresentation();
    clientRepresentation.setClientId(CLIENT_ID);
    clientRepresentation.setDirectAccessGrantsEnabled(true);
    clientRepresentation.setPublicClient(true);
    clientRepresentation.setRedirectUris(List.of(REDIRECT_URL));
    clientRepresentation.setDefaultRoles(new String[] {ROLE_USER});
    realmRepresentation.setClients(List.of(clientRepresentation));

    // Users
    List<UserRepresentation> userRepresentations =
        USER_LIST.stream()
            .map(
                userPass -> {
                  // User Credentials
                  CredentialRepresentation credentialRepresentation =
                      new CredentialRepresentation();
                  credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
                  credentialRepresentation.setValue(userPass.password());

                  // User
                  UserRepresentation userRepresentation = new UserRepresentation();
                  userRepresentation.setUsername(userPass.username());
                  userRepresentation.setEnabled(true);
                  userRepresentation.setCredentials(List.of(credentialRepresentation));
                  userRepresentation.setClientRoles(getClientRoles(userPass));

                  return userRepresentation;
                })
            .toList();
    realmRepresentation.setUsers(userRepresentations);

    // Create Realm
    keycloakAdmin.realms().create(realmRepresentation);

    // Testing
    UserPass admin = USER_LIST.get(0);
    log.info("Testing getting token for '{}' ...", admin.username());

    Keycloak keycloakMovieApp =
        KeycloakBuilder.builder()
            .serverUrl(KEYCLOAK_SERVER_URL)
            .realm(REALM_NAME)
            .username(admin.username())
            .password(admin.password())
            .clientId(CLIENT_ID)
            .build();

    log.info(
        "'{}' token: {}",
        admin.username(),
        keycloakMovieApp.tokenManager().grantToken().getToken());
    log.info("'{}' initialization completed successfully!", REALM_NAME);
  }

  private Map<String, List<String>> getClientRoles(UserPass userPass) {
    List<String> roles = new ArrayList<>();
    roles.add(ROLE_USER);
    if ("admin".equals(userPass.username())) {
      roles.add(ROLE_ADMIN);
    }
    return Map.of(CLIENT_ID, roles);
  }
}
