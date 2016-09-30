package gameEngine.shaders;


import static settings.GameConstants.WaterParameters.FRAGMENT_FILE;
import static settings.GameConstants.WaterParameters.VERTEX_FILE;

import org.lwjgl.util.vector.Matrix4f;

import core.ifaces.gameEngine.shaders.ILoadsModelMatrix;
import core.utils.Camera;
import gameEngine.shaders.ifaces.ISampler2DData;
import visibles.lights.Light;


/**
 * @author storm
 *
 */
public class WaterShader extends BasicShaderWithProjectionMatrix
		implements ISampler2DData,
		ILoadsModelMatrix {

	// vec3
	private int	location_lightPosition;
	private int	location_lightColour;

	// mat4
	private int location_modelMatrix;

	// float
	private int location_moveFactor;

	// vec3
	private int location_cameraPosition;

	// sampler2D
	private int	location_reflectionTexture;
	private int	location_refractionTexture;
	private int	location_dudvMap;
	private int	location_normalMap;
	private int	location_depthMap;


	public WaterShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}


	@Override
	protected void getAllUniformLocations() {
		super.getAllUniformLocations();

		// location_viewMatrix = getUniformLocation("viewMatrix");
		location_modelMatrix = getUniformLocation("modelMatrix");

		location_cameraPosition = getUniformLocation("cameraPosition");

		location_moveFactor = getUniformLocation("moveFactor");

		location_lightColour = getUniformLocation("lightColour");
		location_lightPosition = getUniformLocation("lightPosition");

		location_reflectionTexture = getUniformLocation("reflectionTexture");
		location_refractionTexture = getUniformLocation("refractionTexture");
		location_dudvMap = getUniformLocation("dudvMap");
		location_normalMap = getUniformLocation("normalMap");
		location_depthMap = getUniformLocation("depthMap");
	}


	@Override
	public void connectTextureUnits() {
		load1i(location_reflectionTexture, 0);
		load1i(location_refractionTexture, 1);
		load1i(location_dudvMap, 2);
		load1i(location_normalMap, 3);
		load1i(location_depthMap, 4);
	}


	public void loadLight(Light sun) {
		loadVector3f(location_lightColour, sun.getColour());
		loadVector3f(location_lightPosition, sun.getPosition());
	}


	public void loadMoveFactor(float mvFctr) {
		load1f(location_moveFactor, mvFctr);
	}


	@Override
	public void loadCamera(Camera camera) {
		super.loadCamera(camera);
		loadCameraPosition(camera);
	}


	protected void loadCameraPosition(Camera camera) {
		loadVector3f(location_cameraPosition, camera.getPosition());
	}


	@Override
	public void loadModelMatrix(Matrix4f modelMatrix) {
		loadMatrix4f(location_modelMatrix, modelMatrix);
	}

}
