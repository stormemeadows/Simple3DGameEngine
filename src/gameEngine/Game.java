package gameEngine;


import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import core.display.DisplayManager;
import core.ifaces.ITerrainable;
import core.utils.Camera;
import core.utils.MousePicker;
import core.utils.helpers.H;
import gameEngine.loaders.MasterLoader;
import gameEngine.renderers.MasterRenderer;
import textures.blenders.BasicBlendedTexture;
import visibles.environmentals.EnvironmentalEntity;
import visibles.environmentals.EnvironmentalTexturedModel;
import visibles.guis.GuiElement;
import visibles.lights.Light;
import visibles.players.PlayerEntity;
import visibles.players.PlayerTexturedModel;
import visibles.terrains.Terrain;
import visibles.terrains.TerrainTexture;
import visibles.waters.WaterTile;

/**
 * @author storm
 * 
 *         Sets up the game variables.
 */
class Game {


    PlayerEntity PLAYER;

    EnvironmentalEntity MOVEABLE_LAMP;
    Light               MOVEABLE_LAMP_LIGHT;

    Camera      CAMERA;
    MousePicker MOUSE_PICKER;

    Light              SUN;
    ITerrainable<?, ?> TERRAIN;

    MasterRenderer MASTER_R;

    List<GuiElement>          GUI_ELTS = new ArrayList<GuiElement>();
    List<EnvironmentalEntity> ENV_ENTS = new ArrayList<EnvironmentalEntity>();
    List<Light>               LIGHTS   = new ArrayList<Light>();
    List<ITerrainable<?, ?>>  TERRAINS = new ArrayList<ITerrainable<?, ?>>();
    List<WaterTile>           WATERS   = new ArrayList<WaterTile>();


    /**
     * Order of invocation matters!
     */
    Game() {
        MASTER_R = new MasterRenderer();


        initTerrains(0, 1);
        TERRAIN = H.getFirst(TERRAINS);


        final PlayerTexturedModel person = new PlayerTexturedModel("playerTexture", "person");
        // PLAYER = new PlayerEntity(person, H.v3(153, 5, -274), H.v3(0, 100,
        // 0), 0.6f);
        PLAYER = new PlayerEntity(person, H.v3(153, 5, -274), 0.6f);
        CAMERA = new Camera(PLAYER);
        MOUSE_PICKER = new MousePicker(CAMERA, MASTER_R.getProjectionMatrix(), TERRAIN);


        initWaters();

        final int maxNumEntities = 10;
        final float sizeFactor = 5f;
        initEntities(maxNumEntities, sizeFactor);

        final EnvironmentalTexturedModel lamp = new EnvironmentalTexturedModel("lamp", "lamp");
        addEnvEnt(lamp, H.v3(185, -4.7f, -293));
        addEnvEnt(lamp, H.v3(370, 4.2f, -300));
        MOVEABLE_LAMP = new EnvironmentalEntity(lamp, H.v3(293, -6.8f, -305));
        addEnvEnt(MOVEABLE_LAMP);


        initLights();
        SUN = H.getFirst(LIGHTS);
        MOVEABLE_LAMP_LIGHT = H.getLast(LIGHTS);

        initGuiTextures();
    }


    void cleanUp() {
        MASTER_R.cleanUpShaders();
        MasterLoader.cleanUp();
    }


    void mainLoopLogic() {
        // if (PLAYER.extremeFOVEnabledQ())
        // MASTER_R.setFieldOfViewInDegs(120);

        // randomizeWorld();
        playerAndMouseLogic();
        render();
    }


    void randomizeWorld() {
        // WaterTile w = H.getRandom(WATERS);
        // Light sun = H.getRandom(LIGHTS);
        // ITerrainable<?,?> t = H.getRandom(TERRAINS);

        // EnvironmentalEntity e = H.getRandom(ENV_ENTS);
        // e.increasePosition(H.randTrans(10));
        for (final EnvironmentalEntity e:ENV_ENTS) {
            e.increasePosition(H.rand2DTranslationIn3DSpace(10));
        }
    }


    private void addEnvEnt(EnvironmentalEntity ent) {
        H.add(ENV_ENTS, ent);
    }


    private void addEnvEnt(EnvironmentalTexturedModel m, Vector3f p) {
        addEnvEnt(new EnvironmentalEntity(m, p));
    }


    private void addEnvEnt(EnvironmentalTexturedModel m, Vector3f p,
            float rX, float rY, float rZ, float scale) {
        addEnvEnt(new EnvironmentalEntity(m, p, rX, rY, rZ, scale));
    }


