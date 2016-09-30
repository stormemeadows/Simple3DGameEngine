package core.ifaces.gameEngine.renderers;


import java.util.List;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import core.ifaces.gameEngine.shaders.ILoadsCamera;
import core.ifaces.gameEngine.shaders.ILoadsClippingPlane;
import core.ifaces.gameEngine.shaders.ILoadsLightSourceData;
import core.ifaces.gameEngine.shaders.ILoadsSkyData;
import core.utils.Camera;
import visibles.lights.Light;

/**
 * @author storm
 *
 */
public interface IShaderLoadsSceneData<S extends ILoadsCamera & ILoadsClippingPlane & ILoadsLightSourceData & ILoadsSkyData> {

    void shaderLoadSceneData(List<Light> lights,
            Camera camera, Vector4f clipPlane, Vector3f colorRGB);

}
