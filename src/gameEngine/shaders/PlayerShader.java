package gameEngine.shaders;


import static settings.GameConstants.EnvironmentalParameters.FRAGMENT_FILE;
import static settings.GameConstants.EnvironmentalParameters.VERTEX_FILE;

/**
 * @author storm
 *
 */
public class PlayerShader extends LightAffectedShader {

	public PlayerShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

}
