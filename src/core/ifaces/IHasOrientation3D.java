package core.ifaces;


import org.lwjgl.util.vector.Vector3f;

/**
 * @author storm
 *
 */
public interface IHasOrientation3D {

	Vector3f getOrientation();


	float getRotX();


	float getRotY();


	float getRotZ();

}
