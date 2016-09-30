package core.ifaces;


/**
 * @author storm
 *
 */
public interface IMultiTexturedModelable<T extends IBasicTexturable, M extends IModelable>
		extends IHasMultiTextures<T>, IHasModel<M> {

	@Override
	M getModel();


	@Override
	T[] getAllTextures();

}
