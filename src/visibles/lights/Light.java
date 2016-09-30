package visibles.lights;


import org.lwjgl.util.vector.Vector3f;

import core.ifaces.IHasPosition3D;

/**
 * @author storm
 * @desc The lighting calculations will be done entirely our vertex and fragment
 *       shader programs, so the gameEngine.shaders need access to the colour
 *       and position
 *       of the light. The fragment and vertex gameEngine.shaders handle the
 *       light's colour
 *       and position, resp.
 *
 */
public class Light implements IHasPosition3D {

	private Vector3f	position;
	private Vector3f	colour;

	// attenuation coefficients. 1,0,0 means no attenuation
	private Vector3f attenuation = new Vector3f(1, 0, 0);


	/**
	 * @param position
	 * @param colour
	 */
	public Light(Vector3f position, Vector3f colour) {
		this.position = position;
		this.colour = colour;
	}


	/**
	 * @param position
	 * @param colour
	 */
	public Light(Vector3f position, Vector3f colour, Vector3f attenuation) {
		// this(position, colour);
		this.position = position;
		this.colour = colour;
		this.attenuation = attenuation;
	}


	/**
	 * @return the attenuation
	 */
	public Vector3f getAttenuation() {
		return attenuation;
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
	public void setPosition(Vector3f position) {
		this.position = position;
	}


	/**
	 * @return the colour
	 */
	public Vector3f getColour() {
		return colour;
	}


	/**
	 * @param colour
	 *            the colour to set
	 */
	public void setColour(Vector3f colour) {
		this.colour = colour;
	}


	@Override
	public float getX() {
		return position.x;
	}


	@Override
	public float getY() {
		return position.y;
	}


	@Override
	public float getZ() {
		return position.z;
	}

}
