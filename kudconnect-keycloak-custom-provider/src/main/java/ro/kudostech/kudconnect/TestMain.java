package ro.kudostech.kudconnect;

import java.util.Optional;

public class TestMain {
  public static void main(String[] args) {
    ExternalResourceClient externalResourceClient = new ExternalResourceClient();
//    String token = externalResourceClient.fetchFreshToken();
    Optional<String> userId =
        externalResourceClient.fetchUserIdFromKudconnectServiceInternal("admin@test.com");
    System.out.println(userId.get());
  }
}
