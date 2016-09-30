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

import core.display.DisplayManager;

/**
 * @author storm
 *
 */
public class WaterFrameBuffersV2 {

	private int	reflectionFrameBufferID;
	private int	reflectionTextureID;
	private int	reflectionDepthBufferID;

	private int	refractionFrameBufferID;
	private int	refractionTextureID;
	private int	refractionDepthTextureID;


	/**
	 * Call when loading the game.
	 */
	public WaterFrameBuffersV2() {
		initReflectionFrameBuffer();
		initRefractionFrameBuffer();
	}


	/**
	 * Call when closing the game.
	 */
	public void cleanUp() {
		GL30.glDeleteFramebuffers(reflectionFrameBufferID);
		GL30.glDeleteFramebuffers(refractionFrameBufferID);

		GL11.glDeleteTextures(reflectionTextureID);
		GL11.glDeleteTextures(refractionTextureID);

		GL30.glDeleteRenderbuffers(reflectionDepthBufferID);

		GL11.glDeleteTextures(refractionDepthTextureID);
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
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		GL11.glViewport(0, 0, DisplayManager.getWidth(), DisplayManager.getHeight());
	}


	/**
	 * @return the resulting textureID
	 */
	public int getReflectionTexture() {
		return reflectionTextureID;
	}


	/**
	 * @return the resulting textureID
	 */
	public int getRefractionTexture() {
		return refractionTextureID;
	}


	/**
	 * @return the resulting depthTextureID
	 */
	public int getRefractionDepthTexture() {
		return refractionDepthTextureID;
	}


	private void initReflectionFrameBuffer() {
		reflectionFrameBufferID = createFrameBuffer();
		reflectionTextureID = createTextureAttachment(REFLECTION_WIDTH, REFLECTION_HEIGHT);
		reflectionDepthBufferID = createDepthBufferAttachment(REFLECTION_WIDTH, REFLECTION_HEIGHT);
		unbindCurrentFrameBuffer();
	}


	private void initRefractionFrameBuffer() {
		refractionFrameBufferID = createFrameBuffer();
		refractionTextureID = createTextureAttachment(REFRACTION_WIDTH, REFRACTION_HEIGHT);
		refractionDepthTextureID = createDepthTextureAttachment(REFRACTION_WIDTH, REFRACTION_HEIGHT);
		unbindCurrentFrameBuffer();
	}


	private void bindFrameBuffer(int frameBuffer, int width, int height) {
		final int target = GL11.GL_TEXTURE_2D;

		// To make sure the texture isn't bound
		GL11.glBindTexture(target, 0);
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);
		GL11.glViewport(0, 0, width, height);
	}


	private int createFrameBuffer() {
		final int frameBuffer = GL30.glGenFramebuffers();

		// create the framebuffer
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);

		// indicate that we will always render to color attachment 0
		GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0);
		return frameBuffer;
	}


	private int createTextureAttachment(int width, int height) {
		final int textureID = GL11.glGenTextures();
		final int target = GL11.GL_TEXTURE_2D;
		final int format = GL11.GL_RGB;
		final int fBuff = GL30.GL_FRAMEBUFFER;

		GL11.glBindTexture(target, textureID);
		GL11.glTexImage2D(target, 0, format, width, height,
				0, format, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);

		setTexParams();
		GL32.glFramebufferTexture(fBuff, GL30.GL_COLOR_ATTACHMENT0, textureID, 0);
		return textureID;
	}


	private int createDepthTextureAttachment(int width, int height) {
		final int depthTextureID = GL11.glGenTextures();
		final int target = GL11.GL_TEXTURE_2D;
		final int fBuff = GL30.GL_FRAMEBUFFER;

		GL11.glBindTexture(target, depthTextureID);
		GL11.glTexImage2D(target, 0, GL14.GL_DEPTH_COMPONENT32, width, height,
				0, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, (ByteBuffer) null);

		setTexParams();
		GL32.glFramebufferTexture(fBuff, GL30.GL_DEPTH_ATTACHMENT, depthTextureID, 0);
		return depthTextureID;
	}


	private int createDepthBufferAttachment(int width, int height) {
		final int depthBuffer = GL30.glGenRenderbuffers();
		final int rBuff = GL30.GL_RENDERBUFFER;
		final int fBuff = GL30.GL_FRAMEBUFFER;

		GL30.glBindRenderbuffer(rBuff, depthBuffer);
		GL30.glRenderbufferStorage(rBuff, GL11.GL_DEPTH_COMPONENT, width, height);
		GL30.glFramebufferRenderbuffer(fBuff, GL30.GL_DEPTH_ATTACHMENT, rBuff, depthBuffer);
		return depthBuffer;
	}


	private void setTexParams() {
		final int target = GL11.GL_TEXTURE_2D;

		GL11.glTexParameteri(target, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(target, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	}


}
