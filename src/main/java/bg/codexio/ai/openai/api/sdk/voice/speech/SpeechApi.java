package bg.codexio.ai.openai.api.sdk.voice.speech;

import bg.codexio.ai.openai.api.http.voice.SpeechHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.request.SpeechRequest;

/**
 * Public facade over the {@link AIModelStage},
 * this class essentially represents a preconfigured
 * Speech API with HTTP Executor
 */
public class SpeechApi
        extends AIModelStage {

    public SpeechApi(
            SpeechHttpExecutor executor,
            SpeechRequest.Builder requestContext
    ) {
        super(
                executor,
                requestContext
        );
    }
}
