package visibles.waters.utils;


import static settings.GameConstants.WaterParameters.Buffers.REFLECTION_HEIGHT;
import static settings.GameConstants.WaterParameters.Buffers.REFLECTION_WIDTH;
import static settings.GameConstants.WaterParameters.Buffers.REFRACTION_HEIGHT;
import static settings.GameConstants.WaterParameters.Buffers.REFRACTION_WIDTH;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

import gameEngine.renderers.MasterRenderer;
import textures.BasicTexture;

/**
 * @author storm
 *
 */
public class WaterFrameBuffers {

    private int reflectionFrameBufferID;
    private int refractionFrameBufferID;
    private int reflectionDepthBufferID;

    private BasicTexture reflectionTexture;
    private BasicTexture refractionTexture;
    private BasicTexture refractionDepthTexture;


    /**
     * Call when loading the game.
     */
    public WaterFrameBuffers() {
        initReflectionFrameBuffer();
        initRefractionFrameBuffer();
    }


    @Override
    protected void finalize() throws Throwable {
        cleanUp();
        super.finalize();
    }


    /**
     * Call when closing the game.
     */
    public void cleanUp() {
        GL30.glDeleteFramebuffers(reflectionFrameBufferID);
        GL30.glDeleteFramebuffers(refractionFrameBufferID);
        GL30.glDeleteRenderbuffers(reflectionDepthBufferID);

        GL11.glDeleteTextures(reflectionTexture.getTextureID());
        GL11.glDeleteTextures(refractionTexture.getTextureID());
        GL11.glDeleteTextures(refractionDepthTexture.getTextureID());
    }


    /**
     * Call before rendering to this FBO.
     */
    public void bindReflectionFrameBuffer() {
        bindFrameBuffer(reflectionFrameBufferID, REFLECTION_WIDTH, REFLECTION_HEIGHT);
    }


    /**
     * Call before rendering to this FBO.
     */
    public void bindRefractionFrameBuffer() {
        bindFrameBuffer(refractionFrameBufferID, REFRACTION_WIDTH, REFRACTION_HEIGHT);
    }


    /**
     * Call to switch to default frame buffer.
     */
    public void unbindCurrentFrameBuffer() {
        MasterRenderer.bindDefaultFrameBuffer();
    }


    public BasicTexture getReflectionTexture() {
        return reflectionTexture;
    }


    public BasicTexture getRefractionTexture() {
        return refractionTexture;
    }


    public BasicTexture getRefractionDepthTexture() {
        return refractionDepthTexture;
    }


    private void initReflectionFrameBuffer() {
        reflectionFrameBufferID = createFrameBufferAndReturnID();
        reflectionTexture = createTextureAttachment(REFLECTION_WIDTH, REFLECTION_HEIGHT);
        reflectionDepthBufferID = createDepthBufferAttachmentAndReturnID(REFLECTION_WIDTH, REFLECTION_HEIGHT);
        unbindCurrentFrameBuffer();
    }


    private void initRefractionFrameBuffer() {
        refractionFrameBufferID = createFrameBufferAndReturnID();
        refractionTexture = createTextureAttachment(REFRACTION_WIDTH, REFRACTION_HEIGHT);
        refractionDepthTexture = createDepthTextureAttachment(REFRACTION_WIDTH, REFRACTION_HEIGHT);
        unbindCurrentFrameBuffer();
    }


    private void bindFrameBuffer(int frameBuffer, int width, int height) {
        final int target = GL11.GL_TEXTURE_2D;

        // To make sure the texture isn't bound
        GL11.glBindTexture(target, 0);
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);
        GL11.glViewport(0, 0, width, height);
    }


    private int createFrameBufferAndReturnID() {
        final int frameBufferID = GL30.glGenFramebuffers();

        // create the framebuffer
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBufferID);

        // indicate that we will always render to color attachment 0
        GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0);
        return frameBufferID;
    }


    private BasicTexture createTextureAttachment(int width, int height) {
        final int textureID = GL11.glGenTextures();
        final int target = GL11.GL_TEXTURE_2D;
        final int format = GL11.GL_RGB;
        final int fBuff = GL30.GL_FRAMEBUFFER;

        GL11.glBindTexture(target, textureID);
        GL11.glTexImage2D(target, 0, format, width, height,
                0, format, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);

        setTexParams();
        GL32.glFramebufferTexture(fBuff, GL30.GL_COLOR_ATTACHMENT0, textureID, 0);

        return new BasicTexture(textureID);
    }


    private BasicTexture createDepthTextureAttachment(int width, int height) {
        final int depthTextureID = GL11.glGenTextures();
        final int target = GL11.GL_TEXTURE_2D;
        final int fBuff = GL30.GL_FRAMEBUFFER;

        GL11.glBindTexture(target, depthTextureID);
        GL11.glTexImage2D(target, 0, GL14.GL_DEPTH_COMPONENT32, width, height,
                0, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, (ByteBuffer) null);

        setTexParams();
        GL32.glFramebufferTexture(fBuff, GL30.GL_DEPTH_ATTACHMENT, depthTextureID, 0);

        return new BasicTexture(depthTextureID);
    }


    private int createDepthBufferAttachmentAndReturnID(int width, int height) {
        final int depthBufferID = GL30.glGenRenderbuffers();
        final int rBuff = GL30.GL_RENDERBUFFER;
        final int fBuff = GL30.GL_FRAMEBUFFER;

        GL30.glBindRenderbuffer(rBuff, depthBufferID);
        GL30.glRenderbufferStorage(rBuff, GL11.GL_DEPTH_COMPONENT, width, height);
        GL30.glFramebufferRenderbuffer(fBuff, GL30.GL_DEPTH_ATTACHMENT, rBuff, depthBufferID);
        return depthBufferID;
    }


    private void setTexParams() {
        final int target = GL11.GL_TEXTURE_2D;

        GL11.glTexParameteri(target, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(target, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
    }


}
