package models.textured;


import core.ifaces.ILightAffectedTexturedModelable;
import models.RawModel;
import textures.LightAffectedTexture;

/**
 * @author storm
 *
 */
public class LightAffectedTexturedModel<T extends LightAffectedTexture, M extends RawModel>
		extends TexturedModel<T, M>
		implements ILightAffectedTexturedModelable<T, M> {

	/**
	 * @param model
	 * @param texture
	 */
	public LightAffectedTexturedModel(T texture, M model) {
		super(texture, model);
	}


	/**
	 * @param model
	 * @param texture
	 */
	@SuppressWarnings("unchecked")
	public LightAffectedTexturedModel(String textureFileName, String modelObjFileName) {
		this(((T) new LightAffectedTexture(textureFileName)), (M) new RawModel(modelObjFileName));
	}


}
