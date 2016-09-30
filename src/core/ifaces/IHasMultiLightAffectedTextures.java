package core.ifaces;


/**
 * @author storm
 *
 */
public interface IHasMultiLightAffectedTextures<T extends ILightAffectedTexturable>
		extends IHasMultiTextures<T> {

	@Override
	T[] getAllTextures();


}
