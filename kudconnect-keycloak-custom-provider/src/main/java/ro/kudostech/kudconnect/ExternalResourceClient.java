package ro.kudostech.kudconnect;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jboss.logging.Logger;

public class ExternalResourceClient {

  private static final Logger logger = Logger.getLogger(ExternalResourceClient.class);

  private static final String KUDCONNECT_SERVER_URL = System.getenv("KUDCONNECT_SERVER_URL");

  public String fetchFreshToken() throws IOException, InterruptedException {
    String url = "http://localhost:9080/realms/kudconnect/protocol/openid-connect/token";

    String formParams =
        String.join(
            "&",
            "grant_type=" + URLEncoder.encode("client_credentials", StandardCharsets.UTF_8),
            "client_id=" + URLEncoder.encode("keycloak-client", StandardCharsets.UTF_8),
            "client_secret=" + URLEncoder.encode("keycloak-dummy-secret", StandardCharsets.UTF_8));

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .POST(HttpRequest.BodyPublishers.ofString(formParams))
            .build();

    HttpResponse<String> response;
    response = client.send(request, HttpResponse.BodyHandlers.ofString());

    // Assuming that the response body contains a JSON object with an "access_token" field
    // Using a JSON library like Jackson or Gson to extract the access token would be more robust

    return response.body().split("\"access_token\":\"")[1].split("\"")[0];
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

  public Optional<String> fetchUserIdFromKudconnectServiceInternal(String email) {
    try {
      String token = fetchFreshToken();
      HttpRequest request =
          HttpRequest.newBuilder()
              .uri(URI.create(KUDCONNECT_SERVER_URL + "/user-details/" + email))
              .header("Authorization", "Bearer " + token)
              .method("GET", HttpRequest.BodyPublishers.noBody())
              .build();
      HttpResponse<String> response =
          java.net.http.HttpClient.newHttpClient()
              .send(request, HttpResponse.BodyHandlers.ofString());
      return Optional.of(response.body().split("\"id\":\"")[1].split("\"")[0]);
    } catch (Exception e) {
      logger.error("Error while fetching user id from kudconnect service", e);
      return Optional.empty();
    }
  }
}
