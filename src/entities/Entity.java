package entities;


import org.lwjgl.util.vector.Vector3f;

import core.display.DisplayManager;
import core.ifaces.ILightAffectedTexturedModelable;
import core.ifaces.IOrientable3D;
import core.ifaces.IPositionable3D;
import core.ifaces.IScalable;

/**
 * @author storm
 *
 */
// public class Entity<TM extends
// ILightAffectedTexturedModelable<LightAffectedTexture, ?>>
public class Entity<TM extends ILightAffectedTexturedModelable<?, ?>>
		implements IOrientable3D, IPositionable3D, IScalable {


	private float	rotX, rotY, rotZ;	// orientation
	private float	scale;

	private TM texturedModel;

	private Vector3f position;


	/**
	 * @param texturedModel
	 * @param idxOfTextureInTextureImageFile
	 * @param position
	 * @param rotX
	 * @param rotY
	 * @param rotZ
	 * @param scale
	 */
	public Entity(TM texturedModel,
			int idxOfTextureInTextureImageFile, Vector3f position,
			float rotX, float rotY, float rotZ, float scale) {

		this.texturedModel = texturedModel;
		texturedModel.getTexture().setIndexOfTextureInTextureImageFile(idxOfTextureInTextureImageFile);
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}


	/**
	 * @param texturedModel
	 * @param idxOfTextureInTextureImageFile
	 * @param position
	 * @param scale
	 */
	public Entity(TM texturedModel,
			int idxOfTextureInTextureImageFile,
			Vector3f position, float scale) {
		this(texturedModel, idxOfTextureInTextureImageFile, position, 0, 0, 0, scale);
	}


	/**
	 * @param texturedModel
	 * @param orientation
	 * @param position
	 * @param scale
	 */
	public Entity(TM texturedModel, Vector3f position, Vector3f orientation, float scale) {
		this(texturedModel, 0, position, orientation, scale);
	}


	/**
	 * @param texturedModel
	 * @param position
	 * @param scale
	 */
	public Entity(TM texturedModel, Vector3f position, float scale) {
		this(texturedModel, 0, position, 0, 0, 0, scale);
	}


	/**
	 * @param texturedModel
	 * @param orientation
	 * @param position
	 */
	public Entity(TM texturedModel, Vector3f position, Vector3f orientation) {
		this(texturedModel, 0, position, orientation, 1);
	}


	/**
	 * @param texturedModel
	 * @param position
	 */
	public Entity(TM texturedModel, Vector3f position) {
		this(texturedModel, 0, position, 0, 0, 0, 1);
	}


	/**
	 * Accepts a vector specifying the orientation.
	 */
	public Entity(TM texturedModel, int idxOfTextureInTextureImageFile,
			Vector3f position, Vector3f orientation, float scale) {
		this(texturedModel, position, orientation.x, orientation.y, orientation.z, scale);
	}


	/**
	 * Sets the idxOfTextureInTextureImageFile to 0 by default.
	 */
	public Entity(TM texturedModel, Vector3f position,
			float rotX, float rotY, float rotZ, float scale) {
		this(texturedModel, 0, position, rotX, rotY, rotZ, scale);
	}


	/**
	 * @return Number of seconds since last frame.
	 */
	protected float worldTimeDeltaInSecs() {
		return DisplayManager.getFrameTimeSeconds();
	}


	// * @Chainable
	/**
	 * @param dx
	 * @param dy
	 * @param dz
	 */
	@Override
	public void increasePosition(float dx, float dy, float dz) {
		// public Entity<TM> increasePosition(float dx, float dy, float dz) {
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
		// return this;
	}


	/**
	 * @param translationVector
	 */
	@Override
	public void increasePosition(Vector3f translationVector) {
		increasePosition(translationVector.x, translationVector.y, translationVector.z);
	}


	public float getTextureXOffset() {
		return texturedModel.getTexture().getTextureXOffset();
	}


	public float getTextureYOffset() {
		return texturedModel.getTexture().getTextureYOffset();
	}


	/**
	 * @param dx
	 * @param dy
	 * @param dz
	 */
	@Override
	public void increaseRotation(float dx, float dy, float dz) {
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ += dz;
	}


	/**
	 * @param rotationVector
	 */
	@Override
	public void increaseRotation(Vector3f rotationVector) {
		increaseRotation(rotationVector.x, rotationVector.y, rotationVector.z);
	}


	/**
	 * @return the texturedModel
	 */
	public TM getTexturedModel() {
		return texturedModel;
	}


	/**
	 * @param texturedModel
	 *            the texturedModel to set
	 */
	public void setTexturedModel(TM texturedModel) {
		this.texturedModel = texturedModel;
	}


	/**
	 * @return the position
	 */
	@Override
	public Vector3f getPosition() {
		return position;
	}


	/**
	 * @param position
	 *            the position to set
	 */
	@Override
	public void setPosition(Vector3f position) {
		this.position = position;
	}


	/**
	 * @return the x
	 */
	@Override
	public float getX() {
		return position.x;
	}


	/**
	 * @param x
	 */
	@Override
	public void setX(float x) {
		this.position.x = x;
	}


	/**
	 * @return the y
	 */
	@Override
	public float getY() {
		return position.y;
	}


	/**
	 * @param y
	 */
	@Override
	public void setY(float y) {
		this.position.y = y;
	}


	/**
	 * @return the z
	 */
	@Override
	public float getZ() {
		return position.z;
	}


	/**
	 * @param z
	 */
	@Override
	public void setZ(float z) {
		this.position.z = z;
	}


	/**
	 * @return the orientation
	 */
	@Override
	public Vector3f getOrientation() {
		return new Vector3f(rotX, rotY, rotZ);
	}


	/**
	 * @return the rotX
	 */
	@Override
	public float getRotX() {
		return rotX;
	}


	/**
	 * @param rotX
	 *            the rotX to set
	 */
	@Override
	public void setRotX(float rotX) {
		this.rotX = rotX;
	}


	/**
	 * @return the rotY
	 */
	@Override
	public float getRotY() {
		return rotY;
	}


	/**
	 * @param rotY
	 *            the rotY to set
	 */
	@Override
	public void setRotY(float rotY) {
		this.rotY = rotY;
	}


	/**
	 * @return the rotZ
	 */
	@Override
	public float getRotZ() {
		return rotZ;
	}


	/**
	 * @param rotZ
	 *            the rotZ to set
	 */
	@Override
	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}


	/**
	 * @return the scale
	 */
	@Override
	public float getScale() {
		return scale;
	}


	/**
	 * @param scale
	 *            the scale to set
	 */
	@Override
	public void setScale(float scale) {
		this.scale = scale;
	}


}
