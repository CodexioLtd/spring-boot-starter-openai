package bg.codexio.springframework.boot.autoconfigure.property;

import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.sdk.auth.SdkAuth;
import bg.codexio.springframework.boot.autoconfigure.auth.FromProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.logging.LogLevel;

import java.util.concurrent.TimeUnit;

@Data
@ConfigurationProperties(prefix = "codexio.openai")
public class OpenAIProperties {

    /**
     * Gives the possibility to still retain values
     * from environment variables or openai-credentials.json
     */
    private Class<? extends SdkAuth> authType = FromProperties.class;
    private String apiKey;
    private String organizationId = "";
    private String baseUrl = ApiCredentials.BASE_URL;
    private OpenAILoggingProperties logging = new OpenAILoggingProperties();
    private OpenAIHttpProperties http = new OpenAIHttpProperties();

    @Data
    public static class OpenAILoggingProperties {
        private boolean enabled;
        private LogLevel level;
    }

    @Data
    public static class OpenAIHttpProperties {
        private OpenAIHttpTimeouts timeouts = new OpenAIHttpTimeouts();
        private OpenAIHttpConnectionPool connectionPool =
                new OpenAIHttpConnectionPool();

        @Data
        public static class OpenAIHttpTimeouts {
            private OpenAIHttpDuration call = new OpenAIHttpDuration();
            private OpenAIHttpDuration connect = new OpenAIHttpDuration();
            private OpenAIHttpDuration read = new OpenAIHttpDuration();
        }

        @Data
        public static class OpenAIHttpConnectionPool {
            private int maxIdleConnections = 3;
            private OpenAIHttpDuration keepAlive = new OpenAIHttpDuration();
        }


        @Data
        public static class OpenAIHttpDuration {
            private long period = 1;
            private TimeUnit timeUnit = TimeUnit.MINUTES;
        }
    }
}
