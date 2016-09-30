package textures.utils;


import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import textures.BasicTexture;

/**
 * @author storm
 *
 */
public class StaticTexHandler {

	private StaticTexHandler() {}


	// /**
	// * Activate and bind each texture.
	// *
	// * Can loop through like this because the 32 texture locations,
	// * GL_TEXTURE0, GL_TEXTURE1, ..., GL_TEXTURE31,
	// * are just ints, each differing by 1.
	// *
	// * @param glTexLoc
	// * The texture location to bind the texture.
	// * @param texBlender
	// * The texture to bind.
	// *
	// */
	// public static <T extends BasicTexture> void
	// activateAndBind2DTexturesInTextureBlender(
	// int glTexLoc, BasicBlendedTexture<T> texBlender) {
	// final BasicTexBlendPack<T> texPack = texBlender.getTexPack();
	//
	// final BasicTexture[] textures = {
	// texPack.getBackgroundTexture(),
	// texPack.getrTexture(),
	// texPack.getgTexture(),
	// texPack.getbTexture(),
	// texBlender.getBlendMap()
	// };
	//
	// activateAndBind2DTextures(glTexLoc, textures);
	// }


	/**
	 * @param texture
	 *            The texture to bind.
	 * 
	 * @param glTexLoc
	 *            The texture location to bind the texture.
	 */
	public static <T extends BasicTexture> void activateAndBind2DTexture(int glTexLoc, T texture) {
		final int texID = texture.getTextureID();
		GL13.glActiveTexture(glTexLoc);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texID);
	}


	/**
	 * Activate and bind each texture.
	 * 
	 * Can loop through like this because the 32 texture locations,
	 * GL_TEXTURE0, GL_TEXTURE1, ..., GL_TEXTURE31,
	 * are just ints, each differing by 1.
	 * 
	 * @param glStartTexLoc
	 *            The texture location to start from.
	 * @param textures
	 *            The textures to bind.
	 */
	public static <T extends BasicTexture> void activateAndBind2DTextures(int glStartTexLoc, T[] textures) {
		for (int i = 0; i < textures.length; i++) {
			activateAndBind2DTexture(glStartTexLoc + i, textures[i]);
		}
	}

}
