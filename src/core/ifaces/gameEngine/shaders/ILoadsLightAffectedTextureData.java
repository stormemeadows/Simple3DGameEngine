package core.ifaces.gameEngine.shaders;


/**
 * @author storm
 *
 */
public interface ILoadsLightAffectedTextureData extends ILoadsTextureData {

	void loadFakeLightingVariable(boolean useFake);


	void loadShineVariables(float damper, float reflectivity);


	void loadShineDamper(float damper);


	void loadReflectivity(float reflectivity);
}
