package textures;


import core.ifaces.ILightAffectedTexturable;

/**
 * @author storm
 * 
 * @desc TextureFile indices of n textures in a texture (atlas) are arranged as:
 *       ---------------------------------
 *       |____0____|_1_|_..._|_Sqrt(n)-1_|
 *       |_Sqrt(n)_|_._|_..._|_____._____|
 *       |____.____|_._|_..._|_____._____|
 *       |____.____|_._|_..._|_____._____|
 *       |n-Sqrt(n)|_._|_..._|____n-1____|
 *       ---------------------------------
 */
public class LightAffectedTexture extends Texture
		implements ILightAffectedTexturable {

	private float	shineDamper		= 1;
	private float	reflectivity	= 0;

	private boolean useFakeLighting = false;


	/**
	 * @param textureID
	 */
	public LightAffectedTexture(int textureID) {
		super(textureID);
	}


	/**
	 * @param fileName
	 */
	public LightAffectedTexture(String fileName) {
		super(fileName);
	}


	/**
	 * @param textureID
	 * @param numRows
	 * @param idxInTexFile
	 * @param numTexsInTexFile
	 */
	public LightAffectedTexture(int textureID, int numberOfRows, int indexOfTextureInTextureImageFile,
			int numberOfTexturesInTextureImageFile) {
		super(textureID, numberOfRows, indexOfTextureInTextureImageFile,
				numberOfTexturesInTextureImageFile);
	}


	/**
	 * @param other
	 */
	public LightAffectedTexture(LightAffectedTexture other) {
		super(other);
		this.shineDamper = other.getShineDamper();
		this.reflectivity = other.getReflectivity();
		this.useFakeLighting = other.isUseFakeLighting();
	}


	/**
	 * @Chainable
	 * 
	 * @param shineDamper
	 *            the shineDamper to set
	 */
	public LightAffectedTexture setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
		return this;
	}


	/**
	 * @Chainable
	 * 
	 * @param reflectivity
	 *            the reflectivity to set
	 */
	public LightAffectedTexture setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
		return this;
	}


	/**
	 * @Chainable
	 * 
	 * @param useFakeLighting
	 *            the useFakeLighting to set
	 */
	public LightAffectedTexture setUseFakeLighting(boolean useFakeLighting) {
		this.useFakeLighting = useFakeLighting;
		return this;
	}


	/**
	 * @return the shineDamper
	 */
	@Override
	public float getShineDamper() {
		return shineDamper;
	}


	/**
	 * @return the reflectivity
	 */
	@Override
	public float getReflectivity() {
		return reflectivity;
	}


	/**
	 * @return the useFakeLighting
	 */
	@Override
	public boolean isUseFakeLighting() {
		return useFakeLighting;
	}

}
