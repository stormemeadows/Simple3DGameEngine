package visibles.terrains.utils;


import static settings.GameConstants.Folders.RESOURCES;
import static settings.GameConstants.TerrainParameters.MAX_HEIGHT;
import static settings.GameConstants.TerrainParameters.MAX_PIXEL_COLOUR;
import static settings.GameConstants.TerrainParameters.SIZE;
import static settings.GameConstants.TextureParameters.TEX_FILE_EXT;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector3f;

import gameEngine.loaders.MasterLoader;
import models.RawModel;

/**
 * @author storm
 *
 */
public class TerrainGenerator {

	public RawModel		model;
	public float[][]	heightsMatrix;


	/**
	 * @param heightMapFileName
	 */
	public TerrainGenerator(String heightMapFileName) {
		generateTerrain(heightMapFileName);
	}


	/**
	 * Creates the heightsMatrix and model.
	 * 
	 * MAKE SURE THAT YOUR CAMERA IS IN A POSITION THAT ALLOWS YOU TO SEE
	 * THE TERRAIN! MAKE SURE THE CAMERA'S Y POSITION IS ABOVE 0, AND MAKE
	 * SURE THAT THE TERRAIN IS INFRONT OF THE CAMERA (EITHER MOVE THE
	 * CAMERA BACK, ROTATE THE CAMERA AROUND, OR CHOOSE NEGATIVE GRIDX &
	 * GRIDZ VALUES WHEN CALLING THE TERRAIN CONSTRUCTOR).
	 * 
	 * @param heightMapFileName
	 *            // * @return
	 */
	// private RawModel generateTerrain(String heightMapFileName) {
	private void generateTerrain(String heightMapFileName) {

		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(RESOURCES + heightMapFileName + TEX_FILE_EXT));
		} catch (final IOException e) {
			e.printStackTrace();
		}

		final int numVerts = image.getHeight(); // num verts per side
		final int numVertsMinusOne = numVerts - 1;
		initHeightsMatrix(numVerts);

		// num entries in square matrix
		final int numEntries = numVerts * numVerts;
		final float[] verticesArray = new float[numEntries * 3];
		final float[] normalsArray = new float[numEntries * 3];
		final float[] textureCoordsArray = new float[numEntries * 2];

		int vertPtr = 0;
		for (int i = 0; i < numVerts; i++) {
			for (int j = 0; j < numVerts; j++) {

				// get verticesArray' positions
				final float height = getHeightAtPointInImage(j, i, image);
				heightsMatrix[j][i] = height;
				verticesArray[vertPtr * 3] = j / ((float) numVertsMinusOne) * SIZE;
				verticesArray[vertPtr * 3 + 1] = height;
				verticesArray[vertPtr * 3 + 2] = i / ((float) numVertsMinusOne) * SIZE;

				// get surface normalsArray
				final Vector3f normal = calculateNormalAtPointInImage(j, i, image);
				normalsArray[vertPtr * 3] = normal.x;
				normalsArray[vertPtr * 3 + 1] = normal.y;
				normalsArray[vertPtr * 3 + 2] = normal.z;

				// get textures' coords
				textureCoordsArray[vertPtr * 2] = j / ((float) numVertsMinusOne);
				textureCoordsArray[vertPtr * 2 + 1] = i / ((float) numVertsMinusOne);

				vertPtr++;
			}
		}

		final int[] indicesArray = new int[6 * (numVertsMinusOne) * (numVertsMinusOne)];
		int ptr = 0;
		for (int gz = 0; gz < numVertsMinusOne; gz++) {
			for (int gx = 0; gx < numVertsMinusOne; gx++) {
				final int topLeft = (gz * numVerts) + gx;
				final int topRight = topLeft + 1;
				final int bottomLeft = ((gz + 1) * numVerts) + gx;
				final int bottomRight = bottomLeft + 1;
				indicesArray[ptr++] = topLeft;
				indicesArray[ptr++] = bottomLeft;
				indicesArray[ptr++] = topRight;
				indicesArray[ptr++] = topRight;
				indicesArray[ptr++] = bottomLeft;
				indicesArray[ptr++] = bottomRight;
			}
		}

		model = MasterLoader.loadToVAO(verticesArray, textureCoordsArray, normalsArray, indicesArray);
	}


	/**
	 * The dims of the dimXdim heightsMatrix.
	 */
	private void initHeightsMatrix(int dim) {
		heightsMatrix = new float[dim][dim];
	}


	/**
	 * Faster way to estimate surface normalsArray of terrain.
	 * Approximates using neighbors' heights.
	 * 
	 * @param x
	 * @param z
	 * @param heightMapFileName
	 * @return The surface normal at the location.
	 */
	private Vector3f calculateNormalAtPointInImage(int x, int z, BufferedImage heightMapFileName) {
		final float heightL = getHeightAtPointInImage(x - 1, z, heightMapFileName);
		final float heightR = getHeightAtPointInImage(x + 1, z, heightMapFileName);
		final float heightD = getHeightAtPointInImage(x, z - 1, heightMapFileName);
		final float heightU = getHeightAtPointInImage(x, z + 1, heightMapFileName);

		final Vector3f normal = new Vector3f(heightL - heightR, 2f, heightD - heightU);
		normal.normalise();

		return normal;
	}


	// just return 0 if out of bounds, for now
	private float getHeightAtPointInImage(int x, int z, BufferedImage image) {
		final int imgH = image.getHeight();
		if (x < 0 || x >= imgH || z < 0 || z >= imgH)
			return 0;

		float height = image.getRGB(x, z);
		height += MAX_PIXEL_COLOUR / 2f;
		height /= MAX_PIXEL_COLOUR / 2f;
		height *= MAX_HEIGHT;

		return height;
	}


}
