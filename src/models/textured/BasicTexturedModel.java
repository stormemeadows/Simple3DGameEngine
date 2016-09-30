package models.textured;


import models.RawModel;
import textures.BasicTexture;

/**
 * @author storm
 *
 */
public class BasicTexturedModel<T extends BasicTexture, M extends RawModel> {

	private final T	texture;
	private final M	model;


	/**
	 * @param model
	 * @param texture
	 */
	public BasicTexturedModel(T texture, M model) {
		this.model = model;
		this.texture = texture;
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
	public T getTexture() {
		return texture;
	}


}
