package core.ifaces.gameEngine.shaders;


import org.lwjgl.util.vector.Matrix4f;

/**
 * @author storm
 *
 */
public interface ILoadsModelMatrix {

	void loadModelMatrix(Matrix4f viewMatrix);

}
