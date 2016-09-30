package visibles.terrains;


import static settings.GameConstants.TerrainParameters.SIZE;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import core.ifaces.IHasMultiTextures;
import core.ifaces.ITerrainable;
import core.utils.Maths;
import models.RawModel;
import textures.blenders.BasicBlendedTexture;
import visibles.terrains.utils.TerrainGenerator;

/**
 * @author storm
 *
 */
public class Terrain
		implements ITerrainable<TerrainTexture, RawModel>,
		IHasMultiTextures<TerrainTexture> {

	private final Vector2f	coordsInWorldGrid;
	private final RawModel	model;				// the actual terrain mesh

	private final BasicBlendedTexture<TerrainTexture> textures;

	private final float[][] heightsMatrix;


	/**
	 * @param gridX
	 * @param gridZ
	 * @param textures
	 * @param heightMapFileName
	 */
	public Terrain(int gridX, int gridZ, BasicBlendedTexture<TerrainTexture> tex,
			String heightMapFileName) {

		coordsInWorldGrid = new Vector2f(gridX * SIZE, gridZ * SIZE);
		textures = tex;

		final TerrainGenerator tGen = new TerrainGenerator(heightMapFileName);
		model = tGen.model;
		heightsMatrix = tGen.heightsMatrix;
	}


	@Override
	public TerrainTexture getTexture() {
		return textures.getBlendMap();
	}


	@Override
	public RawModel getModel() {
		return model;
	}


	@Override
	public float getXCoordInWorldGrid() {
		return coordsInWorldGrid.x;
	}


	@Override
	public float getZCoordInWorldGrid() {
		return coordsInWorldGrid.y;
	}


	@Override
	public TerrainTexture[] getAllTextures() {
		final TerrainTexture[] retVal = {
				textures.getBackgroundTexture(),
				textures.getrTexture(),
				textures.getgTexture(),
				textures.getbTexture(),
				textures.getBlendMap()
		};
		return retVal;
	}


	/**
	 * Given (x,z) global coordinates from the world,
	 * finds the grid in the world to which they belong.
	 * If the grid is in bounds, returns the height.
	 * 
	 * @param worldX
	 * @param worldZ
	 * @return
	 */
	@Override
	public float getHeightOfTerrain(float worldX, float worldZ) {
		final float terrainX = worldX - getXCoordInWorldGrid();
		final float terrainZ = worldZ - getZCoordInWorldGrid();

		final float heightsMatrixDimMinusOne = getDimOfHeightMatrix() - 1;
		final float gridSquareSize = SIZE / heightsMatrixDimMinusOne;

		final int gridX = (int) Math.floor(terrainX / gridSquareSize);
		final int gridZ = (int) Math.floor(terrainZ / gridSquareSize);

		// H.pln("WorldCoords:(" + worldX + "," + worldZ + ")",
		// "GridCoords: (" + gridX + "," + gridZ + ")");

		// early return
		if (gridX >= heightsMatrixDimMinusOne || gridX < 0 || gridZ >= heightsMatrixDimMinusOne || gridZ < 0) {
			// H.pln("Height: 0");
			return 0;
		}

		// Line partitioning the 2 triangles of square is x+z-1=0, or x=1-z, so
		// x > 1-z ==> upper triangle, and x < 1-z ==> lower triangle.
		// Since lines have no thickness, use of >= vs <= is arbitrary.
		final float xCoord = (terrainX % gridSquareSize) / gridSquareSize;
		final float zCoord = (terrainZ % gridSquareSize) / gridSquareSize;
		final float center = getHeightAtXZCoords(gridX + 1, gridZ);
		float p1x, p1y, p2y, p2z;
		if (xCoord <= (1 - zCoord)) {
			p1x = p2z = 0;
			p1y = getHeightAtXZCoords(gridX, gridZ);
			p2y = center;
			// p2z = 0;
		} else {
			p1x = p2z = 1;
			p1y = center;
			p2y = getHeightAtXZCoords(gridX + 1, gridZ + 1);
			// p2z = 1;
		}
		final Vector3f p1 = new Vector3f(p1x, p1y, 0);
		final Vector3f p2 = new Vector3f(1, p2y, p2z);
		final Vector3f p3 = new Vector3f(0, getHeightAtXZCoords(gridX, gridZ + 1), 1);
		final Vector2f pos = new Vector2f(xCoord, zCoord);
		// System.out.print("Height: 0\n");
		return Maths.barryCentric(p1, p2, p3, pos);
	}


	/**
	 * Height heightsMatrix entry at (x,z).
	 * 
	 * @param idxX
	 * @param idxZ
	 * @return the height
	 */
	private float getHeightAtXZCoords(int idxX, int idxZ) {
		return heightsMatrix[idxX][idxZ];
	}


	private int getDimOfHeightMatrix() {
		return heightsMatrix.length;
	}


	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

}
