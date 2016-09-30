package gameEngine.shaders;


import static settings.GameConstants.LightingParameters.MAX_NUM_CONCURRENT_LIGHTS;
import static settings.GameConstants.OpenGLVAOParameters.NORMAL_VECTS_IDX;
import static settings.GameConstants.OpenGLVAOParameters.NORMAL_VECTS_NAME;
import static settings.GameConstants.OpenGLVAOParameters.TEX_COORDS_IDX;
import static settings.GameConstants.OpenGLVAOParameters.TEX_COORDS_NAME;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import core.ifaces.gameEngine.shaders.ILoadsClippingPlane;
import core.ifaces.gameEngine.shaders.ILoadsLightAffectedTextureData;
import core.ifaces.gameEngine.shaders.ILoadsLightSourceData;
import core.ifaces.gameEngine.shaders.ILoadsSkyData;
import core.ifaces.gameEngine.shaders.ILoadsTransformationMatrix;
import visibles.lights.Light;


/**
 * @author storm
 * 
 * @desc Adds fields:
 *       location_lightPosition[]
 *       location_lightColour[]
 *       location_attenuation[]
 *       location_transformationMatrix
 *       location_shineDamper
 *       location_reflectivity
 *       location_skyColour
 *       location_plane
 * 
 * @desc Adds methods:
 *       getLightUniformLocations(int)
 *       loadLights(List<Light>)
 *       loadTransformationMatrix(Matrix4f)
 *       loadClipPlane(Vector4f)
 *       loadSkyColour(float,float,float)
 *       loadShineVariables(float,float)
 * 
 * @desc Overrides:
 *       bindAttributes()
 *       getAllUniformLocations()
 */
public abstract class LightAffectedShader extends BasicShaderWithProjectionMatrix
		implements ILoadsTransformationMatrix,
		ILoadsClippingPlane,
		ILoadsSkyData,
		ILoadsLightSourceData,
		ILoadsLightAffectedTextureData {

	// mat4
	private int location_transformationMatrix;

	// vec4
	private int location_plane;

	// vec3[4]
	private int[]	location_lightPosition;
	private int[]	location_lightColour;
	private int[]	location_attenuation;

	// vec3
	private int location_skyColour;

	// vec2
	private int location_offset; // for accessing tex in tex atlas

	// float
	private int	location_shineDamper;
	private int	location_reflectivity;
	private int	location_numberOfRows;	// num rows in tex atlas

	// float, but used as boolean in shader
	private int location_useFakeLighting;


	/**
	 * 2 attribs introduced by this subclass.
	 * 
	 * @param vertexFile
	 * @param fragmentFile
	 */
	public LightAffectedShader(String vertexFile, String fragmentFile) {
		super(vertexFile, fragmentFile);
		numAttribs += 2;
	}


	@Override
	protected void bindAttribLocations() {
		super.bindAttribLocations();

		bindAttribLocation(TEX_COORDS_IDX, TEX_COORDS_NAME);
		bindAttribLocation(NORMAL_VECTS_IDX, NORMAL_VECTS_NAME);
	}


	@Override
	protected void getAllUniformLocations() {
		super.getAllUniformLocations();

		location_transformationMatrix = getUniformLocation("transformationMatrix");

		location_plane = getUniformLocation("plane");

		getUniformLocationsOfLights(MAX_NUM_CONCURRENT_LIGHTS);

		location_skyColour = getUniformLocation("skyColour");

		location_offset = getUniformLocation("offset");

		location_numberOfRows = getUniformLocation("numRows");
		location_shineDamper = getUniformLocation("shineDamper");
		location_reflectivity = getUniformLocation("reflectivity");
		location_useFakeLighting = getUniformLocation("useFakeLighting");
	}


	final protected void getUniformLocationsOfLights(int numLights) {
		setSizeOfLocationArraysForLightingEffects(numLights);
		for (int i = 0; i < numLights; i++)
			getUniformLocationOfLight(i);
	}


	private void setSizeOfLocationArraysForLightingEffects(int numLights) {
		location_lightPosition = new int[numLights];
		location_lightColour = new int[numLights];
		location_attenuation = new int[numLights];
	}


	protected void getUniformLocationOfLight(int idx) {
		getUniformLocOfArray(location_lightPosition, "lightPosition", idx);
		getUniformLocOfArray(location_lightColour, "lightColour", idx);
		getUniformLocOfArray(location_attenuation, "attenuation", idx);
	}


	// TODO make this less kludgy
	@Override
	final public void loadLights(List<Light> lights) {
		// int i = 0;
		// while (i < MAX_NUM_CONCURRENT_LIGHTS) loadLight(i, lights.get(i++));
		// while (i < lights.size()) loadFakeLight(i++);
		for (int i = 0; i < MAX_NUM_CONCURRENT_LIGHTS; i++)
			if (i < lights.size())
				loadLight(i, lights.get(i));
			else
				loadFakeLight(i);
	}


	@Override
	public void loadLight(int idx, Light light) {
		loadLightPosition(idx, light.getPosition());
		loadLightColor(idx, light.getColour());
		loadAttenuation(idx, light.getAttenuation());
	}


	// shader expects certain number of lights, so load fake ones if needed
	protected void loadFakeLight(int idx) {
		load3f(location_lightPosition[idx], 0f, 0f, 0f);
		load3f(location_lightColour[idx], 0f, 0f, 0f);
		load3f(location_attenuation[idx], 1f, 0f, 0f);
	}


	@Override
	public void loadLightPosition(int idx, Vector3f position) {
		loadVector3f(location_lightPosition[idx], position);
	}


	@Override
	public void loadLightColor(int idx, Vector3f color) {
		loadVector3f(location_lightColour[idx], color);
	}


	@Override
	public void loadAttenuation(int idx, Vector3f attenuationFactors) {
		loadVector3f(location_attenuation[idx], attenuationFactors);
	}


	@Override
	public void loadOffset(float x, float y) {
		load2f(location_offset, x, y);
	}


	@Override
	public void loadNumberOfRows(int numberOfRows) {
		load1f(location_numberOfRows, numberOfRows);
	}


	@Override
	public void loadFakeLightingVariable(boolean useFake) {
		loadBoolean(location_useFakeLighting, useFake);
	}


	@Override
	public void loadTransformationMatrix(Matrix4f matrix) {
		loadMatrix4f(location_transformationMatrix, matrix);
	}


	@Override
	public void loadClipPlane(Vector4f plane) {
		loadVector4f(location_plane, plane);
	}


	@Override
	public void loadSkyColor(Vector3f colorRGB) {
		loadVector3f(location_skyColour, colorRGB);
	}


	@Override
	public void loadSkyColor(float r, float g, float b) {
		load3f(location_skyColour, r, g, b);
	}


	@Override
	public void loadShineVariables(float damper, float reflectivity) {
		loadShineDamper(damper);
		loadReflectivity(reflectivity);
	}


	@Override
	public void loadShineDamper(float damper) {
		load1f(location_shineDamper, damper);
	}


	@Override
	public void loadReflectivity(float reflectivity) {
		load1f(location_reflectivity, reflectivity);
	}

}
