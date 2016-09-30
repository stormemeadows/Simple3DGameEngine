package visibles.players;


import textures.LightAffectedTexture;

/**
 * @author storm
 *
 */
public class PlayerTexture extends LightAffectedTexture {

	/**
	 * @param textureID
	 */
	public PlayerTexture(int textureID) {
		super(textureID);
	}


	/**
	 * @param textureID
	 * @param numRows
	 * @param idxInTexFile
	 * @param numTexsInTexFile
	 */
	public PlayerTexture(int textureID, int numberOfRows,
			int indexOfTextureInTextureImageFile, int numberOfTexturesInTextureImageFile) {
		super(textureID, numberOfRows,
				indexOfTextureInTextureImageFile, numberOfTexturesInTextureImageFile);
	}


	/**
	 * @param fileName
	 */
	public PlayerTexture(String fileName) {
		super(fileName);
	}


	/**
	 * @param other
	 */
	// public <T extends LightAffectedTexture> PlayerTexture(T other) {
	public PlayerTexture(PlayerTexture other) {
		super(other);
	}


}
