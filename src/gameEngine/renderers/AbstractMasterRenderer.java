package gameEngine.renderers;


import static settings.GameConstants.MasterRendererParameters.BLUE;
import static settings.GameConstants.MasterRendererParameters.FAR_PLANE;
import static settings.GameConstants.MasterRendererParameters.FOV;
import static settings.GameConstants.MasterRendererParameters.GREEN;
import static settings.GameConstants.MasterRendererParameters.NEAR_PLANE;
import static settings.GameConstants.MasterRendererParameters.RED;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import core.display.DisplayManager;
import core.ifaces.ILightAffectedTexturedModelable;
import core.ifaces.gameEngine.renderers.IHasProjectionMatrix;
import core.utils.Maths;
import entities.Entity;

/**
 * @author storm
 *
 */
abstract class AbstractMasterRenderer
        implements IHasProjectionMatrix {

    /**
     * Enhance performance by not rending faces we cannot see.
     */
    final static void enableCulling() {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }


    final static void disableCulling() {
        GL11.glDisable(GL11.GL_CULL_FACE);
    }


    final static void enableClippingDistance() {
        GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
    }


    // doesn't always work!
    final static void disableClippingDistance() {
        GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
    }


    // sets the blending fn too
    final static void enableAlphaBlending() {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }


    final static void disableAlphaBlending() {
        GL11.glDisable(GL11.GL_BLEND);
    }


    final static void enableDepthTesting() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }


    /**
     * To render eg guis behind other guis, in case of
     * transparent parts.
     */
    final static void disableDepthTesting() {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
    }


    /**
     * Call to switch to default frame buffer.
     */
    public static void bindDefaultFrameBuffer() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
        GL11.glViewport(0, 0, DisplayManager.getWidth(), DisplayManager.getHeight());
    }


    /**
     * Defines the clearing color, then
     * clears the display buffer to that color.
     */
    final static void clearDisplayBufferToClearColor() {
        GL11.glClearColor(RED, GREEN, BLUE, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }


    /**
     * Prepares the screen for rendering.
     */
    final static void prepareToRender() {
        enableDepthTesting();
        clearDisplayBufferToClearColor();
    }


    /*****************************************************************
    *****************************************************************/


    final protected Matrix4f projectionMatrix;

    private float fovInDegs;


    /**
     * Creates and sets up projectionMatrix:
     * ________________________________________
     * |.xScale.|...0....|......0.......|..0..|
     * |...0....|.yScale.|......0.......|..0..|
     * |...0....|...0....|....zScale....|.-1..|
     * |...0....|...0....|.projOfWOntoZ.|..0..|
     * ________________________________________
     */
    AbstractMasterRenderer() {
        projectionMatrix = new Matrix4f(); // 4x4 identity matrix
        setFieldOfViewInDegs(FOV);
        updateNearAndFarPlanes(NEAR_PLANE, FAR_PLANE);
    }


    /**
     * Adjusts scales of projectionMatrix's x and y columns.
     * 
     * @param fovInDegs
     */
    final public void setFieldOfViewInDegs(float fovInDegs) {
        this.fovInDegs = fovInDegs;
        updateFieldOfView();
        // final float fovInRads = Maths.toRads(fovInDegs);
        // final float xScale = Maths.cotFromRads(fovInRads / 2);
        // final float yScale = xScale * DisplayManager.getAspectRatio();
        //
        // projectionMatrix.m00 = xScale;
        // projectionMatrix.m11 = yScale;
    }


    private void updateFieldOfView() {
        final float fovInRads = Maths.toRads(fovInDegs);
        final float xScale = Maths.cotFromRads(fovInRads / 2);
        final float yScale = xScale * DisplayManager.getAspectRatio();

        projectionMatrix.m00 = xScale;
        projectionMatrix.m11 = yScale;
    }


    final protected void updateNearAndFarPlanes(float distToNearPlane, float distToFarPlane) {
        final float frustumLength = distToFarPlane - distToNearPlane;
        final float zScale = -((distToFarPlane + distToNearPlane) / frustumLength);
        final float zCompOfWProj = -((2 * distToFarPlane * distToNearPlane) / frustumLength);

        projectionMatrix.m22 = zScale;
        projectionMatrix.m32 = zCompOfWProj;

        projectionMatrix.m23 = -1;
    }


    final protected void partialRenderToCurrentBuffer() {
        prepareToRender();
        partialRender();
    }


    /**
     * Render everything except waters and guis.
     */
    abstract protected void partialRender();


    @Override
    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }


    abstract protected void cleanUpShaders();


    abstract protected void clearAllRenderableCollections();


    /**
     * To create more descriptive methods.
     * 
     * @param listToAddTo
     * @param listToAddFrom
     */
    final protected <T> void addListOfRenderablesToList(List<T> listToAddTo, List<T> listToAddFrom) {
        for (final T renderable:listToAddFrom)
            addRenderableToList(listToAddTo, renderable);
    }


    /**
     * To create more descriptive methods.
     * 
     * @param list
     * @param renderable
     */
    final protected <T> void addRenderableToList(List<T> list, T renderable) {
        list.add(renderable);
    }


    /**
     * To create more descriptive methods.
     * 
     * @param mapToAddTo
     * @param listToAddFrom
     */
    final protected <TM extends ILightAffectedTexturedModelable<?, ?>> void addListOfRenderablesToMap(
            Map<TM, List<Entity<TM>>> mapToAddTo, List<Entity<TM>> listToAddFrom) {
        for (final Entity<TM> entity:listToAddFrom)
            addRenderableToMap(mapToAddTo, entity);
    }


    /**
     * To create more descriptive methods.
     * 
     * @param texdModelsToEntsMap
     * @param ent
     */
    final protected <TM extends ILightAffectedTexturedModelable<?, ?>> void addRenderableToMap(
            Map<TM, List<Entity<TM>>> texdModelsToEntsMap, Entity<TM> ent) {
        final TM texturedModel = ent.getTexturedModel();
        final List<Entity<TM>> texdModelsBatch = texdModelsToEntsMap.get(texturedModel);

        if (texdModelsBatch != null) {
            texdModelsBatch.add(ent);
        } else {
            final List<Entity<TM>> texdModelsNewBatch = new ArrayList<Entity<TM>>();
            texdModelsNewBatch.add(ent);
            texdModelsToEntsMap.put(texturedModel, texdModelsNewBatch);
        }
    }

}
