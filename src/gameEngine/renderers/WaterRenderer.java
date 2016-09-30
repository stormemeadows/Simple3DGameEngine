package gameEngine.renderers;


import static settings.GameConstants.WaterParameters.CLIP_PLANE_ERROR_ROOM;
import static settings.GameConstants.WaterParameters.DUDV_MAP_TEXTURE_FILE;
import static settings.GameConstants.WaterParameters.NORMAL_MAP_TEXTURE_FILE;
import static settings.GameConstants.WaterParameters.TILE_SIZE;
import static settings.GameConstants.WaterParameters.VERTICES;
import static settings.GameConstants.WaterParameters.WAVE_SPEED;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import core.display.DisplayManager;
import core.ifaces.IModelable;
import core.utils.Camera;
import core.utils.Maths;
import core.utils.helpers.H;
import gameEngine.loaders.MasterLoader;
import gameEngine.shaders.WaterShader;
import models.RawModel;
import textures.BasicTexture;
import visibles.lights.Light;
import visibles.waters.WaterTile;
import visibles.waters.utils.WaterFrameBuffers;


class WaterRenderer
        extends BasicRendererWithProjectionMatrix<WaterShader> {


    public RawModel quad;

    private final WaterFrameBuffers fbos;

    private float moveFactor = 0;
    // private float waveSpeed = 0.04f;

    private final BasicTexture dudvMapTexture;
    private final BasicTexture normalMapTexture;


    WaterRenderer(WaterShader shdr, Matrix4f prjnMat) {
        super(shdr, prjnMat);

        // Just x and z vertex positions here, y is set to 0 in vertex shader.
        this.quad = MasterLoader.store2DPositionsDataInVAOAndReturnRawModel(VERTICES);
        this.fbos = new WaterFrameBuffers();
        this.dudvMapTexture = new BasicTexture(DUDV_MAP_TEXTURE_FILE);
        this.normalMapTexture = new BasicTexture(NORMAL_MAP_TEXTURE_FILE);

        // H.pln("\nNum attribs: " + getNumVertexAttribs() + "\n");
    }


    @Override
    protected void initializationLogicBetweenStartingAndStoppingShader(Matrix4f projectionMatrix) {
        super.initializationLogicBetweenStartingAndStoppingShader(projectionMatrix);
        shader.connectTextureUnits();
    }


    public void run(List<WaterTile> waters, Camera camera, Light sun) {
        startShader();

        shaderLoadSceneData(camera, sun);
        shaderLoadMoveFactor();

        bindModelData(quad);
        activateAndBind2DTextures(getOrderedTextures());
        for (final WaterTile tile:waters) {
            shaderLoadModelMatrix(tile);
            drawModel(quad);
        }
        unbindModelData();
        // unbindVAOAndDisableVertexAttribArrays();

        stopShader();
    }


    protected void shaderLoadSceneData(Camera camera, Light sun) {
        shader.loadCamera(camera);
        shader.loadLight(sun);
    }


    private void shaderLoadMoveFactor() {
        moveFactor += WAVE_SPEED * DisplayManager.getFrameTimeSeconds();
        moveFactor %= 1;
        shader.loadMoveFactor(moveFactor);
    }


    private BasicTexture[] getOrderedTextures() {
        final BasicTexture[] retVal = {
                fbos.getReflectionTexture(),
                fbos.getRefractionTexture(),
                dudvMapTexture,
                normalMapTexture,
                fbos.getRefractionDepthTexture()
        };
        return retVal;
    }


    @Override
    protected void bindModelData(IModelable model) {
        super.bindModelData(model);
        // bindVAOAndEnableVertexAttribArrays(quad.getVaoID());
        MasterRenderer.enableAlphaBlending();
    }


    /**
     * Calls glDrawArrays, rather than glDrawElements.
     */
    @Override
    protected void drawModel(IModelable model) {
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
    }


    @Override
    protected void unbindModelData() {
        MasterRenderer.disableAlphaBlending();
        super.unbindModelData();
    }


    private void shaderLoadModelMatrix(WaterTile tile) {
        final Matrix4f modelMatrix = Maths.createAffineTransformationMatrix(
                tile.getPosition(), TILE_SIZE);
        shader.loadModelMatrix(modelMatrix);
    }


    @Override
    protected void finalize() throws Throwable {
        cleanUp();
        super.finalize();
    }


    @Override
    public void cleanUp() {
        fbos.cleanUp();
        super.cleanUp(); // cleans up shader
    }


    public void bindReflectionFrameBuffer() {
        fbos.bindReflectionFrameBuffer();
    }


    public void bindRefractionFrameBuffer() {
        fbos.bindRefractionFrameBuffer();
    }


    @Deprecated
    public void unbindCurrentFrameBuffer() {
        fbos.unbindCurrentFrameBuffer();
    }


    // smooth waters' edges
    public Vector4f createRefractClipPlane(WaterTile water) {
        final float h = water.getHeight() + CLIP_PLANE_ERROR_ROOM;
        return H.v4(0, -1, 0, h);
    }


    // smooth waters' edges
    public Vector4f createReflectClipPlane(WaterTile water) {
        final float h = water.getHeight() + CLIP_PLANE_ERROR_ROOM;
        return H.v4(0, 1, 0, -h);
    }
}
