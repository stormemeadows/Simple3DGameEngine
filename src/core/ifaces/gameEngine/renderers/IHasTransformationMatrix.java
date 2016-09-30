package core.ifaces.gameEngine.renderers;


import org.lwjgl.util.vector.Matrix4f;

/**
 * @author storm
 *
 */
public interface IHasTransformationMatrix {

	// void createTransformationMatrix();

	Matrix4f getTransformationMatrix();
}
