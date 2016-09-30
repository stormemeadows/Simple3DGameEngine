package core.drivers;


import org.lwjgl.opengl.GL20;

/**
 * @author storm
 *
 */
public class GLDriver {
	public static final void loadBoolean(int location, boolean value) {
		float toLoad = 0;
		if (value) {
			toLoad = 1;
		}
		GL20.glUniform1f(location, toLoad);
	}
}

