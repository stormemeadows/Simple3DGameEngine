package gameEngine.loaders;


import static settings.GameConstants.Folders.DELIMITER;
import static settings.GameConstants.Folders.RESOURCES;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 * @author storm
 *
 */
class ObjFileReader {

	float[]	verticesArray, textureCoordsArray, normalsArray;
	int[]	indicesArray;


	ObjFileReader(String fileName) {
		readInDataFromObjFile(fileName);
	}


	// RawModel loadRawModelFromObjFile(String fileName) {
	private void readInDataFromObjFile(String fileName) {
		FileReader fr = null;
		try {
			fr = new FileReader(new File(RESOURCES + fileName + ".obj"));
		} catch (final FileNotFoundException e) {
			System.err.println("Could not load file!");
			e.printStackTrace();
		}
		final BufferedReader reader = new BufferedReader(fr);
		String line;
		final List<Vector3f> vertices = new ArrayList<Vector3f>();
		final List<Vector2f> textureCoords = new ArrayList<Vector2f>();
		final List<Vector3f> normals = new ArrayList<Vector3f>();
		final List<Integer> indices = new ArrayList<Integer>();

		// float[] verticesArray = null;
		// float[] textureCoordsArray = null;
		// float[] normalsArray = null;
		// int[] indicesArray = null;

		try {
			while (true) {
				line = reader.readLine();
				final String[] currentLine = line.split(" ");

				// handle vertex coords
				if (line.startsWith("v ")) {
					final Vector3f vertex = new Vector3f(
							Float.parseFloat(currentLine[1]),
							Float.parseFloat(currentLine[2]),
							Float.parseFloat(currentLine[3]));
					vertices.add(vertex);

					// handle texture coords
				} else if (line.startsWith("vt ")) {
					final Vector2f texture = new Vector2f(
							Float.parseFloat(currentLine[1]),
							Float.parseFloat(currentLine[2]));
					textureCoords.add(texture);

					// handle normal vects
				} else if (line.startsWith("vn ")) {
					final Vector3f normal = new Vector3f(
							Float.parseFloat(currentLine[1]),
							Float.parseFloat(currentLine[2]),
							Float.parseFloat(currentLine[3]));
					normals.add(normal);

					// reached the faces section
				} else if (line.startsWith("f ")) {
					textureCoordsArray = new float[vertices.size() * 2];
					normalsArray = new float[vertices.size() * 3];
					break;
				}
			} // end while

			// handle triangle faces
			while (line != null) {
				if (!line.startsWith("f ")) {
					line = reader.readLine();
					continue;
				}
				final String[] currentLine = line.split(" ");
				final String[] vertex1 = currentLine[1].split(DELIMITER);
				final String[] vertex2 = currentLine[2].split(DELIMITER);
				final String[] vertex3 = currentLine[3].split(DELIMITER);

				// process the current triangle, sorting the texture and normal
				// coords into the expected positions
				processVertex(vertex1, indices, textureCoords, normals, textureCoordsArray, normalsArray);
				processVertex(vertex2, indices, textureCoords, normals, textureCoordsArray, normalsArray);
				processVertex(vertex3, indices, textureCoords, normals, textureCoordsArray, normalsArray);
				line = reader.readLine();
			}
			reader.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}

		verticesArray = new float[vertices.size() * 3];
		indicesArray = new int[indices.size()];

		int vertexPointer = 0;
		for (final Vector3f vertex:vertices) {
			verticesArray[vertexPointer++] = vertex.x;
			verticesArray[vertexPointer++] = vertex.y;
			verticesArray[vertexPointer++] = vertex.z;
		}

		for (int i = 0; i < indices.size(); i++) {
			indicesArray[i] = indices.get(i);
		}

		// return MasterLoader.loadToVAO(verticesArray, textureCoordsArray,
		// normalsArray, indicesArray);
	} // end loadObjModel


	private static void processVertex(String[] vertexData,
			List<Integer> indices, List<Vector2f> textureCoords, List<Vector3f> normals,
			float[] textureCoordsArray, float[] normalsArray) {

		// obj files start at 1, hence the -1
		final int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
		indices.add(currentVertexPointer);

		// process textureCoords
		final Vector2f currentTex = textureCoords.get(Integer.parseInt(vertexData[1]) - 1);
		textureCoordsArray[currentVertexPointer * 2] = currentTex.x;
		// OpenGL starts from the top left, but Blender (the program in which we
		// create our models) starts from the bottom left, hench 1-currentTex.y
		textureCoordsArray[(currentVertexPointer * 2) + 1] = 1 - currentTex.y;

		// process normals
		final Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);
		normalsArray[(currentVertexPointer * 3)] = currentNorm.x;
		normalsArray[(currentVertexPointer * 3) + 1] = 1 - currentNorm.y;
		normalsArray[(currentVertexPointer * 3) + 2] = 1 - currentNorm.z;

	}

}
