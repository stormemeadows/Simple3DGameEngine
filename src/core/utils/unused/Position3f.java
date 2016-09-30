package core.utils.unused;


import org.lwjgl.util.vector.ReadableVector3f;
import org.lwjgl.util.vector.Vector3f;

/**
 * @author storm
 *
 */
public class Position3f extends Vector3f {
	private static final long serialVersionUID = 1L;


	/**
	 * @param src
	 */
	public Position3f(ReadableVector3f src) {
		super(src);
	}


	/**
	 * @param x
	 * @param y
	 * @param z
	 */
	public Position3f(float x, float y, float z) {
		super(x, y, z);
	}


}
