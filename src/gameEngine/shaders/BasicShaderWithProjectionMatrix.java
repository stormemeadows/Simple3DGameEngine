package gameEngine.shaders;


import org.lwjgl.util.vector.Matrix4f;

import core.ifaces.gameEngine.shaders.ILoadsProjectionMatrix;

/**
 * @author storm
 * 
 * @desc Adds field:
 *       location_projectionMatrix
 * 
 * @desc Adds method:
 *       loadProjectionMatrix(Matrix4f)
 * 
 * @desc Overrides:
 *       getAllUniformLocations()
 */
public abstract class BasicShaderWithProjectionMatrix extends BasicShader
		implements ILoadsProjectionMatrix {

	// mat4
	private int location_projectionMatrix;


	/**
	 * @param vertexFile
	 * @param fragmentFile
	 */
	public BasicShaderWithProjectionMatrix(String vertexFile, String fragmentFile) {
		super(vertexFile, fragmentFile);
	}


	@Override
	protected void getAllUniformLocations() {
		super.getAllUniformLocations();
		location_projectionMatrix = getUniformLocation("projectionMatrix");
	}


	@Override
	public void loadProjectionMatrix(Matrix4f projectionMatrix) {
		loadMatrix4f(location_projectionMatrix, projectionMatrix);
	}
}
