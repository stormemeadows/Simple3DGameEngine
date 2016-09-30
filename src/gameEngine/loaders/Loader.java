package gameEngine.loaders;


import static settings.GameConstants.Folders.RESOURCES;
import static settings.GameConstants.Folders.SKYBOX;
import static settings.GameConstants.OpenGLVAOParameters.NORMAL_VECTS_DIM;
import static settings.GameConstants.OpenGLVAOParameters.NORMAL_VECTS_IDX;
import static settings.GameConstants.OpenGLVAOParameters.POSITION_DIM;
import static settings.GameConstants.OpenGLVAOParameters.POSITION_IDX;
import static settings.GameConstants.OpenGLVAOParameters.TEX_COORDS_DIM;
import static settings.GameConstants.OpenGLVAOParameters.TEX_COORDS_IDX;
import static settings.GameConstants.TextureParameters.TEX_FILE_EXT;
import static settings.GameConstants.TextureParameters.TEX_FILE_TYPE;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import models.RawModel;


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
class Loader {

	static long INSTANCE_COUNT = 0;

	private final List<Integer>	vaoIDs	= new ArrayList<Integer>();
	private final List<Integer>	vboIDs	= new ArrayList<Integer>();
	private final List<Integer>	texIDs	= new ArrayList<Integer>();


	Loader() {
		System.out.println(++INSTANCE_COUNT);
	}


	void cleanUp() {
		cleanUpVertexArrays();
		cleanUpVertexBuffers();
		cleanUpTextures();
	}


	int load2DTexFromPNGFileAndReturnTexID(String fileName) {
		final int texType = GL11.GL_TEXTURE_2D;
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture(TEX_FILE_TYPE,
					new FileInputStream(RESOURCES + fileName + TEX_FILE_EXT));
			GL30.glGenerateMipmap(texType);
			// GL11.glTexParameteri(texType,
			// GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_NEAREST);
			GL11.glTexParameteri(texType, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameterf(texType, GL14.GL_TEXTURE_LOD_BIAS, -0.4f);
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}

		final int texID = texture.getTextureID();
		texIDs.add(texID);
		return texID;
	}


	/**
	 * 
	 * @param dir
	 * @param textureFiles
	 * @return texureID
	 */
	int loadCubeMapFromPNGfilesAndReturnTexID(String dir, String[] textureFiles) {

		final int texType = GL13.GL_TEXTURE_CUBE_MAP;
		final int texID = GL11.glGenTextures();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(texType, texID);

		final int firstCubeMapFace = GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X;
		final int format = GL11.GL_RGBA;
		final int level = 0;
		final int border = 0;
		final int type = GL11.GL_UNSIGNED_BYTE;
		String texFileName;
		PNGFileTextureReader texData;
		for (int i = 0; i < textureFiles.length; i++) {
			texFileName = dir + textureFiles[i] + TEX_FILE_EXT;
			System.out.println("Loading cube map file: " + texFileName);
			texData = extractTextureDataFromPNGFile(texFileName);

			GL11.glTexImage2D(firstCubeMapFace + i, level, format,
					texData.getWidth(), texData.getHeight(),
					border, format, type,
					texData.getBuffer());
		}

		final int texParam1 = GL11.GL_LINEAR;
		final int texParam2 = GL12.GL_CLAMP_TO_EDGE;
		GL11.glTexParameteri(texType, GL11.GL_TEXTURE_MAG_FILTER, texParam1);
		GL11.glTexParameteri(texType, GL11.GL_TEXTURE_MIN_FILTER, texParam1);
		GL11.glTexParameteri(texType, GL11.GL_TEXTURE_WRAP_S, texParam2);
		GL11.glTexParameteri(texType, GL11.GL_TEXTURE_WRAP_T, texParam2);

		texIDs.add(texID);

		return texID;
	}


	/**
	 * Uses the default visibles.skybox directory. @see
	 * GameConstants.Folders.SKYBOX
	 * 
	 * @param dir
	 * @param textureFiles
	 * @return texureID
	 */
	int loadCubeMapFromPNGfilesAndReturnTexID(String[] textureFiles) {
		return loadCubeMapFromPNGfilesAndReturnTexID(SKYBOX, textureFiles);
	}


