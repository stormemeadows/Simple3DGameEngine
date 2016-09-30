package core.utils.unused;

/**
 * @author storm
 *
 */
public class Scale implements Scalable<Float> {

	private float size;


	public Scale(float size) {
		this.size = size;
	}


	@Override
	public void setScale(Float size) {
		this.size = size;
	}


	@Override
	public Float getScale() {
		return size;
	}

}
