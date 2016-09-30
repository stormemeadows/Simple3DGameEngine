package gameEngine.renderers;


import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import core.ifaces.ILightAffectedTexturable;
import core.ifaces.ILightAffectedTexturedModelable;
import core.ifaces.IModelable;
import core.ifaces.gameEngine.renderers.ILightAffectedEntityRendererWithMap;
import core.utils.Camera;
import core.utils.Maths;
import entities.Entity;
import gameEngine.shaders.LightAffectedShader;
import visibles.lights.Light;

/**
 * @author storm
 *         Entity<TM extends
 *         ILightAffectedTexturedModelable<LightAffectedTexture, ?>>
 */
abstract class LightAffectedEntityRendererWithMap<S extends LightAffectedShader, TM extends ILightAffectedTexturedModelable<?, ?>>
        extends LightAffectedRenderer<S, TM>
        implements ILightAffectedEntityRendererWithMap<TM> {

    /**
     * @param shdr
     * @param projectionMatrix
     */
    LightAffectedEntityRendererWithMap(S shader, Matrix4f projectionMatrix) {
        super(shader, projectionMatrix);
    }


    @Override
    public void run(Map<TM, List<Entity<TM>>> texdModelToEntityMap, List<Light> lights,
            Camera camera, Vector4f clipPlane, Vector3f colorRGB) {
        startShader();
        shaderLoadSceneData(lights, camera, clipPlane, colorRGB);
        renderEntities(texdModelToEntityMap);
        stopShader();
    }


    /**
     * For each Entity:
     * 1. Bind and prep model.
     * 2. Mark active textures and bind them.
     * 3. Load relevant texture data.
     * 4. Load transformation matrix (using data fields of Entity).
     * 5. Draw the model.
     * 6. Unbind model and reverse any changes made to prepare it.
     */
    protected void renderEntities(Map<TM, List<Entity<TM>>> map) {
        // for (final TM texdModel:map.keySet())
        // renderEntitiesAssociatedWithTexdModel(map.get(texdModel), texdModel);

        for (final TM texdModel:map.keySet()) {
            final IModelable model = texdModel.getModel();
            final ILightAffectedTexturable tex = texdModel.getTexture();

            bindModelData(model); // 1
            preDraw(tex);

            activateAndBind2DTexture(tex); // 2
            shaderLoadTexData(tex); // 3
            for (final Entity<TM> entityWithTexdModel:map.get(texdModel)) {
                shaderLoadTransformationMatrixFromEntity(entityWithTexdModel); // 4
                drawModel(model); // 5
            }

            postDraw();
            unbindModelData(); // 6
        }

    }


    private void shaderLoadTransformationMatrixFromEntity(Entity<TM> entity) {
        shaderLoadTransformationMatrix(createTransformationMatrixFromEntity(entity));
    }


    private Matrix4f createTransformationMatrixFromEntity(Entity<TM> entity) {
        final Matrix4f matrix = Maths.createTransformationMatrix(
                entity.getPosition(),
                entity.getRotX(), entity.getRotY(), entity.getRotZ(),
                entity.getScale());
        return matrix;
    }


}
