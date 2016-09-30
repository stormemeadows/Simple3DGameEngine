package gameEngine.loaders;


import static settings.GameConstants.Folders.SKYBOX;
import static settings.GameConstants.LoadingParameters.ALWAYS_CLEAN;
import static settings.GameConstants.LoadingParameters.USE_SINGLETON_LOADER;

import models.RawModel;
import settings.GameConstants;

/**
 * @author storm
 * 
 * @desc Keeps track of the
 *       - vertex array objects,
 *       - vertex buffer objects, and
 *       - textures
 *       that OpenGL generates.
 * 
 *       Tells OpenGL to delete them by calling cleanUp().
 */

public final class MasterLoader {
	private final static Loader SINGLETON_LOADER;


	static {
		SINGLETON_LOADER = (USE_SINGLETON_LOADER) ? new Loader() : null;
	}


	public static void cleanUp() {
		GET_LOADER().cleanUp();
	}


	public static int load2DTexFromPNGFileAndReturnTexID(String fileName) {
		final Loader l = GET_LOADER();
		final int retVal = l.load2DTexFromPNGFileAndReturnTexID(fileName);
		if (ALWAYS_CLEAN)
			l.cleanUp();
		return retVal;
	}


	/**
	 * @param dir
	 * @param textureFiles
	 * @return texureID
	 */
	public static int loadCubeMapFromPNGfilesAndReturnTexID(String dir, String[] textureFiles) {
		final Loader l = GET_LOADER();
		final int retVal = l.loadCubeMapFromPNGfilesAndReturnTexID(textureFiles);
		if (ALWAYS_CLEAN)
			l.cleanUp();
		return retVal;
	}


	/**
	 * Uses the default visibles.skybox directory.
	 * 
	 * @see GameConstants.Folders.SKYBOX
	 *
	 * @param dir
	 * @param textureFiles
	 * @return texureID
	 */
	public static int loadCubeMapFromPNGfilesAndReturnTexID(String[] textureFiles) {
		return loadCubeMapFromPNGfilesAndReturnTexID(SKYBOX, textureFiles);
	}


	/**
	 * @param fileName
	 * @return The RawModel.
	 */
	public static RawModel loadRawModelFromObjFile(String fileName) {
		final Loader l = GET_LOADER();
		final ObjFileReader o = new ObjFileReader(fileName);
		final RawModel retVal = l.loadToVAO(o.verticesArray, o.textureCoordsArray, o.normalsArray, o.indicesArray);
		if (ALWAYS_CLEAN)
			l.cleanUp();
		return retVal;
	}


	/**
	 * @param fileName
	 * @param type
	 * @return The shader's id.
	 */
	public static int loadShaderFileAndReturnShaderID(String fileName, int type) {
		return ShaderFileLoader.loadShader(fileName, type);
	}


	/**
	 * @param positions
	 * @param textureCoords
	 * @param normals
	 * @param indices
	 * @return
	 */
	public static RawModel loadToVAO(float[] positions, float[] texCoords,
			float[] normalVects, int[] indices) {
		final Loader l = GET_LOADER();
		final RawModel retVal = l.loadToVAO(positions, texCoords, normalVects, indices);
		if (ALWAYS_CLEAN)
			l.cleanUp();
		return retVal;
	}


	/**
	 * For loading GUIs and skyboxes - models without texCoords nor
	 * normals vects.
	 * 
	 * @param positions
	 * @param dimensions
	 * @return
	 */
	public static RawModel store2DPositionsDataInVAOAndReturnRawModel(float[] positions) {
		final Loader l = GET_LOADER();
		final RawModel retVal = l.store2DPositionsDataInVAOAndReturnRawModel(positions);
		if (ALWAYS_CLEAN)
			l.cleanUp();
		return retVal;
	}


	/**
	 * For loading GUIs and skyboxes - models without texCoords nor
	 * normals vects.
	 * 
	 * @param positions
	 * @param dimensions
	 * @return
	 */
	public static RawModel store3DPositionsDataInVAOAndReturnRawModel(float[] positions) {
		final Loader l = GET_LOADER();
		final RawModel retVal = l.store3DPositionsDataInVAOAndReturnRawModel(positions);
		if (ALWAYS_CLEAN)
			l.cleanUp();
		return retVal;
	}


	private static Loader GET_LOADER() {
		return USE_SINGLETON_LOADER ? SINGLETON_LOADER : new Loader();
	}


	private MasterLoader() {}


}
