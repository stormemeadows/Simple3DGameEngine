package textures;


import core.ifaces.IBasicTexturable;
import gameEngine.loaders.MasterLoader;

/**
 * @author storm
 * 
 */
public class BasicTexture
		implements IBasicTexturable {

	private final int textureID;


	public static BasicTexture loadBasicTextureFromFile(String fileName) {
		return new BasicTexture(fileName);
	}


	protected static int loadFromFile(String fileName) {
		return MasterLoader.load2DTexFromPNGFileAndReturnTexID(fileName);
	}


	/**
	 * @param textureID
	 */
	public BasicTexture(int textureID) {
		this.textureID = textureID;
	}


	/**
	 * @param other
	 */
	// public <B extends BasicTexture> BasicTexture(B other) {
	public BasicTexture(BasicTexture other) {
		this(other.getTextureID());
	}


	/**
	 * @param textureID
	 */
	public BasicTexture(String fileName) {
		this(loadFromFile(fileName));
	}


	/**
	 * @return the textureID
	 */
	@Override
	public int getTextureID() {
		return textureID;
	}


	@Override
	public int getVboID() {
		return getTextureID();
	}


}
