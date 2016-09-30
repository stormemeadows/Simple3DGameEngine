package core.utils.helpers;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import core.utils.Maths;

/**
 * @author storm
 *
 */
public final class H {

    public static Random RANDOM = new Random();


    private H() {}


    public static <T> boolean nullQ(T obj) {
        return obj == null;
    }


    public static <T> T getLast(List<T> list) {
        return list.get(list.size() - 1);
    }


    public static <T> T getFirst(List<T> list) {
        return list.get(0);
    }


    public static <T> T getRandom(List<T> list) {
        return list.get(randi(list.size() - 1));
    }


    @SafeVarargs
    public static <T> void add(List<T> list, T... items) {
        for (final T item:items)
            list.add(item);
    }


    public static Vector4f v4(float x, float y, float z, float w) {
        return Maths.v4(x, y, z, w);
    }


    public static Vector3f v3(float x, float y, float z) {
        return Maths.v3(x, y, z);
    }


    public static Vector2f v2(float x, float y) {
        return Maths.v2(x, y);
    }


    public static float randf() {
        return RANDOM.nextFloat();
    }


    public static int randi(int max) {
        return RANDOM.nextInt(max);
    }


    public static int randSgn() {
        return RANDOM.nextBoolean() ? 1 : -1;
    }


    public static float randfs() {
        return randf() * randSgn();
    }


    public static float randis(int maxAbsVal) {
        return randi(maxAbsVal) * randSgn();
    }


    public static Vector3f rand2DTranslationIn3DSpace(float scalar) {
        return (Vector3f) H.v3(randfs(), 0, randfs()).scale(scalar);
    }


    public static boolean outOfBoundsQ(
            int lBound, boolean lClosed,
            int rBound, boolean rClosed,
            int... eltsToCheck) {

        // return on first success
        for (int i = 0; i < eltsToCheck.length; i++)
            if (outOfBoundsQ(lBound, lClosed, rBound, rClosed, eltsToCheck[i]))
                return true;

        return false;
    }


    public static <T extends Comparable<T>> boolean outOfBoundsQ(
            T lBound, boolean lClosed,
            T rBound, boolean rClosed,
            List<T> eltsToCheck) {

        // return on first success
        for (final T elt:eltsToCheck)
            if (outOfBoundsQ(lBound, lClosed, rBound, rClosed, elt))
                return true;

        return false;
    }


    public static <T extends Comparable<T>> boolean outOfBoundsQ(
            T lBound, boolean lClosed,
            T rBound, boolean rClosed,
            T eltToCheck) {

        final int lTest = eltToCheck.compareTo(lBound);
        final int rTest = rBound.compareTo(eltToCheck);

        boolean retVal;
        if (lClosed) { // [
            if (rClosed) { // []
                retVal = lTest >= 0 || rTest >= 0;
            } else { // [)
                retVal = lTest <= 0 || rTest > 0;
            }
        } else { // (
            if (rClosed) { // (]
                retVal = lTest < 0 || rTest >= 0;
            } else { // ()
                retVal = lTest < 0 || rTest > 0;
            }
        }
        return retVal;
    }


    /**
     * Tests pln(T...);
     */
    public static void plnTest() {
        final List<List<Integer>> list = new ArrayList<>();
        final ArrayList<Integer> lNull = null;
        final ArrayList<Integer> lEmpty = new ArrayList<>();
        final ArrayList<Integer> l1 = new ArrayList<>();
        final ArrayList<Integer> l2 = new ArrayList<>();
        l1.add(10);
        l2.add(20);
        l2.add(21);
        list.add(lNull);
        list.add(lEmpty);
        list.add(l1);
        list.add(l2);
        pln(list);
    }


    /**
     * TODO replace "obj.getClass().isArray()" with something
     * that checks whether the class implements the List interface.
     * 
     * Prints out a variable number of Objects,
     * each to a new line.
     * 
     * Recurs on elements that are themselves arrays.
     * 
     * @param objs
     *            A variable number of Objects
     */
    @SafeVarargs
    public static <T> void pln(T... objs) {
        for (final T obj:objs) {
            if (!nullQ(obj) && obj.getClass().isArray())
                pln("(nested:" + obj + ")");
            else
                System.out.println(obj);
        }
    }


    /**
     * TODO replace "obj.getClass().isArray()" with something
     * that checks whether the class implements the List interface.
     * 
     * Uses System.out.print() to print a variable number of Objects, separating
     * them with the delimiter.
     * 
     * Recurs on elements that are themselves arrays.
     * 
     * @param delimiter
     *            A string to delineate the output.
     * @param objs
     *            A variable number of Objects
     */
    @SafeVarargs
    public static <T> void pWithDelimiter(String delimiter, T... objs) {
        for (int i = 0; i < objs.length - 1; i++) {
            final T obj = objs[i];
            if (!nullQ(obj) && obj.getClass().isArray())
                pWithDelimiter(delimiter, "(nested:" + obj + ")");
            else
                System.out.print(obj + delimiter);
        }

        final T last = last(objs);
        if (last != null && last.getClass().isArray())
            pWithDelimiter(delimiter, "(nested:" + last + ")");
        else
            System.out.println(last);
    }


