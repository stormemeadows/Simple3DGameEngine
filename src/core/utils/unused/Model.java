package core.utils.unused;


/**
 * @author storm
 *
 */
public class Model {

	private int	vaoID;
	private int	vertexCount;	// geometryData


	/**
	 * @param vaoID
	 * @param vertexCount
	 */
	public Model(int vaoID, int vertexCount) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}


	/**
	 * @return the vaoID
	 */
	public int getVaoID() {
		return vaoID;
	}


	/**
	 * @param vaoID
	 *            the vaoID to set
	 */
	public void setVaoID(int vaoID) {
		this.vaoID = vaoID;
	}


	/**
	 * @return the vertexCount
	 */
	public int getVertexCount() {
		return vertexCount;
	}


	/**
	 * @param vertexCount
	 *            the vertexCount to set
	 */
	public void setVertexCount(int vertexCount) {
		this.vertexCount = vertexCount;
	}


}
