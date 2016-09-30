package gameEngine.shaders;


import static settings.GameConstants.GuiParameters.FRAGMENT_FILE;
import static settings.GameConstants.GuiParameters.VERTEX_FILE;
import static settings.GameConstants.OpenGLVAOParameters.POSITION_IDX;
import static settings.GameConstants.OpenGLVAOParameters.POSITION_NAME;

import org.lwjgl.util.vector.Matrix4f;

import core.ifaces.gameEngine.shaders.ILoadsTransformationMatrix;

/**
 * @author storm
 *
 */
public class GuiShader extends ShaderProgram
		implements ILoadsTransformationMatrix {


	private int location_transformationMatrix;


	public GuiShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		numAttribs += 1;
	}


	@Override
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix4f(location_transformationMatrix, matrix);
	}


	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = getUniformLocation("transformationMatrix");
	}


	@Override
	protected void bindAttribLocations() {
		bindAttribLocation(POSITION_IDX, POSITION_NAME);
	}

}