    private void addGui(GuiElement g) {
        // H.pln("Before add: " + GUI_ELTS);
        // H.pln("Adding: " + g);
        H.add(GUI_ELTS, g);
        // H.pln("After add: " + GUI_ELTS);
    }


    private void addGui(String f, Vector2f p, Vector2f s) {
        addGui(new GuiElement(f, p, adjustGuiScales(s.x, s.y)));
    }


    private void addLight(Light l) {
        H.add(LIGHTS, l);
    }


    private void addLight(Vector3f p, Vector3f c) {
        addLight(new Light(p, c));
    }


    private void addLight(Vector3f p, Vector3f c, Vector3f a) {
        addLight(new Light(p, c, a));
    }


    private void addTerrain(int x, int z, BasicBlendedTexture<TerrainTexture> texBlender, String hMap) {
        addTerrain(new Terrain(x, z, texBlender, hMap));
    }


    private void addTerrain(ITerrainable<?, ?> ter) {
        H.add(TERRAINS, ter);
    }


    private void addWater(Vector3f pos) {
        addWater(new WaterTile(pos.x, pos.z, pos.y * 2f));
    }


    private void addWater(WaterTile w) {
        H.add(WATERS, w);
    }


    private Vector2f adjustGuiScales(float x, float y) {
        return H.v2(x, y * DisplayManager.getAspectRatio());
    }


    private void doMousePickerLogic() {
        final Vector3f terPoint = MOUSE_PICKER.getCurrentTerrainPoint();
        if (terPoint != null) {
            Vector3f moveableLampLightPosition;
            moveableLampLightPosition = H.v3(
                    terPoint.x,
                    terPoint.y + 15,
                    terPoint.z);
            MOVEABLE_LAMP.setPosition(terPoint);
            MOVEABLE_LAMP_LIGHT.setPosition(moveableLampLightPosition);
        }
    }


    private void initEntities(int maxNumEntities, float sizeFactor) {
        final EnvironmentalTexturedModel grass = new EnvironmentalTexturedModel("grassTexture", "grassModel");

        final EnvironmentalTexturedModel flower = new EnvironmentalTexturedModel("flower", "grassModel");

        final EnvironmentalTexturedModel fern = new EnvironmentalTexturedModel("fern", "fern");
        fern.getTexture().setNumberOfRows(2).setNumberOfTexturesInTextureImageFile(4);
        // fern.getTexture().setNumberOfRows(2).setUseFakeLighting(true);

        final EnvironmentalTexturedModel bobble = new EnvironmentalTexturedModel("lowPolyTree", "pine");
        bobble.getTexture().setReflectivity(10).setShineDamper(10);
        final EnvironmentalTexturedModel bobble2 = new EnvironmentalTexturedModel("lowPolyTree", "lowPolyTree");

        // EnvironmentalTexturedModel bobble3 = new
        // EnvironmentalTexturedModel("pine", "box");
        // bobble3.getTexture().setReflectivity(20).setShineDamper(1).setHasTransparency(true);

        final EnvironmentalTexturedModel pine = new EnvironmentalTexturedModel("pine", "pine");

        final EnvironmentalTexturedModel tree = new EnvironmentalTexturedModel("tree", "tree");

        final EnvironmentalTexturedModel box = new EnvironmentalTexturedModel("box", "box");
        final EnvironmentalTexturedModel box2 = new EnvironmentalTexturedModel("box", "box");
        box.getTexture().setReflectivity(10).setShineDamper(10);

        final EnvironmentalTexturedModel[] transparentAndFakelyLit = {
                grass, flower
        };

        for (final EnvironmentalTexturedModel t:transparentAndFakelyLit) {
            t.getTexture().setHasTransparency(true);
            t.getTexture().setUseFakeLighting(true);
        }

        // EnvironmentalTexturedModel[] all = {
        // bobble, bobble2,
        // // bobble3,
        // grass, flower, fern, tree, pine, box, box2
        // };


        for (int i = 0; i < maxNumEntities; i++) {

            final float rX = 0;
            final float rY = 0;
            final float rZ = 0;
            float scale = 1;

            if (i % 2 == 0) {
                scale = (1 + 0.2f * H.randfs()) * (sizeFactor + 3);
                addEnvEnt(grass, randPos(), rX, rY, rZ, scale);
            }
            if (i % 13 == 0) {
                scale = (1 + 0.2f * H.randfs()) * (sizeFactor + 3);
                addEnvEnt(flower, randPos(), rX, rY, rZ, scale);
            }
            if (i % 3 == 0) {
                scale = (1 + 0.2f * H.randfs()) * (sizeFactor + 10);
                final int texNum = H.randi(fern.getTexture().getNumberOfTexturesInTextureImageFile());
                fern.getTexture().setIndexOfTextureInTextureImageFile(texNum);
                // H.pln("Fern tex: " + texNum + " : " +
                // fern.getTexture().getIndexOfTextureInTextureImageFile());
                addEnvEnt(fern, randPos(), rX, rY, rZ, scale);
            }
            if (i % 6 == 0) {
                scale = (1 + 0.2f * H.randfs()) * (sizeFactor + 5);
                addEnvEnt(pine, randPos(), rX, rY, rZ, scale);
            }
            if (i % 75 == 0) {
                scale = (1 + 0.2f * H.randfs()) * (sizeFactor + 10);
                addEnvEnt(tree, randPos(), rX, rY, rZ, scale);
            }
            if (i % 77 == 0) {
                scale = (1 + 0.2f * H.randfs()) * (sizeFactor + 5);
                addEnvEnt(bobble, randPos(), rX, rY, rZ, scale);
                addEnvEnt(bobble2, randPos(), rX, rY, rZ, scale);
                // addEnvEnt(bobble3, randPos(), rX, rY, rZ, 4 * scale);
            }
            if (i % 100 == 0) {
                scale = (1 + 0.2f * H.randfs()) * (sizeFactor + 40);
                addEnvEnt(box, randPos(), rX, rY, rZ, scale);
                addEnvEnt(box2, randPos(), rX, rY, rZ, scale);
            }
        }
    }


