package ro.kudostech.kudconnect;

import java.util.ArrayList;
import java.util.List;
import org.jboss.logging.Logger;
import org.keycloak.models.ClientSessionContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.models.UserSessionModel;
import org.keycloak.protocol.oidc.mappers.*;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.representations.IDToken;

public class CustomProtocolMapper extends AbstractOIDCProtocolMapper implements OIDCAccessTokenMapper,
  OIDCIDTokenMapper, UserInfoTokenMapper {
    private static final Logger logger = Logger.getLogger(CustomProtocolMapper.class);

    private static final String USER_ID_TOKEN_CLAIM_KEY = "user_id";

    public static final String PROVIDER_ID = "custom-protocol-mapper";

    private static final List<ProviderConfigProperty> configProperties = new ArrayList<>();
    private ExternalResourceClient externalResourceClient = new ExternalResourceClient();

    static {
        OIDCAttributeMapperHelper.addTokenClaimNameConfig(configProperties);
        OIDCAttributeMapperHelper.addIncludeInTokensConfig(configProperties, CustomProtocolMapper.class);
    }

    @Override
    public String getDisplayCategory() {
        return "Token Mapper";
    }

    @Override
    public String getDisplayType() {
        return "Custom Token Mapper";
    }

    @Override
    public String getHelpText() {
        return "Adds a Baeldung text to the claim";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configProperties;
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    protected void setClaim(IDToken token, ProtocolMapperModel mappingModel,
      UserSessionModel userSession, KeycloakSession keycloakSession,
      ClientSessionContext clientSessionCtx) {
        String userId = externalResourceClient.fetchUserIdFromKudconnectServiceInternal().orElse(null);
        logger.info(String.format("Setting custom claim %s with value %s", USER_ID_TOKEN_CLAIM_KEY, userId));
        addClaim(USER_ID_TOKEN_CLAIM_KEY, userId, token);
//        OIDCAttributeMapperHelper.mapClaim(token, mappingModel, "Baeldung");
    }

    private void addClaim(final String key, final String value, final IDToken token) {
        token.getOtherClaims().put(key, value);
    }

    private void addUserIdClaim(final String key, final String value, final IDToken token) {
        token.getOtherClaims().put(key, value);
    }
}