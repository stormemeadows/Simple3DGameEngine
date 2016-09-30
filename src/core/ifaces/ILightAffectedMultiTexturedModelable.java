package core.ifaces;


/**
 * @author storm
 *
 */
public interface ILightAffectedMultiTexturedModelable<T extends ILightAffectedTexturable, M extends IModelable>
		extends IMultiTexturedModelable<T, M> {

	@Override
	M getModel();


	@Override
	T[] getAllTextures();

}
