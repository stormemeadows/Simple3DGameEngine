package gameEngine.renderers;


import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import core.ifaces.ILightAffectedTexturable;
import core.ifaces.IModelable;
import core.ifaces.ITerrainable;
import core.utils.Maths;
import gameEngine.shaders.TerrainShader;

/**
 * @author storm
 *
 */
class TerrainRenderer
        extends LightAffectedTexturedModelRendererWithList<TerrainShader, ITerrainable<?, ?>> {

    /**
     * @param shader
     * @param projectionMatrix
     */
    TerrainRenderer(final TerrainShader shader, final Matrix4f projectionMatrix) {
        super(shader, projectionMatrix);
    }


    @Override
    protected void initializationLogicBetweenStartingAndStoppingShader(final Matrix4f projectionMatrix) {
        super.initializationLogicBetweenStartingAndStoppingShader(projectionMatrix);
        shader.connectTextureUnits();
    }


    @Override
    protected void renderTexdModels(List<ITerrainable<?, ?>> texturedModels) {
        for (final ITerrainable<?, ?> texturedModel:texturedModels)
            renderTexdModel(texturedModel);
    }


    /**
     * For a TexturedModel:
     * 1. Bind and prep model.
     * 2. Mark active textures and bind them.
     * 3. Load relevant texture data.
     * 4. Load transformation matrix (using data fields of TexturedModel).
     * 5. Draw the model.
     * 6. Unbind model and reverse any changes made to prepare it.
     */
    // @Override
    protected void renderTexdModel(ITerrainable<?, ?> texturedModel) {

        final IModelable model = texturedModel.getModel();
        final ILightAffectedTexturable texture = texturedModel.getTexture();

        bindModelData(model); // 1
        preDraw(texture);

        activateAndBind2DTextures(texturedModel.getAllTextures()); // 2
        // shaderLoadAllTexData(texturedModel.getTexture()); // 3
        shader.loadShineVariables(1, 0); // 3

        shaderLoadTransformationMatrixFromTexdModel(texturedModel); // 4

        drawModel(model); // 5

        postDraw();
        unbindModelData(); // 6
    }


    private void shaderLoadTransformationMatrixFromTexdModel(ITerrainable<?, ?> texturedModel) {
        shaderLoadTransformationMatrix(createTransformationMatrixFromTexdModel(texturedModel));
    }


    private Matrix4f createTransformationMatrixFromTexdModel(ITerrainable<?, ?> texdModel) {
        final Matrix4f matrix = Maths.createTranslationTransformationMatrix(
                new Vector3f(texdModel.getXCoordInWorldGrid(), 0, texdModel.getZCoordInWorldGrid()));
        return matrix;
    }


}
