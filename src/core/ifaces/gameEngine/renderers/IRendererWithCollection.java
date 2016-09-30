package core.ifaces.gameEngine.renderers;


import java.util.Collection;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import core.utils.Camera;
import visibles.lights.Light;

/**
 * @author storm
 *
 */
public interface IRendererWithCollection<C extends Collection<?>> {

    /**
     * @param entities
     * @param lights
     * @param camera
     * @param clipPlane
     * @param colorRGB
     */
    void run(C entities,
            List<Light> lights,
            Camera camera,
            Vector4f clipPlane,
            Vector3f colorRGB);
}
