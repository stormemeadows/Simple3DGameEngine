package textures.blenders;


import java.util.ArrayList;
import java.util.List;

import core.ifaces.IBasicTexturable;
import core.ifaces.IHasBasicTexture;

/**
 * @author storm
 *
 */
public class BasicBlendedTexture<T extends IBasicTexturable>
		implements IHasBasicTexture<T> {

	private final ArrayList<T>	texPack	= new ArrayList<>();
	private final T				blendMap;


	public BasicBlendedTexture(T bckgrndTex, T rTex, T gTex, T bTex, T mapTex) {
		texPack.add(bckgrndTex);
		texPack.add(rTex);
		texPack.add(gTex);
		texPack.add(bTex);
		blendMap = mapTex;
	}


	// @SuppressWarnings("unchecked")
	// public BasicBlendedTexture(String bckgrndTex, String rTex, String gTex,
	// String bTex, String mapTex) {
	// this((T) new BasicTexture(bckgrndTex),
	// (T) new BasicTexture(rTex),
	// (T) new BasicTexture(gTex),
	// (T) new BasicTexture(bTex),
	// (T) new BasicTexture(mapTex));
	// }


	public List<T> toList() {
		final ArrayList<T> textures = new ArrayList<>();
		textures.addAll(texPack);
		textures.add(getBlendMap());
		return textures;
	}


	/**
	 * @return the backgroundTexture
	 */
	public T getBackgroundTexture() {
		return texPack.get(0);
	}


	/**
	 * @return texPack.getthe rTexture
	 */
	public T getrTexture() {
		return texPack.get(1);
	}


	/**
	 * @return texPack.getthe gTexture
	 */
	public T getgTexture() {
		return texPack.get(2);
	}


	/**
	 * @return texPack.getthe bTexture
	 */
	public T getbTexture() {
		return texPack.get(3);
	}


	/**
	 * @return the blendMap
	 */
	public T getBlendMap() {
		return blendMap;
	}


	@Override
	public T getTexture() {
		return getBlendMap();
	}


}
