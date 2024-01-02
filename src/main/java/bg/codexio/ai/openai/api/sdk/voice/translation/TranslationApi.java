package bg.codexio.ai.openai.api.sdk.voice.translation;

import bg.codexio.ai.openai.api.http.voice.TranslationHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.request.TranslationRequest;

/**
 * Public facade over {@link AudioFileStage},
 * this class essentially represents a preconfigured
 * Transcription API with HTTP Executor and Default AI Model
 */
public class TranslationApi
        extends AudioFileStage {

    public TranslationApi(
            TranslationHttpExecutor executor,
            TranslationRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }
}
