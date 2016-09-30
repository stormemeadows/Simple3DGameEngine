package gameEngine.renderers;


import core.utils.helpers.H;
import gameEngine.shaders.ShaderProgram;

/**
 * @author storm
 *
 */
abstract class AbstractRenderer<S extends ShaderProgram> {

	protected S shader;


	protected void printNumVertexAttribs() {
		H.pln("\nNum attribs: " + shader.getNumAttribs() + "\n");
	}


	/**
	 * @param shader
	 */
	AbstractRenderer(S shader) {
		this.shader = shader;
	}


	void cleanUp() {
		shader.cleanUp();
	}


	final void startShader() {
		shader.start();
	}


	final void stopShader() {
		shader.stop();
	}


	// final int getNumVertexAttribs() {
	// return shader.getNumAttribs();
	// }


}
