package core.ifaces.gameEngine.renderers;


import org.lwjgl.util.vector.Matrix4f;

/**
 * @author storm
 */
public interface IHasModelMatrix {

	// void createModelMatrix();

	Matrix4f getModelMatrix();

}
