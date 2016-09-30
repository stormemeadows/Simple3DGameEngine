package visibles.players;


import models.RawModel;

/**
 * @author storm
 *
 */
public class PlayerModel extends RawModel {

	/**
	 * @param vaoID
	 * @param vertexCount
	 */
	public PlayerModel(int vaoID, int vertexCount) {
		super(vaoID, vertexCount);
	}


	/**
	 * @param other
	 */
	public PlayerModel(PlayerModel other) {
		super(other);
	}


	/**
	 * @param fileName
	 */
	public PlayerModel(String fileName) {
		super(fileName);
	}

}
