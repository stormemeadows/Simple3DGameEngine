package textures;


import core.ifaces.ITexturable;

/**
 * @author storm
 *
 */
public class Texture extends BasicTexture
		implements ITexturable {

	protected boolean	hasTransparency						= false;
	protected int		numberOfRows						= 1;
	protected int		indexOfTextureInTextureImageFile	= 0;
	protected int		numberOfTexturesInTextureImageFile	= 1;


	/**
	 * @param textureID
	 */
	public Texture(int textureID) {
		super(textureID);
	}


	/**
	 * @param textureID
	 * @param numberOfRows
	 * @param indexOfTextureInTextureImageFile
	 * @param numberOfTexturesInTextureImageFile
	 */
	public Texture(int textureID, int numberOfRows,
			int indexOfTextureInTextureImageFile,
			int numberOfTexturesInTextureImageFile) {
		this(textureID);
		this.numberOfRows = numberOfRows;
		this.indexOfTextureInTextureImageFile = indexOfTextureInTextureImageFile;
		this.numberOfTexturesInTextureImageFile = numberOfTexturesInTextureImageFile;
	}


	/**
	 * @param other
	 */
	public Texture(Texture other) {
		this(other.getTextureID(), other.getNumberOfRows(),
				other.getIndexOfTextureInTextureImageFile(),
				other.getNumberOfTexturesInTextureImageFile());
		this.hasTransparency = other.isHasTransparency();
	}


	/**
	 * @param fileName
	 */
	public Texture(String fileName) {
		super(fileName);
	}


	@Override
	public float getTextureXOffset() {
		// float numRows = texture.getNumberOfRows();
		// float column = indexOfTextureInTextureImageFile % numRows;
		// return column / numRows;
		final int column = indexOfTextureInTextureImageFile % numberOfRows;
		return ((float) column) / ((float) numberOfRows);
	}


	@Override
	public float getTextureYOffset() {
		// float row = (float)
		// Math.floor(indexOfTextureInTextureImageFile/numRows);
		// return row / numRows;

		// both ints, so needn't floor
		final int row = indexOfTextureInTextureImageFile / numberOfRows;
		return ((float) row) / ((float) numberOfRows);
	}


	/**
	 * @Chainable
	 * 
	 * @param numberOfRows
	 *            the numberOfRows to set
	 */
	@Override
	public Texture setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
		return this;
	}


	/**
	 * @Chainable
	 * 
	 * @param numberOfTexturesInTextureImageFile
	 *            the numberOfTexturesInTextureImageFile to set
	 */
	@Override
	public Texture setNumberOfTexturesInTextureImageFile(int numberOfTexturesInTextureImageFile) {
		this.numberOfTexturesInTextureImageFile = numberOfTexturesInTextureImageFile;
		return this;
	}


	/**
	 * @Chainable
	 * 
	 * @param indexOfTextureInTextureImageFile
	 *            the indexOfTextureInTextureImageFile to set
	 */
	@Override
	public Texture setIndexOfTextureInTextureImageFile(int indexOfTextureInTextureImageFile) {
		this.indexOfTextureInTextureImageFile = indexOfTextureInTextureImageFile;
		return this;
	}


	/**
	 * @Chainable
	 * 
	 * @param hasTransparency
	 *            the hasTransparency to set
	 */
	@Override
	public Texture setHasTransparency(boolean hasTransparency) {
		this.hasTransparency = hasTransparency;
		return this;
	}


	/**
	 * @return the hasTransparency
	 */
	@Override
	public boolean isHasTransparency() {
		return hasTransparency;
	}


	/**
	 * @return the numberOfRows
	 */
	@Override
	public int getNumberOfRows() {
		return numberOfRows;
	}


	/**
	 * @return the indexOfTextureInTextureImageFile
	 */
	@Override
	public int getIndexOfTextureInTextureImageFile() {
		return indexOfTextureInTextureImageFile;
	}


	/**
	 * @return the numberOfTexturesInTextureImageFile
	 */
	public int getNumberOfTexturesInTextureImageFile() {
		return numberOfTexturesInTextureImageFile;
	}

}
