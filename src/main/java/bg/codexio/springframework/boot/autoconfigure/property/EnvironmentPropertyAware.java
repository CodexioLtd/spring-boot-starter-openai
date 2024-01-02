package bg.codexio.springframework.boot.autoconfigure.property;


import bg.codexio.ai.openai.api.payload.environment.AvailableEnvironmentVariables;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;

import java.util.Objects;

@RequiredArgsConstructor
public class EnvironmentPropertyAware
        implements PropertyAware {

    private final OpenAIProperties properties;

    @Override
    public String getProperty(String key) {
        return switch (AvailableEnvironmentVariables.valueOf(key)) {
            case OPENAI_API_BASE_URL -> properties.getBaseUrl();
            case OPENAI_ORG_ID -> properties.getOrganizationId();
            case OPENAI_API_KEY -> properties.getApiKey();
            case OPENAI_LOGGING_ENABLED -> properties.getLogging()
                                                     .isEnabled()
                                           ? "true"
                                           : null;
            case OPENAI_LOGGING_LEVEL ->
                    String.valueOf(Objects.requireNonNullElse(
                            this.properties.getLogging()
                                           .getLevel(),
                            LogLevel.DEBUG
                    ));
        };
    }
}
