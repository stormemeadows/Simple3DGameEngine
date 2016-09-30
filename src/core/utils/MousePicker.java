package core.utils;


import static settings.GameConstants.MousePickerParameters.RAY_RANGE;
import static settings.GameConstants.MousePickerParameters.RECURSION_COUNT;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import core.ifaces.ITerrainable;

public class MousePicker {


	private final Matrix4f	projectionMatrix;
	private final Camera	camera;

	private Matrix4f			viewMatrix;
	private Vector3f			currentTerrainPoint;
	private ITerrainable<?, ?>	terrain;
	private Vector3f			currentRay	= new Vector3f();


	public MousePicker(Camera cam, Matrix4f projection, ITerrainable<?, ?> ter) {
		camera = cam;
		projectionMatrix = projection;

		updateViewMatrix();
		// viewMatrix = Maths.createViewMatrix(camera);
		updateTerrain(ter);
	}


	public Vector3f getCurrentTerrainPoint() {
		return currentTerrainPoint;
	}


	public Vector3f getCurrentRay() {
		return currentRay;
	}


	public void update() {
		updateViewMatrix();
		updateCurrentRay();
		// updateTerrain();
		updateCurrentTerrainPoint();
	}


	private void updateViewMatrix() {
		viewMatrix = Maths.createViewMatrix(camera);
	}


	private void updateCurrentRay() {
		currentRay = calculateMouseRay();
	}


	private void updateCurrentTerrainPoint() {
		if (intersectionInRangeQ(0, RAY_RANGE, currentRay))
			currentTerrainPoint = binarySearch(0, 0, RAY_RANGE, currentRay);
		else
			currentTerrainPoint = null;
	}


	private void updateTerrain(ITerrainable<?, ?> ter) {
		terrain = ter;
	}


	/**
	 * TODO modify this to return the terrain at specified coords
	 * 
	 * @param worldX
	 * @param worldZ
	 * @return
	 */
	private ITerrainable<?, ?> getTerrain(float worldX, float worldZ) {
		return terrain;
	}


	private Vector3f calculateMouseRay() {

		// get mouse's coords on screen
		final float mouseX = Mouse.getX();
		final float mouseY = Mouse.getY();

		final Vector2f normalizedCoords = getNormalizedMouseCoords(mouseX, mouseY);
		final Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1.0f, 1.0f);
		final Vector4f eyeCoords = toEyeCoords(clipCoords);
		final Vector3f worldRay = toWorldCoords(eyeCoords);

		return worldRay;
	}


	private Vector3f toWorldCoords(Vector4f eyeCoords) {
		final Matrix4f invertedView = Matrix4f.invert(viewMatrix, null);
		final Vector4f rayWorld = Matrix4f.transform(invertedView, eyeCoords, null);
		final Vector3f mouseRay = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
		mouseRay.normalise();
		return mouseRay;
	}


	private Vector4f toEyeCoords(Vector4f clipCoords) {
		final Matrix4f invertedProjection = Matrix4f.invert(projectionMatrix, null);
		final Vector4f eyeCoords = Matrix4f.transform(invertedProjection, clipCoords, null);

		// set z component to -1.0f so vector points into the screen
		return new Vector4f(eyeCoords.x, eyeCoords.y, -1.0f, 0.0f);
	}


	/**
	 * Converts mouse's screen coords to OpenGL's coord system.
	 * Note that LWJGL uses an unusual coord system for mouse coords in that the
	 * origin is at the bottom left of the screen instead of the top left.
	 * 
	 * So if not using LWJGL, probably need 'return new Vector2f(x, -y);'
	 * 
	 * @param mouseX
	 * @param mouseY
	 * 
	 * @return The mouse's screen coords in OpenGL's coord system.
	 */
	private Vector2f getNormalizedMouseCoords(float mouseX, float mouseY) {
		final float x = (2.0f * mouseX) / Display.getWidth() - 1.0f;
		final float y = (2.0f * mouseY) / Display.getHeight() - 1.0f;
		// final float x = normaliseMouseCoord(mouseX, Display.getWidth());
		// final float y = normaliseMouseCoord(mouseY, Display.getHeight());
		return new Vector2f(x, y);
	}
	// private float normaliseMouseCoord(float coord, float fctr) {
	// return (2.0f * coord) / fctr - 1.0f;
	// }


	// **********************************************************

	private Vector3f getPointOnRay(Vector3f ray, float distance) {
		final Vector3f camPos, start, scaledRay;
		camPos = camera.getPosition();
		start = new Vector3f(camPos.x, camPos.y, camPos.z);
		scaledRay = new Vector3f(ray.x * distance, ray.y * distance, ray.z * distance);
		return Vector3f.add(start, scaledRay, null);
	}


	private Vector3f binarySearch(int count, float start, float finish, Vector3f ray) {
		final float half = start + ((finish - start) / 2.0f);

		if (count >= RECURSION_COUNT) {
			final Vector3f endPoint = getPointOnRay(ray, half);
			final ITerrainable<?, ?> terrain = getTerrain(endPoint.getX(), endPoint.getZ());

			if (terrain != null)
				return endPoint;
			else
				return null;
		}

		if (intersectionInRangeQ(start, half, ray))
			return binarySearch(count + 1, start, half, ray);
		else
			return binarySearch(count + 1, half, finish, ray);
	}


	private boolean intersectionInRangeQ(float start, float finish, Vector3f ray) {
		final Vector3f startPoint = getPointOnRay(ray, start);
		final Vector3f endPoint = getPointOnRay(ray, finish);

		return !underGroundQ(startPoint) && underGroundQ(endPoint);
	}


	private boolean underGroundQ(Vector3f testPoint) {
		final ITerrainable<?, ?> terrain = getTerrain(testPoint.getX(), testPoint.getZ());
		float height = 0;
		if (terrain != null)
			height = terrain.getHeightOfTerrain(testPoint.getX(), testPoint.getZ());
		return testPoint.y < height;
	}

}
