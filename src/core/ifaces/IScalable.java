package core.ifaces;


/**
 * @author storm
 *
 */
public interface IScalable extends IHasScale {

	@Override
	float getScale();


	void setScale(float scale);

}
