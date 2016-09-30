package core.ifaces.gameEngine.renderers;


import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import core.utils.Camera;
import visibles.lights.Light;

/**
 * @author storm
 *
 */
public interface IRendererWithMap<M extends Map<?, ? extends Collection<?>>> {

    /**
     * @param entities
     * @param lights
     * @param camera
     * @param clipPlane
     * @param colorRGB
     */
    void run(M entities,
            List<Light> lights,
            Camera camera,
            Vector4f clipPlane,
            Vector3f colorRGB);

}
