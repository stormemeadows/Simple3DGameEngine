package core.ifaces.gameEngine.renderers;


import core.ifaces.ILightAffectedTexturable;
import core.ifaces.gameEngine.shaders.ILoadsLightAffectedTextureData;

/**
 * @author storm
 *
 */
public interface IShaderLoadsLightAffectedTextureData<S extends ILoadsLightAffectedTextureData> {
    // extends IShaderLoadsTextureData<S> {


    <T extends ILightAffectedTexturable> void shaderLoadTexData(T tex);


    // <T extends ILightAffectedTexturable> void
    // shaderLoadLightAffectedTexData(T tex);
    // void shaderLoadFakeLightingVariable(boolean useFake);
    // void shaderLoadShineVariables(float damper, float reflectivity);
    // void shaderLoadShineDamper(float damper);
    // void shaderLoadReflectivity(float reflectivity);
}
