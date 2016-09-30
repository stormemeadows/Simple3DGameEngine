package core.ifaces.gameEngine.renderers;


import core.ifaces.ITexturable;
import core.ifaces.gameEngine.shaders.ILoadsTextureData;

/**
 * @author storm
 *
 */
public interface IShaderLoadsTextureData<S extends ILoadsTextureData> {

    <T extends ITexturable> void shaderLoadTexData(T tex);
    // void shaderLoadOffset(float x, float y);
    // void shaderLoadNumberOfRows(int numberOfRows);

}
