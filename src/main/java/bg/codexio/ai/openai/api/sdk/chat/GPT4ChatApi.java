package bg.codexio.ai.openai.api.sdk.chat;

/**
 * Public facade over {@link TemperatureStage},
 * this class essentially represents the
 * {@link ChatApi#poweredByGPT40()} result
 */
public class GPT4ChatApi
        extends TemperatureStage {

    public GPT4ChatApi(
            TemperatureStage temperatureStage
    ) {
        super(
                temperatureStage.executor,
                temperatureStage.requestBuilder
        );
    }
}
