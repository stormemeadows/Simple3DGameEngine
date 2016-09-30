package gameEngine.renderers;


import org.lwjgl.util.vector.Matrix4f;

import gameEngine.shaders.BasicShaderWithProjectionMatrix;

/**
 * @author storm
 *
 */
abstract class BasicRendererWithProjectionMatrix<S extends BasicShaderWithProjectionMatrix>
        extends BasicRenderer<S> {

    /**
     * @param shader
     * @param projectionMatrix
     */
    BasicRendererWithProjectionMatrix(final S shader, final Matrix4f projectionMatrix) {
        super(shader);

        startShader();
        initializationLogicBetweenStartingAndStoppingShader(projectionMatrix);
        stopShader();
    }


    protected void initializationLogicBetweenStartingAndStoppingShader(Matrix4f projectionMatrix) {
        shader.loadProjectionMatrix(projectionMatrix);
    }


}
