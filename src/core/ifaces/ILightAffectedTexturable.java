package core.ifaces;


/**
 * @author storm
 *
 */
public interface ILightAffectedTexturable extends ITexturable {

	@Override
	int getVboID();


	@Override
	int getTextureID();


	float getShineDamper();


	float getReflectivity();


	boolean isUseFakeLighting();

}
