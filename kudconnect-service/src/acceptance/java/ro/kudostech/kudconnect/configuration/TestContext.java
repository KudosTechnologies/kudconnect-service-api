package ro.kudostech.kudconnect.configuration;


import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ro.kudostech.kudconnect.api.ApiClient;
import ro.kudostech.kudconnect.api.client.CandidatesApi;
import ro.kudostech.kudconnect.step.AuthorizationInterceptor;
import ro.kudostech.kudconnect.utils.TokenUtils;

@Component
public class TestContext {

  private final TestRestTemplate testRestTemplate;

  public TestContext(TestRestTemplate testRestTemplate) {
    this.testRestTemplate = testRestTemplate;
  }

  private ApiClient initClient() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getInterceptors()
            .add(new AuthorizationInterceptor(TokenUtils
                    .generateJWT(WireMockConfigurations.keycloakBaseUrl,
                            WireMockConfigurations.KEYCLOAK_REALM)));

    ApiClient apiClient = new ApiClient(restTemplate);
    apiClient.setBasePath(testRestTemplate.getRootUri());
    return apiClient;
  }

  public final CandidatesApi getCandidatesApi() {
    return new CandidatesApi(initClient());
  }
}
