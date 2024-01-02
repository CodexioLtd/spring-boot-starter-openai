package bg.codexio.springframework.boot.autoconfigure.bean;

import bg.codexio.ai.openai.api.http.AuthenticationInterceptor;
import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.models.v40.GPT40VisionPreviewModel;
import bg.codexio.ai.openai.api.models.whisper.Whisper10;
import bg.codexio.ai.openai.api.payload.chat.request.ChatMessageRequest;
import bg.codexio.ai.openai.api.payload.vision.request.VisionRequest;
import bg.codexio.ai.openai.api.payload.voice.request.SpeechRequest;
import bg.codexio.ai.openai.api.payload.voice.request.TranscriptionRequest;
import bg.codexio.ai.openai.api.payload.voice.request.TranslationRequest;
import bg.codexio.ai.openai.api.sdk.auth.FromEnvironment;
import bg.codexio.ai.openai.api.sdk.auth.FromJson;
import bg.codexio.ai.openai.api.sdk.chat.ChatApi;
import bg.codexio.ai.openai.api.sdk.chat.GPT35ChatApi;
import bg.codexio.ai.openai.api.sdk.chat.GPT4ChatApi;
import bg.codexio.ai.openai.api.sdk.images.ImagesApi;
import bg.codexio.ai.openai.api.sdk.vision.VisionApi;
import bg.codexio.ai.openai.api.sdk.voice.speech.SpeechApi;
import bg.codexio.ai.openai.api.sdk.voice.transcription.TranscriptionApi;
import bg.codexio.ai.openai.api.sdk.voice.translation.TranslationApi;
import bg.codexio.springframework.boot.autoconfigure.auth.FromProperties;
import bg.codexio.springframework.boot.autoconfigure.executor.chat.PropertyAwareChatHttpExecutor;
import bg.codexio.springframework.boot.autoconfigure.executor.images.PropertyAwareCreateImageHttpExecutor;
import bg.codexio.springframework.boot.autoconfigure.executor.images.PropertyAwareEditImageHttpExecutor;
import bg.codexio.springframework.boot.autoconfigure.executor.images.PropertyAwareImageVariationHttpExecutor;
import bg.codexio.springframework.boot.autoconfigure.executor.vision.PropertyAwareVisionHttpExecutor;
import bg.codexio.springframework.boot.autoconfigure.executor.voice.PropertyAwareSpeechHttpExecutor;
import bg.codexio.springframework.boot.autoconfigure.executor.voice.PropertyAwareTranscriptionHttpExecutor;
import bg.codexio.springframework.boot.autoconfigure.executor.voice.PropertyAwareTranslationHttpExecutor;
import bg.codexio.springframework.boot.autoconfigure.property.OpenAIProperties;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

public class BeanConfig {

    @Bean
    OpenAIProperties properties(
            OpenAIProperties properties,
            ApplicationContext ctx
    ) {
        if (properties.getAuthType() != null
                && properties.getAuthType() != FromProperties.class) {
            var auth = ctx.getBean(properties.getAuthType())
                          .credentials();

            if (!StringUtils.hasText(properties.getApiKey())) {
                properties.setApiKey(auth.apiKey());
            }

            if (!StringUtils.hasText(properties.getOrganizationId())) {
                properties.setOrganizationId(auth.organization());
            }

            if (StringUtils.hasText(auth.baseUrl())) {
                properties.setBaseUrl(auth.baseUrl());
            }
        }

        if (!StringUtils.hasText(properties.getApiKey())) {
            throw new BeanCreationException(
                    "Cannot create OpenAI SDK as " + "apiKey is missing");
        }

        return properties;
    }

    @Bean
    OkHttpClient httpClient(OpenAIProperties properties) {
        return new OkHttpClient.Builder().addInterceptor(new AuthenticationInterceptor(new HttpExecutorContext(FromProperties.parse(properties)
                                                                                                                             .credentials())))
                                         .connectionPool(new ConnectionPool(
                                                 properties.getHttp()
                                                           .getConnectionPool()
                                                           .getMaxIdleConnections(),
                                                 properties.getHttp()
                                                           .getConnectionPool()
                                                           .getKeepAlive()
                                                           .getPeriod(),
                                                 properties.getHttp()
                                                           .getConnectionPool()
                                                           .getKeepAlive()
                                                           .getTimeUnit()
                                         ))
                                         .callTimeout(
                                                 properties.getHttp()
                                                           .getTimeouts()
                                                           .getCall()
                                                           .getPeriod(),
                                                 properties.getHttp()
                                                           .getTimeouts()
                                                           .getCall()
                                                           .getTimeUnit()
                                         )
                                         .readTimeout(
                                                 properties.getHttp()
                                                           .getTimeouts()
                                                           .getRead()
                                                           .getPeriod(),
                                                 properties.getHttp()
                                                           .getTimeouts()
                                                           .getRead()
                                                           .getTimeUnit()
                                         )
                                         .connectTimeout(
                                                 properties.getHttp()
                                                           .getTimeouts()
                                                           .getConnect()
                                                           .getPeriod(),
                                                 properties.getHttp()
                                                           .getTimeouts()
                                                           .getConnect()
                                                           .getTimeUnit()
                                         )
                                         .build();
    }

    @Bean
    String baseUrl(OpenAIProperties properties) {
        return properties.getBaseUrl();
    }


    @Bean
    @ConditionalOnMissingBean(FromEnvironment.class)
    public FromEnvironment fromEnvironment() {
        return FromEnvironment.AUTH;
    }

    @Bean
    @ConditionalOnMissingBean(FromJson.class)
    public FromJson fromJson() {
        return FromJson.AUTH;
    }

    @Bean
    public ChatApi chatApi(PropertyAwareChatHttpExecutor chatHttpExecutor) {
        return new ChatApi(
                chatHttpExecutor,
                ChatMessageRequest.builder()
        );
    }

    @Bean
    public GPT4ChatApi gpt4chatApi(ChatApi chatApi) {
        return new GPT4ChatApi(chatApi.poweredByGPT40());
    }

    @Bean
    public GPT35ChatApi gpt35chatApi(ChatApi chatApi) {
        return new GPT35ChatApi(chatApi.poweredByGPT35());
    }

    @Bean
    public ImagesApi imagesApi(
            PropertyAwareCreateImageHttpExecutor createExecutor,
            PropertyAwareEditImageHttpExecutor editExecutor,
            PropertyAwareImageVariationHttpExecutor variationExecutor
    ) {
        return new ImagesApi(
                createExecutor,
                editExecutor,
                variationExecutor
        );
    }

    @Bean
    public VisionApi visionApi(PropertyAwareVisionHttpExecutor executor) {
        return new VisionApi(
                executor,
                VisionRequest.empty()
                             .withModel(new GPT40VisionPreviewModel().name())
        );
    }

    @Bean
    public SpeechApi speechApi(PropertyAwareSpeechHttpExecutor executor) {
        return new SpeechApi(
                executor,
                SpeechRequest.builder()
        );
    }

    @Bean
    public TranscriptionApi transcriptionApi(PropertyAwareTranscriptionHttpExecutor executor) {
        return new TranscriptionApi(
                executor,
                TranscriptionRequest.builder()
                                    .withModel(new Whisper10().name())
        );
    }

    @Bean
    public TranslationApi translationApi(PropertyAwareTranslationHttpExecutor executor) {
        return new TranslationApi(
                executor,
                TranslationRequest.builder()
                                  .withModel(new Whisper10().name())
        );
    }
}
