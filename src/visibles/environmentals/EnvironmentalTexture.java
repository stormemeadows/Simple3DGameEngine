package visibles.environmentals;


import textures.LightAffectedTexture;

/**
 * @author storm
 *
 */
public class EnvironmentalTexture extends LightAffectedTexture {

	/**
	 * @param textureID
	 */
	public EnvironmentalTexture(int textureID) {
		super(textureID);
	}


	/**
	 * @param textureID
	 * @param numRows
	 * @param idxInTexFile
	 * @param numTexsInTexFile
	 */
	public EnvironmentalTexture(int textureID, int numberOfRows, int indexOfTextureInTextureImageFile,
			int numberOfTexturesInTextureImageFile) {
		super(textureID, numberOfRows, indexOfTextureInTextureImageFile, numberOfTexturesInTextureImageFile);
		// TODO Auto-generated constructor stub
	}


	/**
	 * @param fileName
	 */
	public EnvironmentalTexture(String fileName) {
		super(fileName);
		// TODO Auto-generated constructor stub
	}
}
