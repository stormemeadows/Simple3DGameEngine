package core.ifaces.gameEngine.shaders;


import org.lwjgl.util.vector.Vector3f;

/**
 * @author storm
 *
 */
public interface ILoadsSkyData {

	void loadSkyColor(Vector3f colorRGB);


	void loadSkyColor(float r, float g, float b);
}
