package core.ifaces;


/**
 * @author storm
 *
 */
public interface ITexturable extends IBasicTexturable {

	float getTextureXOffset();


	float getTextureYOffset();


	boolean isHasTransparency();


	int getNumberOfRows();


	int getIndexOfTextureInTextureImageFile();


	/**
	 * @Chainable
	 */
	ITexturable setNumberOfRows(int numberOfRows);


	/**
	 * @Chainable
	 */
	ITexturable setNumberOfTexturesInTextureImageFile(int numberOfTexturesInTextureImageFile);


	/**
	 * @Chainable
	 */
	ITexturable setIndexOfTextureInTextureImageFile(int indexOfTextureInTextureImageFile);


	/**
	 * @Chainable
	 */
	ITexturable setHasTransparency(boolean hasTransparency);


}
