package visibles.environmentals;


import models.RawModel;

/**
 * @author storm
 *
 */
public class EnvironmentalModel extends RawModel {

	/**
	 * @param vaoID
	 * @param vertexCount
	 */
	public EnvironmentalModel(int vaoID, int vertexCount) {
		super(vaoID, vertexCount);
	}


	/**
	 * @param other
	 */
	public EnvironmentalModel(EnvironmentalModel other) {
		super(other);
	}


	/**
	 * @param fileName
	 */
	public EnvironmentalModel(String fileName) {
		super(fileName);
	}

}
