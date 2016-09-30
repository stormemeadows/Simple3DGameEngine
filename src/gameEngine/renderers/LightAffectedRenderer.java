package gameEngine.renderers;


import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import core.ifaces.ILightAffectedTexturable;
import core.ifaces.ILightAffectedTexturedModelable;
import core.ifaces.IModelable;
import core.ifaces.ITexturable;
import core.ifaces.gameEngine.renderers.IShaderLoadsLightAffectedTextureData;
import core.ifaces.gameEngine.renderers.IShaderLoadsSceneData;
import core.ifaces.gameEngine.renderers.IShaderLoadsTransformationMatrix;
import core.utils.Camera;
import gameEngine.shaders.LightAffectedShader;
import visibles.lights.Light;

/**
 * @author storm
 *
 */
abstract class LightAffectedRenderer<S extends LightAffectedShader, TM extends ILightAffectedTexturedModelable<?, ?>>
        extends BasicRendererWithProjectionMatrix<S>
        implements IShaderLoadsLightAffectedTextureData<S>,
        IShaderLoadsSceneData<S>,
        IShaderLoadsTransformationMatrix<S> {


    /**
     * @param shader
     * @param projectionMatrix
     */
    LightAffectedRenderer(S shader, Matrix4f projectionMatrix) {
        super(shader, projectionMatrix);
    }


    // protected ILightAffectedTexturable curTex;
    // protected IModelable curModel;
    // protected void setCurTexAndModel(ILightAffectedTexturable tex,
    // IModelable model) {
    // this.curTex = tex;
    // this.curModel = model;
    // }
    // protected void setCurTexAndModel(ILightAffectedTexturedModelable<?, ?>
    // texdModel) {
    // setCurTexAndModel(texdModel.getTexture(), texdModel.getModel());
    // }


    protected void preDraw(ITexturable tex) {
        if (tex.isHasTransparency())
            MasterRenderer.disableCulling();
    }


    protected void postDraw() {
        MasterRenderer.enableCulling();
    }


    @Override
    protected void drawModel(IModelable model) {
        final int mode = GL11.GL_TRIANGLES;
        final int type = GL11.GL_UNSIGNED_INT;
        GL11.glDrawElements(mode, model.getVertexCount(), type, 0);
    }


    @Override
    public void shaderLoadTexData(ILightAffectedTexturable tex) {
        // if (tex.isHasTransparency()) MasterRenderer.disableCulling();

        shader.loadNumberOfRows(tex.getNumberOfRows());
        shader.loadOffset(tex.getTextureXOffset(), tex.getTextureYOffset());
        shader.loadFakeLightingVariable(tex.isUseFakeLighting());
        shader.loadShineVariables(tex.getShineDamper(), tex.getReflectivity());
    }


    @Override
    public void shaderLoadSceneData(List<Light> lights,
            Camera camera, Vector4f clipPlane, Vector3f colorRGB) {
        shader.loadClipPlane(clipPlane);
        shader.loadSkyColor(colorRGB);
        shader.loadLights(lights);
        shader.loadCamera(camera);
    }


    @Override
    public void shaderLoadTransformationMatrix(Matrix4f mat) {
        shader.loadTransformationMatrix(mat);
    }

    // protected class Drawer {
    //
    // private final ILightAffectedTexturable tex;
    // private final IModelable model;
    //
    //
    // public Drawer(ILightAffectedTexturable tex, IModelable model) {
    // this.tex = tex;
    // this.model = model;
    // }
    //
    //
    // public Drawer(ILightAffectedTexturedModelable<?, ?> texdModel) {
    // this(texdModel.getTexture(), texdModel.getModel());
    // }
    //
    //
    // public void bindModelData() {
    // LightAffectedRenderer.this.bindModelData(model);
    // }
    //
    //
    // public void drawModel() {
    // LightAffectedRenderer.this.drawModel(model);
    // }
    //
    //
    // public void activateAndBind2DTexture() {
    // LightAffectedRenderer.this.activateAndBind2DTexture(tex);
    // }
    //
    //
    // public void preDraw() {
    // LightAffectedRenderer.this.preDraw(tex);
    // }
    //
    //
    // public void postDraw() {
    // LightAffectedRenderer.this.postDraw();
    // }
    //
    //
    // public void unbindModelData() {
    // LightAffectedRenderer.this.unbindModelData();
    // }
    //
    //
    // public void shaderLoadTexData() {
    // LightAffectedRenderer.this.shaderLoadTexData(tex);
    // }
    // }

}
