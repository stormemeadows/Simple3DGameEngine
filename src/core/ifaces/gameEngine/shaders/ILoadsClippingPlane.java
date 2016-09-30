package core.ifaces.gameEngine.shaders;


import org.lwjgl.util.vector.Vector4f;

/**
 * @author storm
 *
 */
public interface ILoadsClippingPlane {

	void loadClipPlane(Vector4f plane);
}
