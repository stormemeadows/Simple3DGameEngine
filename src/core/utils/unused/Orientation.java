package core.utils.unused;


import org.lwjgl.util.vector.Vector3f;

/**
 * @author storm
 *
 */
public class Orientation extends Vector3f implements Orientable<Float> {

	private static final long serialVersionUID = 1L;


	/**
	 * @param x
	 * @param y
	 * @param z
	 */
	public Orientation(float x, float y, float z) {
		super(x, y, z);
	}


	@Override
	public Float getRotX() {
		return x;
	}


	@Override
	public Float getRotY() {
		return y;
	}


	@Override
	public Float getRotZ() {
		return z;
	}

}
