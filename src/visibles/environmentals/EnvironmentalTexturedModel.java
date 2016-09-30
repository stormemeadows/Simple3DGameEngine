
package visibles.environmentals;


import models.textured.LightAffectedTexturedModel;

/**
 * @author storm
 *
 */
public class EnvironmentalTexturedModel
        extends LightAffectedTexturedModel<EnvironmentalTexture, EnvironmentalModel> {

    /**
     * @param texture
     * @param model
     */
    public EnvironmentalTexturedModel(EnvironmentalTexture texture, EnvironmentalModel model) {
        super(texture, model);
    }


    /**
     * @param texture
     * @param model
     */
    public EnvironmentalTexturedModel(String texFileName, String modelFileName) {
        super(new EnvironmentalTexture(texFileName), new EnvironmentalModel(modelFileName));
    }

}