	/**
	 * @param positions
	 * @param textureCoords
	 * @param normals
	 * @param indices
	 * @return
	 */
	RawModel loadToVAO(float[] positions, float[] texCoords,
			float[] normalVects, int[] indices) {

		final int vaoID = genAndBindVAOAndReturnID();
		final int numVertices = indices.length;

		genAndBindVBOAndStoreInts(indices);
		storeDataInVertexAttribArray(POSITION_IDX, POSITION_DIM, positions);
		storeDataInVertexAttribArray(TEX_COORDS_IDX, TEX_COORDS_DIM, texCoords);
		storeDataInVertexAttribArray(NORMAL_VECTS_IDX, NORMAL_VECTS_DIM, normalVects);

		unbindVAO();

		return new RawModel(vaoID, numVertices);
	}


	/**
	 * For loading GUIs and skyboxes - models without texCoords nor
	 * normals vects.
	 * 
	 * @param positions
	 * @param dimensions
	 * @return
	 */
	RawModel store2DPositionsDataInVAOAndReturnRawModel(float[] positions) {
		return storeNDPositionsDataInVAOAndReturnRawModel(positions, 2);
	}


	/**
	 * For loading GUIs and skyboxes - models without texCoords nor
	 * normals vects.
	 * 
	 * @param positions
	 * @param dimensions
	 * @return
	 */
	RawModel store3DPositionsDataInVAOAndReturnRawModel(float[] positions) {
		return storeNDPositionsDataInVAOAndReturnRawModel(positions, 3);
	}


	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("Number " + INSTANCE_COUNT + " was deleted. Remaining: " + --INSTANCE_COUNT);
		cleanUp();
	}


	private void cleanUpTextures() {
		for (final int textureID:texIDs)
			GL11.glDeleteTextures(textureID);
	}


	private void cleanUpVertexArrays() {
		for (final int vaoID:vaoIDs)
			GL30.glDeleteVertexArrays(vaoID);
	}


	private void cleanUpVertexBuffers() {
		for (final int vboID:vboIDs)
			GL15.glDeleteBuffers(vboID);
	}


	private PNGFileTextureReader extractTextureDataFromPNGFile(String fileName) {
		return new PNGFileTextureReader(fileName);
		// int width = 0;
		// int height = 0;
		// ByteBuffer buffer = null;
		// try {
		// final FileInputStream pngFile = new FileInputStream(fileName);
		// final PNGDecoder decoder = new PNGDecoder(pngFile);
		//
		// width = decoder.getWidth();
		// height = decoder.getHeight();
		//
		// buffer = ByteBuffer.allocateDirect(4 * width * height);
		// decoder.decode(buffer, width * 4, Format.RGBA);
		// buffer.flip();
		//
		// pngFile.close();
		// } catch (final Exception e) {
		// e.printStackTrace();
		// System.err.println("Tried to load texture " + fileName + ", didn't
		// work");
		// System.exit(-1);
		// }
		// return new TextureData(buffer, width, height);
	}


	private int genAndBindVAOAndReturnID() {
		final int vaoID = genVAOAndReturnID();
		vaoIDs.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}


	private void genAndBindVBOAndStoreFloats(float[] data) {
		final int vboID = genVBOAndReturnID();
		vboIDs.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		final FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}


	private void genAndBindVBOAndStoreInts(int[] indices) {
		final int vboID = genVBOAndReturnID();
		vboIDs.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		final IntBuffer buffer = storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}


	private int genVAOAndReturnID() {
		final int vaoID = GL30.glGenVertexArrays();
		return vaoID;
	}


	private int genVBOAndReturnID() {
		final int vboID = GL15.glGenBuffers();
		return vboID;
	}


	private FloatBuffer storeDataInFloatBuffer(float[] data) {
		final FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}


	private IntBuffer storeDataInIntBuffer(int[] data) {
		final IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}


	private void storeDataInVertexAttribArray(int attribIdx, int attribDimension, float[] data) {
		genAndBindVBOAndStoreFloats(data);

		GL20.glVertexAttribPointer(attribIdx, attribDimension, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}


	/**
	 * For loading GUIs and skyboxes - models without texCoords nor
	 * normals vects.
	 * 
	 * @param positions
	 * @param dimensions
	 * @return
	 */
	private RawModel storeNDPositionsDataInVAOAndReturnRawModel(float[] positions, int numDims) {
		final int numVertices = positions.length / numDims;
		final int vaoID = genAndBindVAOAndReturnID();

		storeDataInVertexAttribArray(POSITION_IDX, numDims, positions);
		unbindVAO();

		return new RawModel(vaoID, numVertices);
	}


	private void unbindVAO() {
		GL30.glBindVertexArray(0);
	}

}
