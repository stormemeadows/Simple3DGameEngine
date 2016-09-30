package core.ifaces.gameEngine.shaders;


import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import visibles.lights.Light;

/**
 * @author storm
 *
 */
public interface ILoadsLightSourceData {

	void loadLights(List<Light> lights);


	void loadLight(int idx, Light light);


	void loadLightPosition(int idx, Vector3f position);


	void loadLightColor(int idx, Vector3f color);


	void loadAttenuation(int idx, Vector3f attenuationFactors);

}
