package ro.kudostech.kudconnect;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jboss.logging.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;

public class ExternalResourceClient {

  private static final Logger logger = Logger.getLogger(ExternalResourceClient.class);

  private static final String kudconnectServiceBaseUrl =  System.getenv("KUDCONNECT_SERVER_URL");

  private final ResteasyClient resteasyClientKudconnectService;

  private final ResteasyClient resteasyClient = newResteasyClient(10000);

  public ExternalResourceClient() {
    this(newResteasyClient(1000));
  }

  ExternalResourceClient(ResteasyClient resteasyClientKudconnectService) {
    this.resteasyClientKudconnectService = resteasyClientKudconnectService;
  }

  private static ResteasyClient newResteasyClient(final int readTimeout) {
    return new ResteasyClientBuilderImpl()
        .connectTimeout(1000, TimeUnit.MILLISECONDS)
        .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
        .build();
  }

  public Optional<String> fetchUserIdFromKudconnectServiceInternal(String email) {
    try {
      String keycloakAccessToken = fetchFreshToken();
      UserResponse userResponse =
          resteasyClientKudconnectService
              .target(kudconnectServiceBaseUrl + "/user-details/" + email)
              .request()
              .header(HttpHeaders.AUTHORIZATION, "Bearer " + keycloakAccessToken)
              .get(UserResponse.class);
      String userId = userResponse.getId();

      return Optional.ofNullable(userId);
    } catch (Exception exception) {
      logger.error("Error while fetching user id from kudconnect service", exception);
      return Optional.empty();
    }
  }

  public String fetchFreshToken() {
    return doTokenRequest(
            "http://localhost:9080/realms/kudconnect/protocol/openid-connect/token",
            new Form()
                .param("grant_type", "client_credentials")
                .param("client_id", "keycloak-client")
                .param("client_secret", "keycloak-dummy-secret"))
        .accessToken;
  }

  protected AccessTokenResponse doTokenRequest(String uri, Form form) {
    return resteasyClient
        .target(uri)
        .request()
        .post(Entity.form(form))
        .readEntity(AccessTokenResponse.class);
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  @NoArgsConstructor
  @AllArgsConstructor
  public static class AccessTokenResponse {
    @JsonAlias("access_token")
    private String accessToken;
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class UserResponse {
    private String id;
  }
}
