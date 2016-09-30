package core.ifaces.gameEngine.renderers;


import org.lwjgl.util.vector.Matrix4f;

import core.ifaces.gameEngine.shaders.ILoadsTransformationMatrix;

/**
 * @author storm
 *
 */
public interface IShaderLoadsTransformationMatrix<S extends ILoadsTransformationMatrix> {

    void shaderLoadTransformationMatrix(Matrix4f transformationMatrix);

}
