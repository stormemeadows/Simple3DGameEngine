package gameEngine.renderers.delegates;


import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * @author storm
 *
 */
public class VAOHandler {

    /**
     * 1. Binds the VAO,
     * 2. then enables the attrib arrays in the VAO.
     * 
     * @param vaoID
     * @param numVertexAttribs
     *            Number of vertex attributes. Eg, position coords, tex coords,
     *            and surface normal components could total to 3 vertex attribs.
     */
    public void bindVAOAndEnableVertexAttribArrays(int vaoID, int numVertexAttribs) {
        bindVAO(vaoID);
        enableVertexAttribArrays(numVertexAttribs);
    }


    /**
     * 1. Disables the attrib arrays in the VAO,
     * 2. then unbinds the VAO.
     * 
     * @param vaoID
     * @param numVertexAttribs
     *            Number of vertex attributes. Eg, position coords, tex coords,
     *            and surface normal components could total to 3 vertex attribs.
     */
    public void unbindVAOAndDisableVertexAttribArrays(int numVertexAttribs) {
        disableVertexAttribArrays(numVertexAttribs);
        unbindVAO();
    }


    /********************************************************************************/

    /**
     * Enable the arrays for the VAO.
     * 
     * @param numVertexAttribArrays
     */
    private void disableVertexAttribArrays(int numVertexAttribArrays) {
        for (int i = 0; i < numVertexAttribArrays; i++) {
            GL20.glDisableVertexAttribArray(i);
            // H.pln("Disabled vert attrib array:\t" + i);
        }
    }


    /**
     * Enable the arrays for the VAO.
     * 
     * @param numVertexAttribArrays
     */
    private void enableVertexAttribArrays(int numVertexAttribArrays) {
        for (int i = 0; i < numVertexAttribArrays; i++) {
            GL20.glEnableVertexAttribArray(i);
            // H.pln("Enabled vert attrib array:\t" + i);
        }
    }


    /**
     * Bind the VAO.
     * 
     * @param vertexArrayID
     *            Aka, vaoID
     */
    private void bindVAO(int vertexArrayID) {
        GL30.glBindVertexArray(vertexArrayID);
        // H.pln(vertexArrayID == 0 ? "Unbound last vaoID." : "Bound
        // vaoID:\t\t\t" + vertexArrayID);
    }


    /**
     * Unbind the VAO (binding vertexArrayID 0).
     */
    private void unbindVAO() {
        bindVAO(0); // GL30.glBindVertexArray(0);
    }


}


//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
