package core.ifaces;


/**
 * @author storm
 *
 */
public interface ILightAffectedTexturedModelable<T extends ILightAffectedTexturable, M extends IModelable>
		extends ITexturedModelable<T, M> {

	@Override
	M getModel();


	@Override
	T getTexture();


}
