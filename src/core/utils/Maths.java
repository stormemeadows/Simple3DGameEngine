package core.utils;


import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

/**
 * @author storm
 *
 */
public class Maths {

    public final static Vector3f X_AXIS = v3(1, 0, 0);
    public final static Vector3f Y_AXIS = v3(0, 1, 0);
    public final static Vector3f Z_AXIS = v3(0, 0, 1);


    /**
     * Creates the 4D matrix for the specified transformation on 3D space.
     * 
     * @param translation
     *            The vector representing the magnitude and direction of
     *            translation.
     * @param rx
     *            First Euler angle.
     * @param ry
     *            Second Euler angle.
     * @param rz
     *            Third Euler angle.
     * @param scale
     * @return The 4D square matrix for the transformation.
     */
    public static Matrix4f createTransformationMatrix(Vector3f translation,
            float rx, float ry, float rz, float scalar) {
        final Matrix4f matrix = initMatrix4f();

        matTranslate(translation, matrix);

        matRotateInDegs(rx, X_AXIS, matrix);
        matRotateInDegs(ry, Y_AXIS, matrix);
        matRotateInDegs(rz, Z_AXIS, matrix);

        matScale(v3(scalar, scalar, scalar), matrix);

        return matrix;
    }


    public static Matrix4f createTransformationMatrix(Vector3f translation,
            double rx, double ry, double rz, double scalar) {
        return createTransformationMatrix(translation, (float) rx, (float) ry, (float) rz, (float) scalar);
    }


    /**
     * Translates and scales.
     * No rotations.
     * 
     * @param translation
     *            The vector representing the magnitude and direction of
     *            translation.
     * @param scalar
     * @return The 4D square matrix for the transformation.
     */
    public static Matrix4f createAffineTransformationMatrix(Vector3f translation, float scalar) {
        final Matrix4f matrix = initMatrix4f();

        matTranslate(translation, matrix);

        matScale(v3(scalar, scalar, scalar), matrix);

        return matrix;
    }


    public static Matrix4f createAffineTransformationMatrix(Vector3f translation, double scalar) {
        return createAffineTransformationMatrix(translation, (float) scalar);
    }


    /**
     * Translates and scales.
     * No rotations.
     * 
     * @param translation
     *            The vector representing the magnitude and direction of
     *            translation.
     * @param scalar
     * @return The 4D square matrix for the transformation.
     */
    public static Matrix4f createTranslationTransformationMatrix(Vector3f translation) {
        final Matrix4f matrix = initMatrix4f();

        matTranslate(translation, matrix);

        return matrix;
    }


