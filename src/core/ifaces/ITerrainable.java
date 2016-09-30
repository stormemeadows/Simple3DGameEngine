package core.ifaces;

/**
 * @author storm
 *
 */
// public interface ITerrainable<T extends IBasicTexturable, M extends
// IModelable>
// extends ITexturedModelable<IBasicTexturable, IModelable> {
public interface ITerrainable<T extends ILightAffectedTexturable, M extends IModelable>
		extends ILightAffectedTexturedModelable<T, M> {

	@Override
	M getModel();


	@Override
	T getTexture();


	// @Override
	T[] getAllTextures();


	float getHeightOfTerrain(float worldX, float worldZ);


	float getXCoordInWorldGrid();


	float getZCoordInWorldGrid();

}
