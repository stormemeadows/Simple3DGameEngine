package gameEngine.shaders;


import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import core.drivers.GLDriver;
import gameEngine.loaders.MasterLoader;


/**
 * @author storm
 * 
 * @desc Shader programs:
 * @desc Initialization:
 *       1. Load vertex and fragment shader files (written in GLSL).
 *       2. Create a shader program, ie an instance of (a subclass of) this
 *       class.
 *       2. Attach gameEngine.shaders to program, using the OpenGL bindings
 *       provided by
 *       LWJGL.
 *       4. Bind the shader program's attributes:
 *       Each attribute can be bound to 1 of the 16 locations in the VAO,
 *       which are accessed using indices 0..15.
 *       These attributes are made available to the vertex gameEngine.shaders
 *       via
 *       the "in" keyword preceeding the token by the same name; eg, in a
 *       shader file: 'in float foo'
 *       5. Link the program.
 *       6. Validate the program.
 *       7. Get the locations of all uniform vars, ie read-only vars used in
 *       shader files. These vars are what may be manipulated by our Java source
 *       code. After acting on these data, their values MUST be loaded to the
 *       graphics engine via the 'glUniform' functions.
 * 
 * @desc Start:
 *       Tell graphics engine to use this program, specifically the program with
 *       this id.
 * 
 * @desc Stop:
 *       Tell graphics engine to use no program; specifically, to start using
 *       the "program" with id 0.
 *
 * @desc Clean up:
 *       Stop.
 *       Detatch all gameEngine.shaders from this program.
 *       Delete all gameEngine.shaders that were attached to this program.
 *       Delete this program.
 * 
 * @desc new ShaderProgram().loadFoo(int theLocationOfFoo, FooType
 *       theValueOfFoo)
 *       Just a wrapper to invoke:
 *       GL20.glUniformFooType(int theLocationOfFoo, FooType theValueOfFoo)
 *       These functions allow us to upload the variables' vals to the graphics
 *       engine; ie, give the shader code access to the updated/changed states
 *       of the variables.
 *
 */
public abstract class ShaderProgram {

	// Will be using 4x4 matrices, hence 16 floats.
	// private static FloatBuffer MATRIX_BUFFER =
	// BufferUtils.createFloatBuffer(16);
	private final FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

	private final int	vertexShaderID;
	private final int	fragmentShaderID;
	private final int	programID;

	protected int numAttribs = 0;


	/**
	 * Takes the names of the associated vertex and fragment shader files.
	 * 
	 * 1. Loads vertex and fragment shader files (written in GLSL).
	 * 
	 * 2. Tells OpenGL to create a new shader program.
	 * 
	 * 3. Attaches gameEngine.shaders to program, using the bindings provided by
	 * LWJGL/OpenGL.
	 * 
	 * 4. Bind the shader program's attributes to its VertoxArrayObjects (VAOs):
	 * Each attribute can be bound to 1 of the 16 locations in the VAO,
	 * which are accessed using indices 0..15.
	 * These attributes are made available to the vertex gameEngine.shaders via
	 * the "in" keyword preceeding the token by the same name; eg, in a
	 * shader file: 'in float foo'. @see bindAttirbutes()
	 * 
	 * 5. Link the program, using bindings provided by LWJGL/OpenGL.
	 * 
	 * 6. Validate the program, again using bindings provided by LWJGL/OpenGL.
	 * 
	 * 7. Get the locations of all uniform vars, ie the read-only vars used in
	 * shader source files. These vars are what we manipulate in our Java
	 * source code. After acting on these data, their values MUST be loaded to
	 * the graphics engine via the 'glUniform' functions.
	 * 
	 * @param vertexFile
	 * @param fragmentFile
	 */
	public ShaderProgram(String vertexFile, String fragmentFile) {
		// ShaderFileLoader shaderFileLoader = new ShaderFileLoader();

		vertexShaderID = MasterLoader.loadShaderFileAndReturnShaderID(vertexFile, GL_VERTEX_SHADER);
		fragmentShaderID = MasterLoader.loadShaderFileAndReturnShaderID(fragmentFile, GL_FRAGMENT_SHADER);

		programID = glCreateProgram();

		glAttachShader(programID, vertexShaderID);
		glAttachShader(programID, fragmentShaderID);

		// must bind the attributes before the shader program is linked
		bindAttribLocations();

		glLinkProgram(programID);
		glValidateProgram(programID);

		getAllUniformLocations();

		// numAttribs = 0;
	}


	final protected void getUniformLocOfArray(int[] array, String name, int idx) {
		array[idx] = getUniformLocation(name + "[" + idx + "]");
	}