    /**
     * Uses System.out.print() to print a variable number of Objects,
     * separating them with a comma and space.
     * 
     * @see p(String, Objects...)
     * @param objs
     */
    @SafeVarargs
    public static <T> void p(T... objs) {
        pWithDelimiter(", ", objs);
    }


    /**
     * Prints each separated by a comma and space, and returns the array of all.
     * 
     * @param objs
     */
    @SafeVarargs
    public static <T> T[] pAndReturnAll(T... objs) {
        p(objs);
        return objs;
    }


    /**
     * Prints each to a new line, then returns the array of all.
     * 
     * @param objs
     */
    @SafeVarargs
    public static <T> T[] plnAndReturnAll(T... objs) {
        pln(objs);
        return objs;
    }


    /**
     * Prints each separated by a comma and space, and returns the last.
     * 
     * @param objs
     */
    @SafeVarargs
    public static <T> T pAndReturnLast(T... objs) {
        p(objs);
        return last(objs);
    }


    /**
     * Prints each to a new line, then returns the last.
     * 
     * @param objs
     */
    @SafeVarargs
    public static <T> T plnAndReturnLast(T... objs) {
        pln(objs);
        return last(objs);
    }


    @SafeVarargs
    public static <T> T last(T... objs) {
        return objs[objs.length - 1];
    }

    // /**
    // * @overload Uses System.out.print() to print a variable number of
    // Objects,
    // * separating them with a new line.
    // * @see p(String, Objects...)
    // * @param objs
    // * A variable number of Objects
    // */
    // public static void pln(Object... objs) {
    // for (int i = 0; i < objs.length; i++)
    // System.out.println(objs[i]);
    // }
    //
    //
    // /**
    // * Uses System.out.print() to print a variable number of Objects,
    // separating
    // * them with the delimiter.
    // *
    // * @param delimiter
    // * A string to delineate the output.
    // * @param objs
    // * A variable number of Objects
    // */
    // public static void pWithDelimiter(String delimiter, Object... objs) {
    // for (int i = 0; i < objs.length - 1; i++)
    // System.out.print(objs[i] + delimiter);
    // System.out.print(last(objs));
    // }
    //
    //
    // /**
    // * Uses System.out.print() to print a variable number of Objects,
    // * separating them with a comma and space.
    // *
    // * @see p(String, Objects...)
    // * @param objs
    // */
    // public static void p(Object... objs) {
    // pWithDelimiter(", ", objs);
    // }
    //
    //
    // /**
    // * Prints each separated by a comma and space, and returns the array of
    // all.
    // *
    // * @param objs
    // */
    // public static Object[] pAndReturnAll(Object... objs) {
    // p(objs);
    // return objs;
    // }
    //
    //
    // /**
    // * Prints each to a new line, then returns the array of all.
    // *
    // * @param objs
    // */
    // public static Object[] plnAndReturnAll(Object... objs) {
    // pln(objs);
    // return objs;
    // }
    //
    //
    // /**
    // * Prints each separated by a comma and space, and returns the last.
    // *
    // * @param objs
    // */
    // public static Object pAndReturnLast(Object... objs) {
    // p(objs);
    // return last(objs);
    // }
    //
    //
    // /**
    // * Prints each to a new line, then returns the last.
    // *
    // * @param objs
    // */
    // public static Object plnAndReturnLast(Object... objs) {
    // pln(objs);
    // return last(objs);
    // }
    //
    //
    // private static Object last(Object... objs) {
    // return objs[objs.length - 1];
    // }
    //
    //
    // /////////////////////////////
    // ///// Numbers specifically.
    //
    // @SafeVarargs
    // public static <N extends Number> void pln(N... nums) {
    // for (int i = 0; i < nums.length; i++)
    // System.out.println(nums[i]);
    // }
    //
    //
    // @SafeVarargs
    // public static <N extends Number> void pWithDelimiter(String delimiter,
    // N... nums) {
    // for (int i = 0; i < nums.length - 1; i++)
    // System.out.print(nums[i] + delimiter);
    // System.out.print(last(nums));
    // }
    //
    //
    // @SafeVarargs
    // public static <N extends Number> void p(N... nums) {
    // pWithDelimiter(", ", nums);
    // }
    //
    //
    // @SafeVarargs
    // public static <N extends Number> N pAndReturnLast(N... nums) {
    // p(nums);
    // return last(nums);
    // }
    //
    //
    // @SafeVarargs
    // public static <N extends Number> N plnAndReturnLast(N... nums) {
    // pln(nums);
    // // return nums[nums.length - 1];
    // return last(nums);
    // }
    //
    //
    // @SafeVarargs
    // private static <N extends Number> N last(N... nums) {
    // return nums[nums.length - 1];
    // }
    //
    //
}

