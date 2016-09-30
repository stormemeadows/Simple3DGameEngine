package core.utils;


import static settings.GameConstants.CameraParameters.PITCH_SENSITIVITY;
import static settings.GameConstants.CameraParameters.ROT_SENSITIVITY;
import static settings.GameConstants.CameraParameters.ZOOM_SENSITIVITY;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import core.ifaces.IPositionable3D;
import entities.Entity;

/**
 * @author storm
 *
 */
public class Camera
        implements IPositionable3D {

    private final static int MOUSE_BUTTON_L = 0;
    private final static int MOUSE_BUTTON_R = 1;

    private Vector3f position = new Vector3f(0, 0, 0);

    private float pitch = 20f; // how high/low the camera is aimed
    private float yaw   = 0f;  // how left/right the camera is aimed
    private float roll  = 0f;  // how tilted the camera is


    private float distanceFromEntity = 50; // zoom
    private float angleAroundEntity  = 0;


    private final Entity<?> entity;


    /**
     * @param other
     */
    public Camera(Camera other) {
        entity = other.entity;
        pitch = other.getPitch();
        yaw = other.getYaw();
        roll = other.getRoll();
        distanceFromEntity = other.distanceFromEntity;
        angleAroundEntity = other.angleAroundEntity;
        position = new Vector3f(other.position);
    }


    /**
     * @param entity
     */
    public Camera(Entity<?> entity) {
        this.entity = entity;
        this.position.x = entity.getPosition().x;
        this.position.y = entity.getPosition().y + distanceFromEntity;
        this.position.z = entity.getPosition().z;
    }


    /**
     * @return the pitch
     */
    public float getPitch() {
        return pitch;
    }


    /**
     * @return the roll
     */
    public float getRoll() {
        return roll;
    }


    /**
     * @return the yaw
     */
    public float getYaw() {
        return yaw;
    }


    public void invertPitch() {
        this.pitch = -pitch;
    }


    public void invertRoll() {
        this.roll = -roll;
    }


    public void invertYaw() {
        this.yaw = -yaw;
    }


    /**
     * @param translationVector
     */
    @Override
    public void increasePosition(Vector3f translationVector) {
        increasePosition(translationVector.x, translationVector.y, translationVector.z);
    }


    /**
     * @param dx
     * @param dy
     * @param dz
     */
    @Override
    public void increasePosition(float dx, float dy, float dz) {
        increaseX(dx);
        increaseY(dy);
        increaseZ(dz);
    }


    /**
     * @param d
     */
    public void increaseX(float d) {
        position.x += d;
    }


    /**
     * @param d
     */
    public void increaseY(float d) {
        position.y += d;
    }


    /**
     * @param d
     */
    public void increaseZ(float d) {
        position.z += d;
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


    public void move() {
        checkInputs();
        update();
    }


    private void calcAngleAroundEntity() {
        if (Mouse.isButtonDown(MOUSE_BUTTON_L)) {
            final float angleChange = Mouse.getDX() * ROT_SENSITIVITY;
            angleAroundEntity -= angleChange;
        }
    }


    /**
     * @return Horizontal distance from player.
     */
    private float calcHDist() {
        return (float) (distanceFromEntity * Math.cos(Math.toRadians(pitch)));
    }


    private void calcPitch() {
        if (Mouse.isButtonDown(MOUSE_BUTTON_R)) {
            final float pitchChange = Mouse.getDY() * PITCH_SENSITIVITY;
            pitch -= pitchChange;
        }
    }


    private float calcTheta() {
        return entity.getRotY() + angleAroundEntity;
    }


    /**
     * @return Vertical distance from player.
     */
    private float calcVDist() {
        return (float) (distanceFromEntity * Math.sin(Math.toRadians(pitch)));
    }


    private void calcZoom() {
        final float zoomLevel = Mouse.getDWheel() * ZOOM_SENSITIVITY;
        distanceFromEntity -= zoomLevel;
    }


    /**
     * Update the Camera's position.
     */
    private void update() {
        final float theta = calcTheta();
        final float hDist = calcHDist();
        final float vDist = calcVDist();
        final float xOffset = (float) (hDist * Math.sin(Math.toRadians(theta)));
        final float zOffset = (float) (hDist * Math.cos(Math.toRadians(theta)));

        // adjust position
        position.x = entity.getPosition().x - xOffset;
        position.z = entity.getPosition().z - zOffset;
        position.y = entity.getPosition().y + vDist;

        // adjust remaining angle
        this.yaw = 180 - theta;
        this.yaw %= 360;
    }


    protected void checkInputs() {
        calcZoom();
        calcPitch();
        calcAngleAroundEntity();
    }

}