	/**
	 * Bind the shader program's attributes.
	 * 
	 * Each attribute can be bound to 1 of the 16 locations in the VAO,
	 * which are accessed using indices 0..15.
	 * These attributes are made available to the vertex gameEngine.shaders
	 * source files
	 * via the "in" keyword, which preceeds the token by the same name in that
	 * source file.
	 * Eg, if in a ShaderProgram we have;
	 * 
	 * <pre>
	 * // FooShaderProgram.java
	 * bindAttribute(0, "foo");
	 * bindAttribute(1, "bar");
	 * </pre>
	 * 
	 * then in the associated vertex source file we'll have access to the
	 * variables 'foo' and 'bar' (which are not Strings in the source file).
	 * The variables' types are declared in the shader source.
	 * Eg, in the associated vertex shader source file, could have:
	 * 
	 * <pre>
	 * // fooShaderVertexShader.txt
	 * in float foo;
	 * in vec4 bar;
	 * </pre>
	 * 
	 * 
	 * When overriding,first call
	 * 
	 * <pre>
	 * super.bindAttributes(),
	 * </pre>
	 * 
	 * then bind any new attributes introduced in the subclass using
	 * 'bindAttribute(int,String)' (which cannot be overwritten).
	 * 
	 */
	protected abstract void bindAttribLocations();


	protected abstract void getAllUniformLocations();


	final public int getNumAttribs() {
		return numAttribs;
	}


	/**
	 * Get the location of the uniform variable used in the associate source
	 * files.
	 * 
	 * @param uniformName
	 *            The name of the uniform variable.
	 *            Spelling must match that of the uniform variable token within
	 *            the source file(s).
	 */
	final protected int getUniformLocation(String uniformName) {
		return glGetUniformLocation(programID, uniformName);
	}


	/**
	 * Bind an attribute to this program.
	 * 
	 * @param IdxOfAttribInVAO
	 * @param NameOfAttribVar
	 */
	final protected void bindAttribLocation(int IdxOfAttribInVAO, String NameOfAttribVar) {
		glBindAttribLocation(programID, IdxOfAttribInVAO, NameOfAttribVar);
	}


	/**
	 * Tell OpenGL to use this program.
	 */
	final public void start() {
		glUseProgram(programID);
	}


	/**
	 * Unbind the program by telling OpenGL to use the program with id 0,
	 * ie, to use no program at all.
	 */
	final public void stop() {
		glUseProgram(0);
	}


	/**
	 * Stop, ie tell graphics engine to use no program;
	 * specifically, to start using the "program" with id 0.
	 * 
	 * Detatch all gameEngine.shaders from this program.
	 * 
	 * Delete all gameEngine.shaders that were attached to this program.
	 * 
	 * Delete this program.
	 */
	final public void cleanUp() {

		// unbind the program
		stop();

		// detach gameEngine.shaders
		glDetachShader(programID, vertexShaderID);
		glDetachShader(programID, fragmentShaderID);

		// delete gameEngine.shaders
		glDeleteShader(vertexShaderID);
		glDeleteShader(fragmentShaderID);

		// delete program
		glDeleteProgram(programID);
	}


	/***************************************************************************
	 ********* Simple wrappers for LWJGL/OpenGL 'glUniform' functions **********
	 ***************************************************************************/

	final protected void load1i(int location, int value) {
		glUniform1i(location, value);
	}


	final protected void load1f(int location, float value) {
		glUniform1f(location, value);
	}


	final protected void load2f(int location, float x, float y) {
		glUniform2f(location, x, y);
	}


	final protected void load3f(int location, float x, float y, float z) {
		glUniform3f(location, x, y, z);
	}


	final protected void load4f(int location, float x, float y, float z, float w) {
		glUniform4f(location, x, y, z, w);
	}


	final protected void loadVector2f(int location, Vector2f v) {
		load2f(location, v.x, v.y);
	}


	final protected void loadVector3f(int location, Vector3f v) {
		load3f(location, v.x, v.y, v.z);
	}


	final protected void loadVector4f(int location, Vector4f v) {
		load4f(location, v.x, v.y, v.z, v.w);
	}


	/**
	 * Loads 1.0f if the value is true, and 0.0f if its false.
	 * Eg, use in the shader source:
	 * 
	 * <pre>
	 * if (useFakeLighting > 0.5) { // point normal upwards for fake lighting
	 * 	actualNormal = vec3(0.0, 1.0, 0.0);
	 * }
	 * 
	 * </pre>
	 * 
	 * 
	 * @param location
	 * @param value
	 */
	final protected void loadBoolean(int location, boolean value) {
		// float toLoad = 0f;
		// if (value) {
		// toLoad = 1f;
		// }
		// load1f(location, toLoad);
		final float toLoad = (value ? 0f : 1f);
		load1f(location, toLoad);
	}


	final protected void loadBoolean2(int location, boolean value) {
		GLDriver.loadBoolean(location, value);
	}


	final protected void loadMatrix4f(int location, Matrix4f matrix) {
		// matrix.store(MATRIX_BUFFER);
		// MATRIX_BUFFER.flip();
		// glUniformMatrix4(location, false, MATRIX_BUFFER);
		matrix.store(matrixBuffer);
		matrixBuffer.flip();
		glUniformMatrix4(location, false, matrixBuffer);
	}


}
