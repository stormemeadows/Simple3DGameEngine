package core.ifaces;


import core.ifaces.opengl_api.IVertexBufferObject;

/**
 * @author storm
 */
public interface IBasicTexturable extends IVertexBufferObject {

	@Override
	int getVboID();


	/**
	 * @return the vboID, probably
	 */
	int getTextureID();

}
