package core.utils.unused;

/**
 * @author storm
 *
 */
public class Position implements Positionable<Float> {

	public Float x, y, z;


	/**
	 * @param x
	 * @param y
	 * @param z
	 */
	public Position(Float x, Float y, Float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}


	/**
	 * @return the x
	 */
	@Override
	public Float getX() {
		return x;
	}


	/**
	 * @return the y
	 */
	@Override
	public Float getY() {
		return y;
	}


	/**
	 * @return the z
	 */
	@Override
	public Float getZ() {
		return z;
	}


}
