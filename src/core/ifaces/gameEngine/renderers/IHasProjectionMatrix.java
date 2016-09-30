package core.ifaces.gameEngine.renderers;


import org.lwjgl.util.vector.Matrix4f;

/**
 * @author storm
 */
public interface IHasProjectionMatrix {

	// void createProjectionMatrix();

	Matrix4f getProjectionMatrix();

}
