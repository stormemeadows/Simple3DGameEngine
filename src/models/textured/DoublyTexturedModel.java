
package models.textured;


import models.RawModel;
import textures.LightAffectedTexture;

/**
 * @author storm
 *
 */
public class DoublyTexturedModel<T1 extends LightAffectedTexture, T2 extends LightAffectedTexture, M extends RawModel> {

	private T1	texture1;
	private T2	texture2;
	private M	model;


	/**
	 * @param texture1
	 * @param texture2
	 * @param model
	 */
	public DoublyTexturedModel(T1 texture1, T2 texture2, M model) {
		this.texture1 = texture1;
		this.texture2 = texture2;
		this.model = model;
	}


	/**
	 * @return the texture1
	 */
	public T1 getTexture1() {
		return texture1;
	}


	/**
	 * @return the texture2
	 */
	public T2 getTexture2() {
		return texture2;
	}


	/**
	 * @return the model
	 */
	public M getModel() {
		return model;
	}


}
