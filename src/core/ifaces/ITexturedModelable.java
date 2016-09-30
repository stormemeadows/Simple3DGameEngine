package core.ifaces;


/**
 * @author storm
 *
 */
public interface ITexturedModelable<T extends ITexturable, M extends IModelable>
		extends IHasTexture<T>, IHasModel<M> {

	@Override
	M getModel();


	@Override
	T getTexture();


}
