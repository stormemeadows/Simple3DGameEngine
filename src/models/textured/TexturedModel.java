package models.textured;


import core.ifaces.ITexturedModelable;
import models.RawModel;
import textures.Texture;

/**
 * @author storm
 */
public class TexturedModel<T extends Texture, M extends RawModel>
		// implements IHasTexture<T>, IHasModel<M> {
		implements ITexturedModelable<T, M> {

	protected T	texture;
	protected M	model;


	/**
	 * @param model
	 * @param texture
	 */
	public TexturedModel(T texture, M model) {
		this.model = model;
		this.texture = texture;
	}


	/**
	 * @param model
	 * @param texture
	 */
	@SuppressWarnings("unchecked")
	public TexturedModel(String textureFileName, String modelObjFileName) {
		this(((T) new Texture(textureFileName)), (M) new RawModel(modelObjFileName));
	}


	/**
	 * @return the model
	 */
	@Override
	public M getModel() {
		return model;
	}


	/**
	 * @return the texture
	 */
	@Override
	public T getTexture() {
		return texture;
	}

}
