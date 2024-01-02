package bg.codexio.ai.openai.api.sdk.voice.transcription;

import bg.codexio.ai.openai.api.http.voice.TranscriptionHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.request.TranscriptionRequest;

/**
 * Public facade over {@link AudioFileStage},
 * this class essentially represents a preconfigured
 * Transcription API with HTTP Executor and Default AI Model
 */
public class TranscriptionApi
        extends AudioFileStage {

    public TranscriptionApi(
            TranscriptionHttpExecutor executor,
            TranscriptionRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }
}
