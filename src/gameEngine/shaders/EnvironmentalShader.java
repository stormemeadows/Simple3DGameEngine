package gameEngine.shaders;


import static settings.GameConstants.EnvironmentalParameters.FRAGMENT_FILE;
import static settings.GameConstants.EnvironmentalParameters.VERTEX_FILE;

/**
 * @author storm
 *
 */
public class EnvironmentalShader extends LightAffectedShader {

	public EnvironmentalShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

}
