package gameEngine.renderers;


import org.lwjgl.util.vector.Matrix4f;

import gameEngine.shaders.PlayerShader;
import visibles.players.PlayerTexturedModel;


/**
 * @author storm
 *
 */
class PlayerRenderer extends LightAffectedEntityRendererWithMap<PlayerShader, PlayerTexturedModel> {

	/**
	 * @param shader
	 * @param projectionMatrix
	 */
	PlayerRenderer(PlayerShader shader, Matrix4f projectionMatrix) {
		super(shader, projectionMatrix);
	}


}
