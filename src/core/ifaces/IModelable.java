package core.ifaces;


import core.ifaces.opengl_api.IVertexArrayObject;

/**
 * @author storm
 */
public interface IModelable extends IVertexArrayObject {

	@Override
	int getVertexCount();


	@Override
	int getVaoID();

}
