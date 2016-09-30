package core.ifaces;


import org.lwjgl.util.vector.Vector3f;

/**
 * @author storm
 *
 */
public interface IPositionable3D extends IHasPosition3D {


	void increasePosition(Vector3f translationVector);


	void increasePosition(float dx, float dy, float dz);


	@Override
	Vector3f getPosition();


	void setPosition(Vector3f position);


	@Override
	float getX();


	void setX(float x);


	@Override
	float getY();


	void setY(float y);


	@Override
	float getZ();


	void setZ(float z);


}

