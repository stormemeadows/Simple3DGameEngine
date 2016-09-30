package core.utils.unused;

/**
 * @author storm
 *
 */
public abstract class AbstractEntity<O extends Orientable<?>, P extends Positionable<?>, S extends Scalable<?>> {

	private O	orientation;
	private P	position;
	private S	scale;


	/**
	 * @param orientation
	 * @param position
	 * @param scale
	 */
	public AbstractEntity(O orientation, P position, S scale) {
		super();
		this.orientation = orientation;
		this.position = position;
		this.scale = scale;
	}


	/**
	 * @return the orientation
	 */
	public O getOrientation() {
		return orientation;
	}


	/**
	 * @return the position
	 */
	public P getPosition() {
		return position;
	}


	/**
	 * @return the scale
	 */
	public S getScale() {
		return scale;
	}


}
