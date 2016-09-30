package core.ifaces.gameEngine.renderers;


import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import core.ifaces.ILightAffectedTexturedModelable;
import core.utils.Camera;
import entities.Entity;
import visibles.lights.Light;

/**
 * @author storm
 */
// public interface ILightAffectedEntityRendererWithMap<TM extends
// ILightAffectedTexturedModelable<?, ?>> {
public interface ILightAffectedEntityRendererWithMap<TM extends ILightAffectedTexturedModelable<?, ?>>
        extends IRendererWithMap<Map<TM, List<Entity<TM>>>> {

    /**
     * @param entities
     * @param lights
     * @param camera
     * @param clipPlane
     * @param colorRGB
     */
    @Override
    void run(Map<TM, List<Entity<TM>>> entities,
            List<Light> lights,
            Camera camera,
            Vector4f clipPlane,
            Vector3f colorRGB);

}
