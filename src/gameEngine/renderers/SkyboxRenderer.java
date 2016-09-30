package gameEngine.renderers;


import static settings.GameConstants.SkyboxParameters.TEXTURE2_FILES;
import static settings.GameConstants.SkyboxParameters.TEXTURE_FILES;
import static settings.GameConstants.SkyboxParameters.VERTICES;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import core.display.DisplayManager;
import core.ifaces.IModelable;
import core.utils.Camera;
import gameEngine.loaders.MasterLoader;
import gameEngine.shaders.SkyboxShader;
import models.RawModel;
import textures.BasicTexture;

/**
 * @author storm
 *
 */
// class SkyboxRenderer {
class SkyboxRenderer
        extends BasicRendererWithProjectionMatrix<SkyboxShader> {

    private final RawModel     cube;
    private final BasicTexture cubeMapTex1;
    private final BasicTexture cubeMapTex2;

    private BasicTexture curCubeMapTex1;
    private BasicTexture curCubeMapTex2;
    private float        blendFactor;

    private float curTime = 0;


    /**
     * @param loader
     * @param projectionMatrix
     */
    SkyboxRenderer(SkyboxShader shader, Matrix4f projectionMatrix) {
        super(shader, projectionMatrix);

        cube = loadCubeFromVertexData(VERTICES);
        cubeMapTex1 = loadCubeMapFromTexFiles(TEXTURE_FILES);
        cubeMapTex2 = loadCubeMapFromTexFiles(TEXTURE2_FILES);
        calcCurrentTexturesAndBlendFactor();
    }


    @Override
    protected void initializationLogicBetweenStartingAndStoppingShader(Matrix4f projectionMatrix) {
        super.initializationLogicBetweenStartingAndStoppingShader(projectionMatrix);
        shader.connectTextureUnits();
    }


    public void run(Camera camera, Vector3f colorRGB) {
        startShader();

        shaderLoadSceneData(camera, colorRGB);
        renderSkybox();

        stopShader();
    }


    protected void renderSkybox() {
        bindModelData(cube);

        calcCurrentTexturesAndBlendFactor();
        activateAndBindCubeMapTextures();
        shaderLoadBlendFactor();

        drawModel(cube);

        unbindModelData();
    }


    protected void shaderLoadSceneData(Camera camera, Vector3f colorRGB) {
        shader.loadCamera(camera);
        shader.loadFogColour(colorRGB);
    }


    private void calcCurrentTexturesAndBlendFactor() {
        final int[] timePeriods = {
                0, 500, 800, 2100, 2400
        };

        curTime += DisplayManager.getFrameTimeSeconds() * 100;
        curTime %= timePeriods[timePeriods.length - 1];

        if (curTime >= timePeriods[0] && curTime < timePeriods[1]) {
            curCubeMapTex1 = cubeMapTex2;
            curCubeMapTex2 = cubeMapTex2;
            blendFactor = (curTime - timePeriods[0]) / (timePeriods[1] - 0);
        } else if (curTime >= timePeriods[1] && curTime < timePeriods[2]) {
            curCubeMapTex1 = cubeMapTex2;
            curCubeMapTex2 = cubeMapTex1;
            blendFactor = (curTime - timePeriods[1]) / (timePeriods[2] - timePeriods[1]);
        } else if (curTime >= timePeriods[2] && curTime < timePeriods[3]) {
            curCubeMapTex1 = cubeMapTex1;
            curCubeMapTex2 = cubeMapTex1;
            blendFactor = (curTime - timePeriods[2]) / (timePeriods[3] - timePeriods[2]);
        } else {
            curCubeMapTex1 = cubeMapTex1;
            curCubeMapTex2 = cubeMapTex2;
            blendFactor = (curTime - timePeriods[3]) / (timePeriods[4] - timePeriods[3]);
        }

    }


    private void activateAndBindCubeMapTextures() {
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, curCubeMapTex1.getTextureID());

        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, curCubeMapTex2.getTextureID());
    }


    private void shaderLoadBlendFactor() {
        shader.loadBlendFactor(blendFactor);
    }


    private BasicTexture loadCubeMapFromTexFiles(String[] fileNames) {
        return new BasicTexture(MasterLoader.loadCubeMapFromPNGfilesAndReturnTexID(fileNames));
    }


    private RawModel loadCubeFromVertexData(float[] verts) {
        return MasterLoader.store3DPositionsDataInVAOAndReturnRawModel(verts);
    }


    /**
     * Calls glDrawArrays, rather than glDrawElements.
     */
    @Override
    protected void drawModel(IModelable model) {
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
    }


}
