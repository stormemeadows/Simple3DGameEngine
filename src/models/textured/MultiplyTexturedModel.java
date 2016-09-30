package models.textured;


import java.util.List;

import models.RawModel;
import textures.LightAffectedTexture;

/**
 * @author storm
 *
 */
public class MultiplyTexturedModel<L extends List<? extends LightAffectedTexture>, M extends RawModel> {

	private L	textures;
	private M	model;


	/**
	 * @param model
	 * @param texture
	 */
	public MultiplyTexturedModel(L textures, M model) {
		this.model = model;
		this.textures = textures;
	}


	/**
	 * @return the model
	 */
	public M getModel() {
		return model;
	}


	/**
	 * @return the texture
	 */
	public L getTexture() {
		return textures;
	}

}
