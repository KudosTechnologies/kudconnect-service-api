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

  private ApiClient initClient(String accessToken) {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getInterceptors().add(new AuthorizationInterceptor(accessToken));

    ApiClient apiClient = new ApiClient(restTemplate);
    apiClient.setBasePath(testRestTemplate.getRootUri());
    return apiClient;
  }

  public final CandidatesApi getCandidatesApiWithAdminAccess() {
    return new CandidatesApi(initClient(TokenUtils.generateAdminJWT()));
  }

  public final CandidatesApi getCandidatesApiWithUserAccess() {
    return new CandidatesApi(initClient(TokenUtils.generateUserJWT()));
  }
}
