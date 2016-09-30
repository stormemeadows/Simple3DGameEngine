package visibles.skybox;


import models.RawModel;
import models.textured.DoublyBasicTexturedModel;

/**
 * @author storm
 *
 */
public class SkyboxTexturedModel extends DoublyBasicTexturedModel<SkyboxTexture, SkyboxTexture, RawModel> {

	/**
	 * @param texture1
	 * @param texture2
	 * @param model
	 */
	public SkyboxTexturedModel(SkyboxTexture texture1, SkyboxTexture texture2, RawModel model) {
		super(texture1, texture2, model);
	}

}
