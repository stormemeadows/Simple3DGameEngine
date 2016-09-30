package core.ifaces.gameEngine.renderers;


import java.util.List;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import core.ifaces.ILightAffectedTexturedModelable;
import core.utils.Camera;
import visibles.lights.Light;

/**
 * @author storm
 */
public interface ILightAffectedTexturedModelRendererWithList<TM extends ILightAffectedTexturedModelable<?, ?>>
        extends IRendererWithCollection<List<TM>> {


    /**
     * @param texdModels
     * @param lights
     * @param camera
     * @param clipPlane
     * @param colorRGB
     */
    @Override
    void run(List<TM> texdModels,
            List<Light> lights,
            Camera camera,
            Vector4f clipPlane,
            Vector3f colorRGB);

}
