package visibles.guis;


import org.lwjgl.util.vector.Vector2f;

import textures.BasicTexture;

/**
 * @author storm
 *
 */
public class GuiElement
        extends BasicTexture {

    private final Vector2f position;
    private final Vector2f scale;   // size of x, and of y


    /**
     * @param textureID
     * @param position
     * @param scale
     */
    public GuiElement(int textureID, Vector2f position, Vector2f scale) {
        super(textureID);
        this.position = position;
        this.scale = scale;
    }


    /**
     * @param texFileName
     * @param position
     * @param scale
     */
    public GuiElement(String texFileName, Vector2f position, Vector2f scale) {
        super(texFileName);
        this.position = position;
        this.scale = scale;
        // H.pln("\n\n\n", toString(), "\n\n\n");
    }


    @Override
    public String toString() {
        final String retVal = "GuiElement["
                + "ID:" + getTextureID() + ", "
                + "Pos:" + getPosition() + ", "
                + "Scale:" + getScale() + "]";
        return retVal;
    }


    /**
     * @return the position
     */
    public Vector2f getPosition() {
        return position;
    }


    /**
     * @return the scale
     */
    public Vector2f getScale() {
        return scale;
    }


}
