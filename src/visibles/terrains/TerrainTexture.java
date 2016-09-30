package visibles.terrains;


import textures.LightAffectedTexture;

/**
 * @author storm
 *
 */
public class TerrainTexture extends LightAffectedTexture {

	public TerrainTexture(int textureID) {
		super(textureID);
	}


	public TerrainTexture(int textureID, int numberOfRows, int indexOfTextureInTextureImageFile,
			int numberOfTexturesInTextureImageFile) {
		super(textureID, numberOfRows, indexOfTextureInTextureImageFile,
				numberOfTexturesInTextureImageFile);
	}


	/**
	 * @param fileName
	 */
	public TerrainTexture(String fileName) {
		super(fileName);
	}


}
