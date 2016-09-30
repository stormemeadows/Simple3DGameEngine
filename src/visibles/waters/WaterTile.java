package visibles.waters;


import org.lwjgl.util.vector.Vector3f;

public class WaterTile {

	private final Vector3f position;


	public WaterTile(Vector3f p) {
		this.position = p;
	}


	public WaterTile(float centerX, float centerZ, float height) {
		this(new Vector3f(centerX, height, centerZ));
	}


	public Vector3f getPosition() {
		return new Vector3f(position);
	}


	public float getHeight() {
		return getY();
	}


	public float getY() {
		return position.y;
	}


	public float getX() {
		return position.x;
	}


	public float getZ() {
		return position.z;
	}


}
