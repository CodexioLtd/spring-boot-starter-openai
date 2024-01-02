package bg.codexio.ai.openai.api.sdk.vision;

import bg.codexio.ai.openai.api.http.vision.VisionHttpExecutor;
import bg.codexio.ai.openai.api.payload.vision.request.VisionRequest;

/**
 * Public facade over {@link TokenStage},
 * this class essentially represents a preconfigured
 * Vision API with HTTP Executor and Default AI Model
 */
public class VisionApi
        extends TokenStage {

    public VisionApi(
            VisionHttpExecutor executor,
            VisionRequest requestContext
    ) {
        super(
                executor,
                requestContext
        );
    }
}
