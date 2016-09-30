package settings;


import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import core.utils.Maths;

/**
 * @author storm
 */
public final class GameConstants {


    final public static class LoadingParameters {

        final public static boolean USE_SINGLETON_LOADER = false;
        final public static boolean ALWAYS_CLEAN         = false;
    }


    final public static class Display {

        final public static String TITLE   = "Simple 3D Game Engine";
        final public static int    FPS_CAP = 60;

        // final public static int WIDTH = 1280;
        // final public static int HEIGHT = 780;
        final public static int DIMS_FACTOR = 1;
        final public static int WIDTH       = 1280 / DIMS_FACTOR;
        final public static int HEIGHT      = 780 / DIMS_FACTOR;
    }


    final public static class Folders {

        // TODO use built-in file utils instead of hardcoding '/'
        final public static String DELIMITER = "/";
        final public static String SOURCE    = "src/";
        final public static String RESOURCES = "res/";
        final public static String SHADERS   = "shdrs/";

        final public static String VERTEX_SHADERS   = SHADERS + "vertex/";
        final public static String FRAGMENT_SHADERS = SHADERS + "fragment/";
        final public static String TEXTURES         = RESOURCES;
        final public static String SKYBOX           = TEXTURES + "skybox/";
    }


    final public static class OpenGLVAOParameters {

        final public static String POSITION_NAME = "position";
        final public static int    POSITION_IDX  = 0;
        final public static int    POSITION_DIM  = 3;

        final public static String TEX_COORDS_NAME = "textureCoords";
        final public static int    TEX_COORDS_IDX  = 1;
        final public static int    TEX_COORDS_DIM  = 2;

        final public static String NORMAL_VECTS_NAME = "normal";
        final public static int    NORMAL_VECTS_IDX  = 2;
        final public static int    NORMAL_VECTS_DIM  = 3;
    }


    final public static class MasterRendererParameters {

        final public static float FOV        = 70;
        final public static float NEAR_PLANE = 0.1f;
        final public static float FAR_PLANE  = 1000;

        // Set the clipping distance to an "unfathomable height,"
        // in case disabling the distance doesn't work.
        final static private float   DEFAULT_CLIP_PLANE_HEIGHT = 100000f;
        final public static Vector4f DEFAULT_CLIP_PLANE        = Maths.v4(0, -1, 0, DEFAULT_CLIP_PLANE_HEIGHT);

        final public static float    RED       = 0.5444f;
        final public static float    GREEN     = 0.62f;
        final public static float    BLUE      = 0.69f;
        final public static Vector3f SKY_COLOR = new Vector3f(RED, GREEN, BLUE);
        // sky blue: (1.0f, 0.53f, 0.81f)
    }


    final public static class CameraParameters {

        final public static float ZOOM_LEVEL_MAX = 100f;
        final public static float ZOOM_LEVEL_MIN = 1f;

        final public static float PITCH_SENSITIVITY = 0.1f;
        final public static float ROT_SENSITIVITY   = 0.3f;
        final public static float ZOOM_SENSITIVITY  = 0.1f;
    }


    final public static class PhysicalParameters {

        final public static float GRAVITY = -50; // units/sec^2
    }


    final public static class PlayerParameters {

        final public static float BOUNCE_ELASTICITY_COEF = -0.3f; //
        final public static float RUN_SPEED              = 100;   // units/sec
        final public static float TURN_SPEED             = 200;   // degrees/sec
        final public static float JUMP_POWER             = 50;    // units/sec^2
        final public static float IN_AIR_JUMP_POWER      = 10;    // units/sec^2
    }


    final public static class TextureParameters {

        final public static String TEX_FILE_EXT  = ".png";
        final public static String TEX_FILE_TYPE = "PNG";
    }


    final public static class LightingParameters {

        final public static int MAX_NUM_CONCURRENT_LIGHTS = 4;
    }


    final public static class MousePickerParameters {

        final public static int   RECURSION_COUNT = 200;
        final public static float RAY_RANGE       = 600;
    }


    final public static class TerrainParameters {

        final public static float SIZE             = 800;
        final public static float MAX_HEIGHT       = 80;
        final public static float MAX_PIXEL_COLOUR = 256 * 256 * 256;

        final public static String VERTEX_FILE   = Folders.VERTEX_SHADERS + "terrain.vs.txt";
        final public static String FRAGMENT_FILE = Folders.FRAGMENT_SHADERS + "terrain.fs.txt";
    }


    final public static class EnvironmentalParameters {

        final public static String VERTEX_FILE   = Folders.VERTEX_SHADERS + "environmental.vs.txt";
        final public static String FRAGMENT_FILE = Folders.FRAGMENT_SHADERS + "environmental.fs.txt";
    }