    /**
     * Creates the 4D matrix for the specified transformation on 3D space,
     * but without rotation and in a 2D affine subspace (hence z component is
     * 1 in scaling step).
     * 
     * @param translation
     *            The vector representing the magnitude and direction of
     *            translation.
     * @param scale
     * @return The 4D square matrix for the transformation.
     */
    public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scaleVect) {
        final Matrix4f matrix = initMatrix4f();

        matTranslate(translation, matrix);

        final Vector3f scaleVect3D = v3(scaleVect.x, scaleVect.y, 1);
        matScale(scaleVect3D, matrix);

        return matrix;
    }


    /**
     * Creates a view matrix.
     * 
     * @param camera
     * @return The view matrix.
     */
    public static Matrix4f createViewMatrix(Camera camera) {
        final Matrix4f matrix = initMatrix4f();

        matRotateInDegs(camera.getPitch(), X_AXIS, matrix);
        matRotateInDegs(camera.getYaw(), Y_AXIS, matrix);
        matRotateInDegs(camera.getRoll(), Z_AXIS, matrix);

        final Vector3f pos = camera.getPosition();
        final Vector3f negPos = v3(-pos.x, -pos.y, -pos.z);
        matTranslate(negPos, matrix);

        return matrix;
    }


    public static float barryCentric(Vector3f pt1, Vector3f pt2, Vector3f pt3, Vector2f pos) {
        final float det = (pt2.z - pt3.z) * (pt1.x - pt3.x) + (pt3.x - pt2.x) * (pt1.z - pt3.z);
        final float l1 = ((pt2.z - pt3.z) * (pos.x - pt3.x) + (pt3.x - pt2.x) * (pos.y - pt3.z)) / det;
        final float l2 = ((pt3.z - pt1.z) * (pos.x - pt3.x) + (pt1.x - pt3.x) * (pos.y - pt3.z)) / det;
        final float l3 = 1.0f - l1 - l2;
        return l1 * pt1.y + l2 * pt2.y + l3 * pt3.y;
    }


    /***************************************************************************
     ************************* Convenience functions ***************************
     ***************************************************************************/

    /**
     * @return A 4D identity matrix.
     */
    private static Matrix4f initMatrix4f() {
        final Matrix4f mat = new Matrix4f();
        mat.setIdentity();
        return mat;
    }


    /**
     * @param scaleVect
     * @param matrixToMutate
     */
    private static void matScale(Vector3f scaleVect, Matrix4f matrixToMutate) {
        Matrix4f.scale(scaleVect, matrixToMutate, matrixToMutate);
    }


    /**
     * @param transVect
     * @param matrixToMutate
     */
    private static void matTranslate(Vector2f transVect, Matrix4f matrixToMutate) {
        Matrix4f.translate(transVect, matrixToMutate, matrixToMutate);
    }


    /**
     * @param transVect
     * @param matrixToMutate
     */
    private static void matTranslate(Vector3f transVect, Matrix4f matrixToMutate) {
        Matrix4f.translate(transVect, matrixToMutate, matrixToMutate);
    }


    /**
     * @param angleInDegrees
     * @param axis
     * @param matrixToMutate
     */
    public static void matRotateInDegs(double angleInDegrees, Vector3f axis, Matrix4f matrixToMutate) {
        matRotateInRads(toRads(angleInDegrees), axis, matrixToMutate);
    }


    /**
     * @param angleInRadians
     * @param axis
     * @param matrixToMutate
     */
    public static void matRotateInRads(double angleInRadians, Vector3f axis, Matrix4f matrixToMutate) {
        matRotateInRads((float) angleInRadians, axis, matrixToMutate);
    }


    public static void matRotateInRads(float angleInRadians, Vector3f axis, Matrix4f matrixToMutate) {
        Matrix4f.rotate(angleInRadians, axis, matrixToMutate, matrixToMutate);
    }


    ////////////
    // Radians


    ////
    // floats

    public static float toRads(float angleInDegrees) {
        return (float) Math.toRadians(angleInDegrees);
    }


    public static float sinFromRads(float angleInRads) {
        return (float) Math.sin(angleInRads);
    }


    public static float cosFromRads(float angleInRads) {
        return (float) Math.cos(angleInRads);
    }


    public static float tanFromRads(float angleInRads) {
        return (float) Math.tan(angleInRads);
    }


    public static float cscFromRads(float angleInRads) {
        return 1 / sinFromRads(angleInRads);
    }


    public static float secFromRads(float angleInRads) {
        return 1 / cosFromRads(angleInRads);
    }


    public static float cotFromRads(float angleInRads) {
        return 1 / tanFromRads(angleInRads);
    }


    ////
    // doubles

    public static double toRads(double angleInDegrees) {
        return Math.toRadians(angleInDegrees);
    }


    public static double sinFromRads(double angleInRads) {
        return Math.sin(angleInRads);
    }


    public static double cosFromRads(double angleInRads) {
        return Math.cos(angleInRads);
    }


    public static double tanFromRads(double angleInRads) {
        return Math.tan(angleInRads);
    }


    public static double cscFromRads(double angleInRads) {
        return 1 / sinFromRads(angleInRads);
    }


    public static double secFromRads(double angleInRads) {
        return 1 / cosFromRads(angleInRads);
    }


    public static double cotFromRads(double angleInRads) {
        return 1 / tanFromRads(angleInRads);
    }


    ////////////
    // Degrees


    ////
    // floats

    public static float toDegs(float angleInRads) {
        return (float) Math.toDegrees(angleInRads);
    }


    public static float sinFromDegs(float angleInDegs) {
        return sinFromRads(toRads(angleInDegs));
    }


    public static float cosFromDegs(float angleInDegs) {
        return cosFromRads(toRads(angleInDegs));
    }


    public static float tanFromDegs(float angleInDegs) {
        return tanFromRads(toRads(angleInDegs));
    }


    public static float cscFromDegs(float angleInDegs) {
        return cscFromRads(toRads(angleInDegs));
    }


    public static float secFromDegs(float angleInDegs) {
        return secFromRads(toRads(angleInDegs));
    }


    public static float cotFromDegs(float angleInDegs) {
        return cotFromRads(toRads(angleInDegs));
    }


    ////
    // doubles


    public static double toDegs(double angleInRads) {
        return Math.toDegrees(angleInRads);
    }


    public static double sinFromDegs(double angleInDegs) {
        return sinFromRads(toRads(angleInDegs));
    }


    public static double cosFromDegs(double angleInDegs) {
        return cosFromRads(toRads(angleInDegs));
    }


    public static double tanFromDegs(double angleInDegs) {
        return tanFromRads(toRads(angleInDegs));
    }


    public static double cscFromDegs(double angleInDegs) {
        return cscFromRads(toRads(angleInDegs));
    }


    public static double secFromDegs(double angleInDegs) {
        return secFromRads(toRads(angleInDegs));
    }


    public static double cotFromDegs(double angleInDegs) {
        return cotFromRads(toRads(angleInDegs));
    }


    public static Vector4f v4(float x, float y, float z, float w) {
        return new Vector4f(x, y, z, w);
    }


    public static Vector3f v3(float x, float y, float z) {
        return new Vector3f(x, y, z);
    }


    public static Vector2f v2(float x, float y) {
        return new Vector2f(x, y);
    }


    public static Vector4f v4(double x, double y, double z, double w) {
        return v4((float) x, (float) y, (float) z, (float) w);
    }


    public static Vector3f v3(double x, double y, double z) {
        return v3((float) x, (float) y, (float) z);
    }


    public static Vector2f v2(double x, double y) {
        return v2((float) x, (float) y);
    }


}
