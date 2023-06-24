package ro.kudostech.kudconnect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ro.kudostech.kudconnect.api.ApiClient;
import ro.kudostech.kudconnect.api.client.CandidatesApi;

@Component
public class TestContext {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private ApiClient initClient() {
    ApiClient apiClient =
        new ApiClient(new RestTemplate(new HttpComponentsClientHttpRequestFactory()));
    apiClient.setBasePath(testRestTemplate.getRootUri());
        return apiClient;
    }

    public final CandidatesApi getCandidatesApi() {
        return new CandidatesApi(initClient());
    }
}
