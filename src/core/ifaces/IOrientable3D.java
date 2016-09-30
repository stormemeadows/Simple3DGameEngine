package core.ifaces;


import org.lwjgl.util.vector.Vector3f;

/**
 * @author storm
 *
 */
public interface IOrientable3D extends IHasOrientation3D {

	void increaseRotation(Vector3f rotationVector);


	void increaseRotation(float dx, float dy, float dz);


	@Override
	Vector3f getOrientation();


	@Override
	float getRotX();


	@Override
	float getRotY();


	@Override
	float getRotZ();


	void setRotX(float rotX);


	void setRotY(float rotY);


	void setRotZ(float rotZ);


}
