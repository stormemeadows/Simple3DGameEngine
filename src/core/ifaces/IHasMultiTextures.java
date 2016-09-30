package core.ifaces;


/**
 * @author storm
 *
 */
public interface IHasMultiTextures<T extends IBasicTexturable> {

	T[] getAllTextures();

}
