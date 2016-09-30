package gameEngine.renderers;


import org.lwjgl.util.vector.Matrix4f;

import gameEngine.shaders.EnvironmentalShader;
import visibles.environmentals.EnvironmentalTexturedModel;


/**
 * @author storm
 *
 */
class EnvironmentalRenderer extends LightAffectedEntityRendererWithMap<EnvironmentalShader, EnvironmentalTexturedModel> {

	/**
	 * @param shader
	 * @param projectionMatrix
	 */
	EnvironmentalRenderer(EnvironmentalShader shader, Matrix4f projectionMatrix) {
		super(shader, projectionMatrix);
	}

}
