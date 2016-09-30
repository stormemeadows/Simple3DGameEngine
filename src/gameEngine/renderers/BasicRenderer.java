package gameEngine.renderers;


import org.lwjgl.opengl.GL13;

import core.ifaces.IBasicTexturable;
import core.ifaces.IModelable;
import gameEngine.renderers.delegates.VAOHandler;
import gameEngine.shaders.ShaderProgram;
import textures.utils.TexHandler;

/**
 * @author storm
 *
 */
abstract class BasicRenderer<S extends ShaderProgram>
        extends AbstractRenderer<S> {

    protected final VAOHandler vaoDelegate = new VAOHandler();
    protected final TexHandler texDelegate = new TexHandler();


    BasicRenderer(S shader) {
        super(shader);
    }


    // abstract protected void preDrawModel(IModelable model);
    // abstract protected void postDrawModel(IModelable model);
    abstract protected void drawModel(IModelable model);


    protected void bindModelData(IModelable model) {
        bindVAOAndEnableVertexAttribArrays(model.getVaoID());
    }


    protected void unbindModelData() {
        unbindVAOAndDisableVertexAttribArrays();
    }


    /******************************************************************************
     ********************************** Textures **********************************
     ******************************************************************************/

    /**
     * @param texture
     *            The texture to bind.
     * @param glTexLoc
     *            The texture location to bind the texture.
     */
    protected <T extends IBasicTexturable> void activateAndBind2DTexture(int glTexLoc, T texture) {
        // StaticTexHandler
        texDelegate.activateAndBind2DTexture(glTexLoc, texture);
    }


    final protected <T extends IBasicTexturable> void activateAndBind2DTexture(T texture) {
        activateAndBind2DTexture(GL13.GL_TEXTURE0, texture);
    }


    /**
     * @param glStartTexLoc
     *            The texture location to start from.
     * @param textures
     *            The textures to bind.
     */
    protected <T extends IBasicTexturable> void activateAndBind2DTextures(int glStartTexLoc, T[] textures) {
        // StaticTexHandler
        texDelegate.activateAndBind2DTextures(glStartTexLoc, textures);
    }


    final protected <T extends IBasicTexturable> void activateAndBind2DTextures(T[] textures) {
        activateAndBind2DTextures(GL13.GL_TEXTURE0, textures);
    }


    /******************************************************************************
     ************************************ VAOs ************************************
     ******************************************************************************/


    /**
     * Binds the VAO, then enables the attrib arrays in the VAO.
     */
    final protected void bindVAOAndEnableVertexAttribArrays(int vertexArrayID) {
        // StaticVAOHandler
        vaoDelegate.bindVAOAndEnableVertexAttribArrays(vertexArrayID, shader.getNumAttribs());
    }


    /**
     * Disables the attrib arrays in the VAO, then unbinds the VAO.
     */
    final protected void unbindVAOAndDisableVertexAttribArrays() {
        // StaticVAOHandler
        vaoDelegate.unbindVAOAndDisableVertexAttribArrays(shader.getNumAttribs());
    }


}