    private void initGuiTextures() {
        addGui("health", H.v2(-0.85f, 0.90f), H.v2(0.1f, 0.1f));
        addGui("lol", H.v2(0.90f, -0.85f), H.v2(0.05f, 0.05f));
    }


    private void initLights() {
        addLight(H.v3(1000, 1000, 1000), H.v3(1, 1, 1)); // sun

        // light attenuation factors
        final Vector3f lafs = H.v3(0.7f, 0.1f, 0.001f);
        addLight(H.v3(185, 10, -293), H.v3(2, 0, 0), lafs);
        addLight(H.v3(370, 17, -300), H.v3(0, 2, 2), lafs);
        addLight(H.v3(293, 7, -305), H.v3(2, 2, 0), lafs);

    }


    private void initTerrains(int m, int n) {
        // BasicBlendedTexture<TerrainTexture> texBlender = new
        // BasicBlendedTexture<>(
        // "grassy", "dirt", "pinkFlowers", "path", "blendMap");
        final BasicBlendedTexture<TerrainTexture> texBlender = new BasicBlendedTexture<>(
                new TerrainTexture("grassy"),
                new TerrainTexture("dirt"),
                new TerrainTexture("pinkFlowers"),
                new TerrainTexture("path"),
                new TerrainTexture("blendMap"));

        final int posM = Math.abs(m);
        final int posN = Math.abs(n);
        // int negM = (posM > 0 ? -posM : 0);
        // int negN = (posN > 0 ? -posN : 0);
        final int negM = -posM;
        final int negN = -posN;

        for (int i = negM; i <= posM; i++) {
            for (int j = negN; j <= posN; j++) {
                addTerrain(i, j, texBlender, "heightmap");
            }
        }
    }


    private void initWaters() {
        // addWater(randPos(H.getFirst(TERRAINS)));
        addWater(PLAYER.getPosition());
    }


    /**
     * Update MOUSE_PICKER after CAMERA has moved.
     * 
     * @param terrain
     */
    private void playerAndMouseLogic() {
        PLAYER.move(TERRAIN);
        CAMERA.move();

        MOUSE_PICKER.update();
        doMousePickerLogic();
    }


    private Vector3f randPos() {
        return randPos(H.getRandom(TERRAINS));
    }


    private Vector3f randPos(ITerrainable<?, ?> terrain) {
        final float x = H.randf() * 800 - 400;
        final float z = H.randf() * -600;
        final float y = terrain.getHeightOfTerrain(x, z);
        return H.v3(x, y, z);
    }


    private void render() {
        MASTER_R.run(PLAYER, ENV_ENTS, TERRAINS, LIGHTS, WATERS, GUI_ELTS, CAMERA, SUN);
    }

}
