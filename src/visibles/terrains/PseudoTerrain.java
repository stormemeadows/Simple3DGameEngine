package visibles.terrains;

/**
 * 
 * 
 * TODO DELETE THIS
 * 
 * 
 * 
 * @author storm
 */
public class PseudoTerrain {
	// public class PseudoTerrain extends
	// LightAffectedTexturedModel<TerrainTexture, RawModel>
	// implements ITerrainable<TerrainTexture, RawModel>, IHasPosition3D {
	//
	// /**
	// * @param textureFileName
	// * @param modelObjFileName
	// */
	// public PseudoTerrain(String textureFileName, String modelObjFileName) {
	// super(textureFileName, modelObjFileName);
	// // TODO Auto-generated constructor stub
	// }
	//
	//
	// /**
	// * @param lightAffectedTexture
	// * @param model
	// */
	// public PseudoTerrain(TerrainTexture lightAffectedTexture, RawModel model)
	// {
	// super(lightAffectedTexture, model);
	// // TODO Auto-generated constructor stub
	// }
	//
	// private float x;
	// private float z;
	// private float fakeY = 0;
	//
	// private BasicBlendedTexture<TerrainTexture> texBlender;
	//
	// private float[][] heightsMatrix;
	//
	//
	// private PseudoTerrain() {
	// this(new TerrainTexture(0), new RawModel(0, 0));
	// }
	//
	//
	// /**
	// * @param gridX
	// * @param gridZ
	// * @param loader
	// * @param texturePack
	// * @param blendMap
	// * @param heightMapFileName
	// * @deprecated Use {@link
	// #PseudoTerrain(int,int,String,BasicBlendedTexture
	// * <TerrainTexture>)} instead
	// */
	// @Deprecated
	// public PseudoTerrain(int gridX, int gridZ,
	// BasicBlendedTexture<TerrainTexture> texBlender,
	// String heightMapFileName) {
	// this(gridX, gridZ, heightMapFileName, texBlender);
	// }
	//
	//
	// /**
	// * @param gridX
	// * @param gridZ
	// * @param heightMapFileName
	// * @param loader
	// * @param texturePack
	// * @param blendMap
	// */
	// public PseudoTerrain(int gridX, int gridZ,
	// String heightMapFileName,
	// BasicBlendedTexture<TerrainTexture> texBlender) {
	// this();
	// this.x = gridX * SIZE;
	// this.z = gridZ * SIZE;
	// this.fakeY = 0;
	// this.model = generateTerrain(heightMapFileName);
	// this.texBlender = texBlender;
	// }
	//
	//
	// @Override
	// public TerrainTexture[] getAllTextures() {
	// return (TerrainTexture[]) texBlender.toArray();
	// }
	// // /**
	// // * @return the texBlender
	// // */
	// // public BasicBlendedTexture<TerrainTexture> getTexBlender() {
	// // return texBlender;
	// // }
	//
	//
	// /**
	// * @return the blendMap
	// */
	// public TerrainTexture getBlendMap() {
	// return texBlender.getBlendMap();
	// }
	//
	//
	// /**
	// * @return the orientation
	// */
	// @Override
	// public Vector3f getPosition() {
	// return new Vector3f(x, fakeY, z);
	// }
	//
	//
	// /**
	// * @return the x
	// */
	// @Override
	// public float getX() {
	// return x;
	// }
	//
	//
	// /**
	// * @return the x
	// */
	// @Override
	// public float getXCoordInWorldGrid() {
	// return x;
	// }
	//
	//
	// /**
	// * @return the z
	// */
	// @Override
	// public float getZ() {
	// return z;
	// }
	//
	//
	// /**
	// * @return the z
	// */
	// @Override
	// public float getZCoordInWorldGrid() {
	// return z;
	// }
	//
	//
	// /**
	// * @return the fakeY
	// */
	// @Override
	// public float getY() {
	// return fakeY;
	// }
	//
	//
	// /**
	// * @return the model
	// */
	// @Override
	// public RawModel getModel() {
	// return model;
	// }
	//
	//
	// /**
	// * @param dim
	// * The dimensions of the matrix.
	// * Square matrix, so just one dim needs to be specified.
	// */
	// private void createHeightMatrix(int dim) {
	// this.heightsMatrix = new float[dim][dim];
	// }
	//
	//
	// private int getDimensionOfHeightMatrix() {
	// return this.heightsMatrix.length;
	// }
	//
	//
	// private float getY(int idxX, int idxZ) {
	// return heightsMatrix[idxX][idxZ];
	// }
	//
	//
	// @Override
	// public float getHeightOfTerrain(float worldX, float worldZ) {
	// final float terrainX = worldX - this.x;
	// final float terrainZ = worldZ - this.z;
	//
	// final float heightsMatrixDimMinusOne = getDimensionOfHeightMatrix() - 1;
	// final float gridSquareSize = SIZE / heightsMatrixDimMinusOne;
	//
	// final int gridX = (int) Math.floor(terrainX / gridSquareSize);
	// final int gridZ = (int) Math.floor(terrainZ / gridSquareSize);
	//
	// // H.pln("WorldCoords:(" + worldX + "," + worldZ + ")",
	// // "GridCoords: (" + gridX + "," + gridZ + ")");
	//
	// // early return
	// if (gridX >= heightsMatrixDimMinusOne || gridX < 0
	// || gridZ >= heightsMatrixDimMinusOne || gridZ < 0) {
	// // System.out.print("Height: 0\n");
	// return 0;
	// }
	//
	// // Line partitioning the 2 triangles of square is x+z-1=0, or x=1-z, so
	// // x > 1-z ==> upper triangle, and x < 1-z ==> lower triangle.
	// // Since lines have no thickness, use of >= vs <= is arbitrary.
	// final float xCoord = (terrainX % gridSquareSize) / gridSquareSize;
	// final float zCoord = (terrainZ % gridSquareSize) / gridSquareSize;
	// final float center = getY(gridX + 1, gridZ);
	// float p1x, p1y, p2y, p2z;
	// if (xCoord <= (1 - zCoord)) {
	// p1x = 0;
	// p1y = getY(gridX, gridZ);
	// p2y = center;
	// p2z = 0;
	// } else {
	// p1x = 1;
	// p1y = center;
	// p2y = getY(gridX + 1, gridZ + 1);
	// p2z = 1;
	// }
	// final Vector3f p1 = new Vector3f(p1x, p1y, 0);
	// final Vector3f p2 = new Vector3f(1, p2y, p2z);
	// final Vector3f p3 = new Vector3f(0, getY(gridX, gridZ + 1), 1);
	// final Vector2f pos = new Vector2f(xCoord, zCoord);
	// // System.out.print("Height: 0\n");
	// return Maths.barryCentric(p1, p2, p3, pos);
	// }
	//
	//
	// /**
	// * MAKE SURE THAT YOUR CAMERA IS IN A POSITION THAT ALLOWS YOU TO SEE
	// * THE TERRAIN! MAKE SURE THE CAMERA'S Y POSITION IS ABOVE 0, AND MAKE
	// * SURE THAT THE TERRAIN IS INFRONT OF THE CAMERA (EITHER MOVE THE
	// * CAMERA BACK, ROTATE THE CAMERA AROUND, OR CHOOSE NEGATIVE GRIDX &
	// * GRIDZ VALUES WHEN CALLING THE TERRAIN CONSTRUCTOR).
	// *
	// * @param loader
	// * @param heightMapFileName
	// * @return
	// */
	// // private RawModel generateTerrain(Loader loader, String
	// heightMapFileName)
	// // {
	// private RawModel generateTerrain(String heightMapFileName) {
	//
	// BufferedImage image = null;
	// try {
	// image = ImageIO.read(new File(RESOURCES + heightMapFileName +
	// TEX_FILE_EXT));
	// } catch (final IOException e) {
	// e.printStackTrace();
	// }
	//
	// final int numVerts = image.getHeight(); // num verts per side
	// final int numVertsMinusOne = numVerts - 1;
	//
	// createHeightMatrix(numVerts);
	//
	// final int count = numVerts * numVerts; // num entries in square matrix
	// final float[] verticesArray = new float[count * 3];
	// final float[] normalsArray = new float[count * 3];
	// final float[] textureCoordsArray = new float[count * 2];
	//
	// int vertPtr = 0;
	// for (int i = 0; i < numVerts; i++) {
	// for (int j = 0; j < numVerts; j++) {
	//
	// // get verticesArray' positions
	// final float height = getHeightAtPointInImage(j, i, image);
	// heightsMatrix[j][i] = height;
	// verticesArray[vertPtr * 3] = j / ((float) numVertsMinusOne) * SIZE;
	// verticesArray[vertPtr * 3 + 1] = height;
	// verticesArray[vertPtr * 3 + 2] = i / ((float) numVertsMinusOne) * SIZE;
	//
	// // get surface normalsArray
	// final Vector3f normal = calculateNormalAtPointInImage(j, i, image);
	// normalsArray[vertPtr * 3] = normal.x;
	// normalsArray[vertPtr * 3 + 1] = normal.y;
	// normalsArray[vertPtr * 3 + 2] = normal.z;
	//
	// // get textures' coords
	// textureCoordsArray[vertPtr * 2] = j / ((float) numVertsMinusOne);
	// textureCoordsArray[vertPtr * 2 + 1] = i / ((float) numVertsMinusOne);
	//
	// vertPtr++;
	// }
	// }
	//
	// final int[] indicesArray = new int[6 * (numVertsMinusOne) *
	// (numVertsMinusOne)];
	// int ptr = 0;
	// for (int gz = 0; gz < numVertsMinusOne; gz++) {
	// for (int gx = 0; gx < numVertsMinusOne; gx++) {
	// final int topLeft = (gz * numVerts) + gx;
	// final int bottomLeft = ((gz + 1) * numVerts) + gx;
	// final int topRight = topLeft + 1;
	// final int bottomRight = bottomLeft + 1;
	// indicesArray[ptr++] = topLeft;
	// indicesArray[ptr++] = bottomLeft;
	// indicesArray[ptr++] = topRight;
	// indicesArray[ptr++] = topRight;
	// indicesArray[ptr++] = bottomLeft;
	// indicesArray[ptr++] = bottomRight;
	// }
	// }
	//
	// // return loader.loadToVAO(verticesArray, textureCoordsArray,
	// // normalsArray,indicesArray);
	// // return new Loader().loadToVAO(verticesArray, textureCoordsArray,
	// // normalsArray, indicesArray);
	// return MasterLoader.loadToVAO(verticesArray, textureCoordsArray,
	// normalsArray, indicesArray);
	//
	// } // end loadObjModel
	//
	//
	// /**
	// * Faster way to estimate surface normalsArray of terrain.
	// * Approximates using neighbors' heights.
	// *
	// * @param x
	// * @param z
	// * @param heightMapFileName
	// * @return The surface normal at the location.
	// */
	// private Vector3f calculateNormalAtPointInImage(int x, int z,
	// BufferedImage heightMapFileName) {
	// final float heightL = getHeightAtPointInImage(x - 1, z,
	// heightMapFileName);
	// final float heightR = getHeightAtPointInImage(x + 1, z,
	// heightMapFileName);
	// final float heightD = getHeightAtPointInImage(x, z - 1,
	// heightMapFileName);
	// final float heightU = getHeightAtPointInImage(x, z + 1,
	// heightMapFileName);
	//
	// final Vector3f normal = new Vector3f(heightL - heightR, 2f, heightD -
	// heightU);
	// normal.normalise();
	//
	// return normal;
	// }
	//
	//
	// private float getHeightAtPointInImage(int x, int z, BufferedImage image)
	// {
	//
	// // just return 0 if out of bounds, for now
	// if (x < 0 || x >= image.getHeight() || z < 0 || z >= image.getHeight()) {
	// return 0; }
	//
	// float height = image.getRGB(x, z);
	// height += MAX_PIXEL_COLOUR / 2f;
	// height /= MAX_PIXEL_COLOUR / 2f;
	// height *= MAX_HEIGHT;
	//
	// return height;
	// }

}
