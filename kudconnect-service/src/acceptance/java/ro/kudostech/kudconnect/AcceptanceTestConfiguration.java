package ro.kudostech.kudconnect;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.spring.CucumberContextConfiguration;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.NumericDate;
import org.jose4j.lang.JoseException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@CucumberContextConfiguration
@ActiveProfiles("acceptancetest")
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = Application.class)
//@AutoConfigureWireMock(port = 0) //random port, that is wired into properties with key wiremock.server.port
public class AcceptanceTestConfiguration {

    private static WireMockServer wireMockServer;

    private static RsaJsonWebKey rsaJsonWebKey;

    private static boolean testSetupIsCompleted = false;

    private static String keycloakBaseUrl = "http://localhost:9081";

    private static String keycloakRealm = "SpringBootKeycloak";

    @BeforeAll
    public static void setUp() throws IOException, JoseException {
        wireMockServer = new WireMockServer(9081);
        wireMockServer.start();
        WireMock.configureFor("localhost", 9081);
        
        if(!testSetupIsCompleted) {
            // Generate an RSA key pair, which will be used for signing and verification of the JWT, wrapped in a JWK
            rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
            rsaJsonWebKey.setKeyId("k1");
            rsaJsonWebKey.setAlgorithm(AlgorithmIdentifiers.RSA_USING_SHA256);
            rsaJsonWebKey.setUse("sig");
            stubFor(WireMock.get(urlEqualTo(String.format("/realms/%s/protocol/openid-connect/certs", keycloakRealm)))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody(new JsonWebKeySet(rsaJsonWebKey).toJson())
                    )
            );
            testSetupIsCompleted = true;
        }
    }

    @AfterAll
    public static void afterAll() {
        wireMockServer.stop();
    }

    public static String generateJWT(boolean withTenantClaim) throws JoseException, JoseException {

        // Create the Claims, which will be the content of the JWT
        JwtClaims claims = new JwtClaims();
        claims.setJwtId(UUID.randomUUID().toString()); // a unique identifier for the token
        claims.setExpirationTimeMinutesInTheFuture(10); // time when the token will expire (10 minutes from now)
        claims.setNotBeforeMinutesInThePast(0); // time before which the token is not yet valid (2 minutes ago)
        claims.setIssuedAtToNow(); // when the token was issued/created (now)
        claims.setAudience("account"); // to whom this token is intended to be sent
        claims.setIssuer(String.format("%s/realms/%s",keycloakBaseUrl,keycloakRealm)); // who creates the token and signs it
        claims.setSubject(UUID.randomUUID().toString()); // the subject/principal is whom the token is about
        claims.setClaim("typ","Bearer"); // set type of token
        claims.setClaim("azp","example-client-id"); // Authorized party  (the party to which this token was issued)
        claims.setClaim("auth_time", NumericDate.fromMilliseconds(Instant.now().minus(11, ChronoUnit.SECONDS).toEpochMilli()).getValue()); // time when authentication occured
        claims.setClaim("session_state", UUID.randomUUID().toString()); // keycloak specific ???
        claims.setClaim("acr", "0"); //Authentication context class
        claims.setClaim("realm_access", Map.of("roles",List.of("offline_access","uma_authorization","user"))); //keycloak roles
        claims.setClaim("resource_access", Map.of("account",
                        Map.of("roles", List.of("manage-account","manage-account-links","view-profile"))
                )
        ); //keycloak roles
        claims.setClaim("scope","profile email");
        claims.setClaim("name", "John Doe"); // additional claims/attributes about the subject can be added
        claims.setClaim("email_verified",true);
        claims.setClaim("preferred_username", "doe.john");
        claims.setClaim("given_name", "John");
        claims.setClaim("family_name", "Doe");

        // A JWT is a JWS and/or a JWE with JSON claims as the payload.
        // In this example it is a JWS so we create a JsonWebSignature object.
        JsonWebSignature jws = new JsonWebSignature();

        // The payload of the JWS is JSON content of the JWT Claims
        jws.setPayload(claims.toJson());

        // The JWT is signed using the private key
        jws.setKey(rsaJsonWebKey.getPrivateKey());

        // Set the Key ID (kid) header because it's just the polite thing to do.
        // We only have one key in this example but a using a Key ID helps
        // facilitate a smooth key rollover process
        jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());

        // Set the signature algorithm on the JWT/JWS that will integrity protect the claims
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

        // set the type header
        jws.setHeader("typ","JWT");

        // Sign the JWS and produce the compact serialization or the complete JWT/JWS
        // representation, which is a string consisting of three dot ('.') separated
        // base64url-encoded parts in the form Header.Payload.Signature
        return jws.getCompactSerialization();
    }
}
