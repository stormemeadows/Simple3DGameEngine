package textures.utils;


import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import core.ifaces.IBasicTexturable;

/**
 * @author storm
 *
 */
public class TexHandler {


    /**
     * @param texture
     *            The texture to bind.
     * 
     * @param glTexLoc
     *            The texture location to bind the texture.
     */
    public <T extends IBasicTexturable> void activateAndBind2DTexture(int glTexLoc, T texture) {
        GL13.glActiveTexture(glTexLoc);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
        // H.pln("Activated texture loc:\t" + glTexLoc);
        // H.pln("Activated textureID:\t" + texture.getTextureID());
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
    public <T extends IBasicTexturable> void activateAndBind2DTextures(int glStartTexLoc, T[] textures) {
        for (int i = 0; i < textures.length; i++)
            activateAndBind2DTexture(glStartTexLoc + i, textures[i]);
    }


    public <T extends IBasicTexturable> void activateAndBind2DTextures(int glStartTexLoc, List<T> textures) {
        for (int i = 0; i < textures.size(); i++)
            activateAndBind2DTexture(glStartTexLoc + i, textures.get(i));
    }

}


// package textures.utils;
//
//
// import org.lwjgl.opengl.GL11;
// import org.lwjgl.opengl.GL13;
//
// import core.ifaces.IBasicTexturable;
//
/// **
// * @author storm
// *
// */
// public class TexHandler {
//
//
// /**
// * @param texture
// * The texture to bind.
// *
// * @param glTexLoc
// * The texture location to bind the texture.
// */
// public <T extends IBasicTexturable> void activateAndBind2DTexture(
// int glTexLoc, T texture, int targetTexType) {
// GL13.glActiveTexture(glTexLoc);
// GL11.glBindTexture(targetTexType, texture.getTextureID());
// }
//
//
// /**
// * @param texture
// * The texture to bind.
// *
// * @param glTexLoc
// * The texture location to bind the texture.
// */
// public <T extends IBasicTexturable> void activateAndBind2DTexture(int
// glTexLoc, T texture) {
// activateAndBind2DTexture(glTexLoc, texture, GL11.GL_TEXTURE_2D);
// // GL13.glActiveTexture(glTexLoc);
// // GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
// }
//
//
// /**
// * Activate and bind each texture.
// *
// * Can loop through like this because the 32 texture locations,
// * GL_TEXTURE0, GL_TEXTURE1, ..., GL_TEXTURE31,
// * are just ints, each differing by 1.
// *
// * @param glStartTexLoc
// * The texture location to start from.
// * @param textures
// * The textures to bind.
// */
// public <T extends IBasicTexturable> void activateAndBind2DTextures(
// int glStartTexLoc, T[] textures, int targetTexType) {
// for (int i = 0; i < textures.length; i++)
// activateAndBind2DTexture(glStartTexLoc + i, textures[i], targetTexType);
// }
//
//
// /**
// * Activate and bind each texture.
// *
// * Can loop through like this because the 32 texture locations,
// * GL_TEXTURE0, GL_TEXTURE1, ..., GL_TEXTURE31,
// * are just ints, each differing by 1.
// *
// * @param glStartTexLoc
// * The texture location to start from.
// * @param textures
// * The textures to bind.
// */
// public <T extends IBasicTexturable> void activateAndBind2DTextures(int
// glStartTexLoc, T[] textures) {
// activateAndBind2DTextures(glStartTexLoc, textures, GL11.GL_TEXTURE_2D);
// }
//
// }
