package textures.blenders;


import java.util.ArrayList;
import java.util.List;

import core.ifaces.IBasicTexturable;
import textures.BasicTexture;

/**
 * @author storm
 *
 */
class BasicTexBlendPack<T extends IBasicTexturable> {

	private final T	backgroundTexture;
	private final T	rTexture;
	private final T	gTexture;
	private final T	bTexture;


	/**
	 * @param bckgrndTexName
	 * @param rTexName
	 * @param gTexName
	 * @param bTexName
	 */
	@SuppressWarnings("unchecked")
	public BasicTexBlendPack(String bckgrndTexName,
			String rTexName, String gTexName, String bTexName) {
		this((T) new BasicTexture(bckgrndTexName),
				(T) new BasicTexture(rTexName),
				(T) new BasicTexture(gTexName),
				(T) new BasicTexture(bTexName));
	}


	/**
	 * @param backgroundTexture
	 * @param rTexture
	 * @param gTexture
	 * @param bTexture
	 */
	public BasicTexBlendPack(T background, T red, T green, T blue) {
		this.backgroundTexture = background;
		this.rTexture = red;
		this.gTexture = green;
		this.bTexture = blue;
	}


	public List<T> getAllTextures() {
		final List<T> retVal = new ArrayList<T>();
		retVal.add(backgroundTexture);
		retVal.add(rTexture);
		retVal.add(gTexture);
		retVal.add(bTexture);
		return retVal;
	}


	/**
	 * @return the backgroundTexture
	 */
	public T getBackgroundTexture() {
		return backgroundTexture;
	}


	/**
	 * @return the rTexture
	 */
	public T getrTexture() {
		return rTexture;
	}


	/**
	 * @return the gTexture
	 */
	public T getgTexture() {
		return gTexture;
	}


	/**
	 * @return the bTexture
	 */
	public T getbTexture() {
		return bTexture;
	}

}

