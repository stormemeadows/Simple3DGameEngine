package gameEngine.renderers;


import static settings.GameConstants.GuiParameters.FULL_SCREEN_2D_VERTS;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import core.ifaces.IModelable;
import core.utils.Maths;
import gameEngine.loaders.MasterLoader;
import gameEngine.shaders.GuiShader;
import models.RawModel;
import visibles.guis.GuiElement;

/**
 * @author storm
 * 
 * @desc Order in which guis are rendered determines overlapping/clipping
 *       behavior.
 */
class GuiRenderer
        extends BasicRenderer<GuiShader> {

    private final RawModel screenRect;


    GuiRenderer(GuiShader shader) {
        super(shader);
        // printNumVertexAttribs();
        screenRect = MasterLoader.store2DPositionsDataInVAOAndReturnRawModel(
                FULL_SCREEN_2D_VERTS);
    }


    /**
     * Takes a list of all of the gui textures we'd like to render.
     * 
     * @param guis
     *            Order in which guis are rendered determines
     *            overlapping/clipping behavior.
     */
    void run(List<GuiElement> guis) {
        startShader();
        renderGuis(guis);
        stopShader();
    }


    /**
     * // bind vao ie the screenRect model (in order to render onto it)
     * /// for each vert attrib array
     * //// enable the vert attrib array
     * // enable alpha blending
     * // disable depth testing
     * /// for each texture:
     * //// mark the texture as active
     * //// bind the texture
     * //// load the transformation matrix to the shader
     * //// draw the texture
     * // reenable depth testing
     * // redisable alpha blending
     * /// for each bound vert attrib array
     * //// disable the bound vert attrib array
     * // unbind the vao
     */
    final protected void renderGuis(List<GuiElement> guis) {
        bindModelData(screenRect);
        preDraw();
        for (final GuiElement gui:guis) {
            activateAndBind2DTexture(gui);
            shaderLoadTransformationMatrix(gui);
            drawModel(screenRect);
        }
        postDraw();
        unbindModelData();
    }


    private void shaderLoadTransformationMatrix(GuiElement gui) {
        shaderLoadModelMatrix(gui);
    }


    private void shaderLoadModelMatrix(GuiElement gui) {
        shader.loadTransformationMatrix(createModelMatrix(gui));
    }


    private Matrix4f createModelMatrix(GuiElement gui) {
        return Maths.createTransformationMatrix(gui.getPosition(), gui.getScale());
    }


    @Override
    protected void drawModel(IModelable model) {
        GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, model.getVertexCount());
    }


    protected void preDraw() {
        MasterRenderer.enableAlphaBlending();
        MasterRenderer.disableDepthTesting();
    }


    protected void postDraw() {
        MasterRenderer.enableDepthTesting();
        MasterRenderer.disableAlphaBlending();
    }


}

// final protected void renderGuis(List<GuiElement> guis) {
// // bind vao
// GL30.glBindVertexArray(screenRect.getVaoID());
//
// // for each vert attrib array:
// // for (int i1 = 0; i1 < shader.getNumAttribs(); i1++) {
//
//
// // for (int i1 = 0; i1 < H.plnAndReturnLast(shader.getNumAttribs());
// // i1++) {
// for (int i1 = 0; i1 < shader.getNumAttribs(); i1++) {
// // enable the vert attrib array
// // GL20.glEnableVertexAttribArray(H.plnAndReturnLast(i1));
// GL20.glEnableVertexAttribArray(i1);
// }
//
//
// // enable alpha blending
// GL11.glEnable(GL11.GL_BLEND);
// GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//
// // disable depth testing
// GL11.glDisable(GL11.GL_DEPTH_TEST);
//
//
// // for each texture:
// for (final GuiElement gui:guis) {
// // System.out.println("\n\t*** BINDING GUI: " + gui + " ***\n");
//
// // mark the texture as active
// GL13.glActiveTexture(GL13.GL_TEXTURE0);
// // bind the texture
// GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getTextureID());
//
// // load the transformation matrix to the shader
// shader.loadTransformationMatrix(Maths.createTransformationMatrix(gui.getPosition(),
// gui.getScale()));
//
// // draw the texture
// GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0,
// screenRect.getVertexCount());
// }
//
//
// // reenable depth testing
// GL11.glEnable(GL11.GL_DEPTH_TEST);
//
// // redisable alpha blending
// GL11.glDisable(GL11.GL_BLEND);
//
//
// // for each bound vert attrib array:
// for (int i = 0; i < shader.getNumAttribs(); i++) {
// // disable the bound vert attrib array
// GL20.glDisableVertexAttribArray(i);
// }
//
//
// // unbind the vao
// GL30.glBindVertexArray(0);
// }
