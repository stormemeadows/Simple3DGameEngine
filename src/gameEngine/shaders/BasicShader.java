package gameEngine.shaders;


import static settings.GameConstants.OpenGLVAOParameters.POSITION_IDX;
import static settings.GameConstants.OpenGLVAOParameters.POSITION_NAME;

import org.lwjgl.util.vector.Matrix4f;

import core.ifaces.gameEngine.shaders.ILoadsCamera;
import core.utils.Camera;
import core.utils.Maths;

/**
 * @author storm
 * 
 * @desc Adds field:
 *       location_viewMatrix
 * 
 * @desc Adds method:
 *       loadViewMatrix(Matrix4f)
 *       loadCamera(Camera)
 * 
 * @desc Overrides:
 *       bindAttributes()
 *       getAllUniformLocations()
 */
public abstract class BasicShader extends ShaderProgram
		implements ILoadsCamera {

	// mat4
	private int location_viewMatrix;


	/**
	 * @param vertexFile
	 * @param fragmentFile
	 */
	public BasicShader(String vertexFile, String fragmentFile) {
		super(vertexFile, fragmentFile);
		numAttribs += 1;
	}


	@Override
	protected void bindAttribLocations() {
		bindAttribLocation(POSITION_IDX, POSITION_NAME);
	}


	@Override
	protected void getAllUniformLocations() {
		location_viewMatrix = getUniformLocation("viewMatrix");
	}


	@Override
	public void loadCamera(Camera camera) {
		final Matrix4f viewMatrix = createViewMatrix(camera);
		loadViewMatrix(viewMatrix);
	}


	protected Matrix4f createViewMatrix(Camera camera) {
		return Maths.createViewMatrix(camera);
	}


	final protected void loadViewMatrix(Matrix4f viewMatrix) {
		loadMatrix4f(location_viewMatrix, viewMatrix);
	}


}
