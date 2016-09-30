package gameEngine.renderers;


import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import core.ifaces.ILightAffectedTexturedModelable;
import core.ifaces.gameEngine.renderers.ILightAffectedTexturedModelRendererWithList;
import core.utils.Camera;
import gameEngine.shaders.LightAffectedShader;
import visibles.lights.Light;

/**
 * @author storm
 *
 */
abstract class LightAffectedTexturedModelRendererWithList<S extends LightAffectedShader, TM extends ILightAffectedTexturedModelable<?, ?>>
        extends LightAffectedRenderer<S, TM>
        implements ILightAffectedTexturedModelRendererWithList<TM> {


    /**
     * @param shaderProgram
     * @param projectionMatrix
     */
    LightAffectedTexturedModelRendererWithList(S shader, Matrix4f projectionMatrix) {
        super(shader, projectionMatrix);
    }


    @Override
    public void run(List<TM> texdModels, List<Light> lights,
            Camera camera, Vector4f clipPlane, Vector3f colorRGB) {
        startShader();

        shaderLoadSceneData(lights, camera, clipPlane, colorRGB);

        renderTexdModels(texdModels);

        stopShader();
    }


    abstract protected void renderTexdModels(List<TM> texturedModels);
    // final protected void renderTexdModels(List<TM> texturedModels) {
    // for (final TM texturedModel:texturedModels)
    // renderTexdModel(texturedModel);
    // }
    // abstract protected void renderTexdModel(TM texturedModel);

}
