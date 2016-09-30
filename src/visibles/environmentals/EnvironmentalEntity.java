package visibles.environmentals;


import org.lwjgl.util.vector.Vector3f;

import entities.Entity;

/**
 * @author storm
 *
 */
public class EnvironmentalEntity
        extends Entity<EnvironmentalTexturedModel> {

    /**
     * @param texturedModel
     * @param idxOfTextureInTextureImageFile
     * @param position
     * @param rotX
     * @param rotY
     * @param rotZ
     * @param scale
     */
    public EnvironmentalEntity(EnvironmentalTexturedModel texturedModel, int idxOfTextureInTextureImageFile,
            Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(texturedModel, idxOfTextureInTextureImageFile, position, rotX, rotY, rotZ, scale);
    }


    /**
     * @param texturedModel
     * @param position
     * @param rotX
     * @param rotY
     * @param rotZ
     * @param scale
     */
    public EnvironmentalEntity(EnvironmentalTexturedModel texturedModel, Vector3f position,
            float rotX, float rotY, float rotZ, float scale) {
        super(texturedModel, position, rotX, rotY, rotZ, scale);
    }


    /**
     * @param texturedModel
     * @param idxOfTextureInTextureImageFile
     * @param position
     * @param scale
     */
    public EnvironmentalEntity(EnvironmentalTexturedModel texturedModel, int idxOfTextureInTextureImageFile,
            Vector3f position, float scale) {
        super(texturedModel, idxOfTextureInTextureImageFile, position, scale);
        // TODO Auto-generated constructor stub
    }


    /**
     * @param texturedModel
     * @param idxOfTextureInTextureImageFile
     * @param position
     * @param orientation
     * @param scale
     */
    public EnvironmentalEntity(EnvironmentalTexturedModel texturedModel, int idxOfTextureInTextureImageFile,
            Vector3f position, Vector3f orientation, float scale) {
        super(texturedModel, idxOfTextureInTextureImageFile, position, orientation, scale);
        // TODO Auto-generated constructor stub
    }


    /**
     * @param texturedModel
     * @param position
     * @param scale
     */
    public EnvironmentalEntity(EnvironmentalTexturedModel texturedModel, Vector3f position, float scale) {
        super(texturedModel, position, scale);
        // TODO Auto-generated constructor stub
    }


    /**
     * @param texturedModel
     * @param position
     * @param orientation
     * @param scale
     */
    public EnvironmentalEntity(EnvironmentalTexturedModel texturedModel, Vector3f position, Vector3f orientation,
            float scale) {
        super(texturedModel, position, orientation, scale);
        // TODO Auto-generated constructor stub
    }


    /**
     * @param texturedModel
     * @param position
     * @param orientation
     */
    public EnvironmentalEntity(EnvironmentalTexturedModel texturedModel, Vector3f position, Vector3f orientation) {
        super(texturedModel, position, orientation);
        // TODO Auto-generated constructor stub
    }


    /**
     * @param texturedModel
     * @param position
     */
    public EnvironmentalEntity(EnvironmentalTexturedModel texturedModel, Vector3f position) {
        super(texturedModel, position);
        // TODO Auto-generated constructor stub
    }

}
