package gameEngine.shaders;


import static settings.GameConstants.SkyboxParameters.FRAGMENT_FILE;
import static settings.GameConstants.SkyboxParameters.ROTATE_SPEED;
import static settings.GameConstants.SkyboxParameters.VERTEX_FILE;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import core.display.DisplayManager;
import core.utils.Camera;
import core.utils.Maths;
import gameEngine.shaders.ifaces.ISampler2DData;

public class SkyboxShader extends BasicShaderWithProjectionMatrix
		implements ISampler2DData {

	// sampler2D
	private int	location_cubeMap;
	private int	location_cubeMap2;

	// vec3
	private int location_fogColour;

	// float
	private int location_blendFactor;

	private float angleOfRotationInDegrees = 0;


	public SkyboxShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}


	@Override
	protected void getAllUniformLocations() {
		super.getAllUniformLocations();

		location_cubeMap = getUniformLocation("cubeMap");
		location_cubeMap2 = getUniformLocation("cubeMap2");

		location_fogColour = getUniformLocation("fogColour");

		location_blendFactor = getUniformLocation("blendFactor");
	}


	@Override
	public void loadCamera(Camera camera) {
		final Matrix4f matrix = Maths.createViewMatrix(camera);
		matrix.m30 = 0;
		matrix.m31 = 0;
		matrix.m32 = 0;

		// rotate about vertical axis, ie y axis
		angleOfRotationInDegrees += ROTATE_SPEED * DisplayManager.getFrameTimeSeconds();
		Maths.matRotateInDegs(angleOfRotationInDegrees, Maths.Y_AXIS, matrix);

		loadViewMatrix(matrix);
	}


	@Override
	public void connectTextureUnits() {
		load1i(location_cubeMap, 0);
		load1i(location_cubeMap2, 1);
	}


	public void loadFogColour(Vector3f rgb) {
		loadVector3f(location_fogColour, rgb);
	}


	public void loadFogColour(float r, float g, float b) {
		load3f(location_fogColour, r, g, b);
	}


	public void loadBlendFactor(float blend) {
		load1f(location_blendFactor, blend);
	}

}
