package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.http.images.CreateImageHttpExecutor;
import bg.codexio.ai.openai.api.http.images.EditImageHttpExecutor;
import bg.codexio.ai.openai.api.http.images.ImageVariationHttpExecutor;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequest;

/**
 * Public facade over {@link ActionTypeStage},
 * this class essentially represents a preconfigured
 * Images API with all HTTP executors already populated
 */
public class ImagesApi
        extends ActionTypeStage<ImageRequest> {

    public ImagesApi(
            CreateImageHttpExecutor createExecutor,
            EditImageHttpExecutor editExecutor,
            ImageVariationHttpExecutor variationExecutor
    ) {
        super(
                createExecutor,
                editExecutor,
                variationExecutor
        );
    }
}
