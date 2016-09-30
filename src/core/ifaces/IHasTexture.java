package core.ifaces;


/**
 * @author storm
 *
 */
public interface IHasTexture<T extends ITexturable> extends IHasBasicTexture<T> {

	@Override
	T getTexture();

}
