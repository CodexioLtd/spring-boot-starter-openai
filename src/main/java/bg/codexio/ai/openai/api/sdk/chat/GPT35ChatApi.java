package bg.codexio.ai.openai.api.sdk.chat;

/**
 * Public facade over {@link TemperatureStage},
 * this class essentially represents the
 * {@link ChatApi#poweredByGPT35()} result
 */
public class GPT35ChatApi
        extends TemperatureStage {

    public GPT35ChatApi(
            TemperatureStage temperatureStage
    ) {
        super(
                temperatureStage.executor,
                temperatureStage.requestBuilder
        );
    }
}
