package models;


import core.ifaces.IModelable;
import gameEngine.loaders.MasterLoader;

/**
 * @author storm
 *
 */
public class RawModel
		implements IModelable {

	private final int	vaoID;
	private final int	vertexCount;


	// public static RawModel loadFromFile(String fileName) {
	private static RawModel loadFromFile(String fileName) {
		return MasterLoader.loadRawModelFromObjFile(fileName);
	}


	public RawModel(int vaoID, int vertexCount) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}


	public RawModel(RawModel other) {
		this(other.getVaoID(), other.getVertexCount());
	}


	/**
	 * @param fileName
	 */
	public RawModel(String fileName) {
		this(RawModel.loadFromFile(fileName));
	}


	/**
	 * @return the vaoID
	 */
	@Override
	public int getVaoID() {
		return vaoID;
	}


	/**
	 * @return the vertexCount
	 */
	@Override
	public int getVertexCount() {
		return vertexCount;
	}


}
