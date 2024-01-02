package bg.codexio.ai.openai.api.sdk.chat;

import bg.codexio.ai.openai.api.http.chat.ChatHttpExecutor;
import bg.codexio.ai.openai.api.payload.chat.request.ChatMessageRequest;

/**
 * Public facade over the {@link AIModelStage},
 * this class essentially represents a preconfigured
 * Chat API with HTTP Executor
 */
public class ChatApi
        extends AIModelStage {

    public ChatApi(
            ChatHttpExecutor executor,
            ChatMessageRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }
}
