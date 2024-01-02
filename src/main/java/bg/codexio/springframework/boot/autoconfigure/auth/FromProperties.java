package bg.codexio.springframework.boot.autoconfigure.auth;

import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.sdk.auth.SdkAuth;
import bg.codexio.springframework.boot.autoconfigure.property.OpenAIProperties;

public class FromProperties
        implements SdkAuth {

    private final OpenAIProperties properties;

    private FromProperties(OpenAIProperties properties) {
        this.properties = properties;
    }

    public static FromProperties parse(OpenAIProperties properties) {
        return new FromProperties(properties);
    }

    @Override
    public ApiCredentials credentials() {
        return new ApiCredentials(
                properties.getApiKey(),
                properties.getOrganizationId(),
                properties.getBaseUrl()
        );
    }
}
