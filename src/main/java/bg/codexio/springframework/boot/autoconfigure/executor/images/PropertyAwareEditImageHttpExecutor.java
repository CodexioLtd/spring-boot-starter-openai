package bg.codexio.springframework.boot.autoconfigure.executor.images;

import bg.codexio.ai.openai.api.http.images.EditImageHttpExecutor;
import bg.codexio.springframework.boot.autoconfigure.property.PropertyAware;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;

/**
 * Overrides {@link #getEnv(String)} to read
 * from {@link PropertyAware#getProperty(String)} instead
 * from {@link System#getenv(String)} so logging level
 * can be configured from application.yml / application.properties
 */
public class PropertyAwareEditImageHttpExecutor
        extends EditImageHttpExecutor {

    private final PropertyAware propertyAware;

    public PropertyAwareEditImageHttpExecutor(
            OkHttpClient client,
            String baseUrl,
            ObjectMapper objectMapper,
            PropertyAware propertyAware
    ) {
        super(
                client,
                baseUrl,
                objectMapper
        );

        this.propertyAware = propertyAware;
    }

    @Override
    protected String getEnv(String key) {
        return this.propertyAware.getProperty(key);
    }
}