    final public static class GuiParameters {

        final public static String VERTEX_FILE   = Folders.VERTEX_SHADERS + "gui.vs.txt";
        final public static String FRAGMENT_FILE = Folders.FRAGMENT_SHADERS + "gui.fs.txt";

        final public static float[] FULL_SCREEN_2D_VERTS = {
                -1, +1, // top left corner of screen
                -1, -1, // bottom left
                +1, +1, // top right
                +1, -1 // bottom right
        };
    }


    final public static class WaterParameters {

        final public static class Buffers {

            final public static int REFLECTION_WIDTH  = 320;
            final public static int REFLECTION_HEIGHT = 180;

            final public static int REFRACTION_WIDTH  = Display.WIDTH;  // 1280;
            final public static int REFRACTION_HEIGHT = Display.HEIGHT; // 720;
        }

        final public static float CLIP_PLANE_ERROR_ROOM = 1f;

        final public static float TILE_SIZE = 60f;
        // final public static float TILE_SIZE = 120f;

        final public static String DUDV_MAP_TEXTURE_FILE   = "waterDUDV";
        final public static String NORMAL_MAP_TEXTURE_FILE = "matchingNormalMap";
        final public static float  WAVE_SPEED              = 0.04f;

        final public static String VERTEX_FILE   = Folders.VERTEX_SHADERS + "water.vs.txt";
        final public static String FRAGMENT_FILE = Folders.FRAGMENT_SHADERS + "water.fs.txt";

        // Just x and z vertex coordinates here.
        // the y coord is set to 0 in vertex shader.
        final public static float[] VERTICES = {
                -1, -1,
                -1, +1,
                +1, -1,
                +1, -1,
                -1, +1,
                +1, +1
        };
    }

    final public static class SkyboxParameters {

        final public static String VERTEX_FILE   = Folders.VERTEX_SHADERS + "skybox.vs.txt";
        final public static String FRAGMENT_FILE = Folders.FRAGMENT_SHADERS + "skybox.fs.txt";

        final public static float ROTATE_SPEED = 1.0f; // degrees per sec

        final public static float SIZE = 500f;

        // final public static String SKYBOXES_FOLDER = "skyboxes/";
        // final public static String SKYBOX_NAME = "clouds/";
        // final public static String SKYBOX_NAME = "clouds/";
        // final public static String SKYBOX_2_NAME = "night_sky/";
        // final public static String SKYBOX_NAME = "darkskies/";
        // final public static String SKYBOX_TEX_EXT = ".png";
        // final public static String SKYBOX_TEX_EXT = ".tga";

        public static String[] TEXTURE_FILES = {
                "right", "left", "top", "bottom", "back", "front"
        };

        public static String[] TEXTURE2_FILES = {
                "right2", "left2", "top2", "bottom2", "back2", "front2"
        };

        final public static float[] VERTICES = {
                -SIZE, +SIZE, -SIZE,
                -SIZE, -SIZE, -SIZE,
                +SIZE, -SIZE, -SIZE,
                +SIZE, -SIZE, -SIZE,
                +SIZE, +SIZE, -SIZE,
                -SIZE, +SIZE, -SIZE,

                -SIZE, -SIZE, +SIZE,
                -SIZE, -SIZE, -SIZE,
                -SIZE, +SIZE, -SIZE,
                -SIZE, +SIZE, -SIZE,
                -SIZE, +SIZE, +SIZE,
                -SIZE, -SIZE, +SIZE,

                +SIZE, -SIZE, -SIZE,
                +SIZE, -SIZE, +SIZE,
                +SIZE, +SIZE, +SIZE,
                +SIZE, +SIZE, +SIZE,
                +SIZE, +SIZE, -SIZE,
                +SIZE, -SIZE, -SIZE,

                -SIZE, -SIZE, +SIZE,
                -SIZE, +SIZE, +SIZE,
                +SIZE, +SIZE, +SIZE,
                +SIZE, +SIZE, +SIZE,
                +SIZE, -SIZE, +SIZE,
                -SIZE, -SIZE, +SIZE,

                -SIZE, +SIZE, -SIZE,
                +SIZE, +SIZE, -SIZE,
                +SIZE, +SIZE, +SIZE,
                +SIZE, +SIZE, +SIZE,
                -SIZE, +SIZE, +SIZE,
                -SIZE, +SIZE, -SIZE,

                -SIZE, -SIZE, -SIZE,
                -SIZE, -SIZE, +SIZE,
                +SIZE, -SIZE, -SIZE,
                +SIZE, -SIZE, -SIZE,
                -SIZE, -SIZE, +SIZE,
                +SIZE, -SIZE, +SIZE
        };
    }

}
