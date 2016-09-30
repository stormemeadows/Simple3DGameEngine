package gameEngine.renderers;


import static settings.GameConstants.MasterRendererParameters.DEFAULT_CLIP_PLANE;
import static settings.GameConstants.MasterRendererParameters.SKY_COLOR;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector4f;

import core.ifaces.ITerrainable;
import core.utils.Camera;
import core.utils.containers.LightAffectedTexturedModelToListOfEntitiesHashMap;
import core.utils.helpers.H;
import gameEngine.shaders.EnvironmentalShader;
import gameEngine.shaders.GuiShader;
import gameEngine.shaders.PlayerShader;
import gameEngine.shaders.SkyboxShader;
import gameEngine.shaders.TerrainShader;
import gameEngine.shaders.WaterShader;
import visibles.environmentals.EnvironmentalEntity;
import visibles.environmentals.EnvironmentalTexturedModel;
import visibles.guis.GuiElement;
import visibles.lights.Light;
import visibles.players.PlayerEntity;
import visibles.players.PlayerTexturedModel;
import visibles.waters.WaterTile;

/**
 * @author storm
 * 
 */
public class MasterRenderer
        extends AbstractMasterRenderer {

    private final EnvironmentalShader environmentalShader;
    private final PlayerShader        playerShader;
    private final TerrainShader       terrainShader;
    private final WaterShader         waterShader;
    private final SkyboxShader        skyboxShader;
    private final GuiShader           guiShader;

    private final EnvironmentalRenderer environmentalRenderer;
    private final PlayerRenderer        playerRenderer;
    private final TerrainRenderer       terrainRenderer;
    private final WaterRenderer         waterRenderer;
    private final SkyboxRenderer        skyboxRenderer;
    private final GuiRenderer           guiRenderer;

    private final LightAffectedTexturedModelToListOfEntitiesHashMap<EnvironmentalTexturedModel> environmentalsToRender;
    private final LightAffectedTexturedModelToListOfEntitiesHashMap<PlayerTexturedModel>        playersToRender;

    private final List<ITerrainable<?, ?>> terrainsToRender;
    private final List<WaterTile>          watersToRender;
    private final List<GuiElement>         guisToRender;


    // renderables without their own shaders and renderers
    private final List<Light> lightsToRender;
    private Vector4f          clipPlaneToRender;
    private Light             sunToRender;
    private Camera            cameraToRender;


    public MasterRenderer() {
        super(); // creates the projection matrix
        enableCulling();

        // init rendererables with their own shaders and renderers
        environmentalsToRender = new LightAffectedTexturedModelToListOfEntitiesHashMap<>();
        environmentalShader = new EnvironmentalShader();
        environmentalRenderer = new EnvironmentalRenderer(environmentalShader, projectionMatrix);

        playersToRender = new LightAffectedTexturedModelToListOfEntitiesHashMap<>();
        playerShader = new PlayerShader();
        playerRenderer = new PlayerRenderer(playerShader, projectionMatrix);

        terrainsToRender = new ArrayList<>();
        terrainShader = new TerrainShader();
        terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);

        watersToRender = new ArrayList<>();
        waterShader = new WaterShader();
        waterRenderer = new WaterRenderer(waterShader, projectionMatrix);

        // just one sky - no need for a collection of skyboxes
        skyboxShader = new SkyboxShader();
        skyboxRenderer = new SkyboxRenderer(skyboxShader, projectionMatrix);

        guisToRender = new ArrayList<>();
        guiShader = new GuiShader();
        guiRenderer = new GuiRenderer(guiShader);


        // init renderables without their own shaders and renderers
        lightsToRender = new ArrayList<>();
        clipPlaneToRender = null;
        sunToRender = null;
        cameraToRender = null;
    }


    /**
     * Render the scene 3 times:
     *
     * 1. To the reflection frame buffer,
     * ie everything above the watersToRender's surface.
     *
     * 2. To the refraction frame buffer,
     * ie everything below the watersToRender's surface.
     *
     * 3. To the screen.
     * Note that this doesn't always work:
     * 'GL11.glDisable(GL30.GL_CLIP_DISTANCE0)'
     * So use kludgy workaround instead, rendering with the
     * clip plane at an 'unfathomableHeight.'
     * 
     * @param player
     * @param environmentals
     * @param terrains
     * @param lights
     * @param waters
     * @param guis
     * @param camera
     * @param sun
     */
    public void run(PlayerEntity player, List<EnvironmentalEntity> environmentals,
            List<ITerrainable<?, ?>> terrains, List<Light> lights, List<WaterTile> waters,
            List<GuiElement> guis, Camera camera, Light sun) {
        processAllRenderableCollections(player, environmentals,
                terrains, lights, waters, guis, camera, sun);
        if (player.extremeFOVEnabledQ()) {
            setFieldOfViewInDegs(120);
        }
        partialRenderToWaterFrameBuffers(); // 1 and 2
        fullRenderToDefaultFrameBuffer(); // 3
        clearAllRenderableCollections();
    }


    /**
     * Sets all renderable data and ensures no duplicates.
     * 
     * @param player
     * @param environmentals
     * @param terrains
     * @param lights
     * @param waters
     * @param guis
     * @param camera
     * @param sun
     */
    private void processAllRenderableCollections(PlayerEntity player,
            List<EnvironmentalEntity> environmentals, List<ITerrainable<?, ?>> terrains,
            List<Light> lights, List<WaterTile> waters, List<GuiElement> guis, Camera camera, Light sun) {
        processPlayer(player);
        processAllRenderableCollectionsExceptPlayers(environmentals, terrains, lights, waters, guis, camera, sun);
    }


    /**
     * @param environmentals
     * @param terrains
     * @param lights
     * @param waters
     * @param guis
     * @param camera
     * @param sun
     */
    private void processAllRenderableCollectionsExceptPlayers(List<EnvironmentalEntity> environmentals,
            List<ITerrainable<?, ?>> terrains, List<Light> lights, List<WaterTile> waters,
            List<GuiElement> guis, Camera camera, Light sun) {
        for (final EnvironmentalEntity environmental:environmentals)
            processEnvironmental(environmental);
        for (final ITerrainable<?, ?> terrain:terrains)
            processTerrain(terrain);
        for (final WaterTile wtr:waters)
            processWater(wtr);
        for (final GuiElement gui:guis)
            processGui(gui);
        for (final Light light:lights)
            processLight(light);
        processCamera(camera);
        processSun(sun);
    }


    /**
     * 1. and 2. Render to the reflection and refraction frame buffers.
     * Enables the clipping distance.
     */
    private void partialRenderToWaterFrameBuffers() {
        MasterRenderer.enableClippingDistance();
        // partialRenderToWaterReflectionFrameBufferSlower();
        // partialRenderToWaterReflectionFrameBufferSlower2();
        partialRenderToWaterReflectionFrameBufferFaster();
        partialRenderToWaterRefractionFrameBuffer();
    }


    /**
     * 1. Render scene to the reflection frame buffer.
     * Binds the reflection frame buffer.
     */
    @SuppressWarnings("unused")
    private void partialRenderToWaterReflectionFrameBufferSlower() {
        waterRenderer.bindReflectionFrameBuffer();

        final Camera cameraCopy = new Camera(cameraToRender);

        float vertDistBetweenCamAndWater;
        for (final WaterTile water:watersToRender) {
            clipPlaneToRender = waterRenderer.createReflectClipPlane(water);

            vertDistBetweenCamAndWater = Math.abs(2 * (water.getHeight() - cameraToRender.getY()));
            cameraCopy.increaseY(vertDistBetweenCamAndWater);
            cameraCopy.invertPitch();

            partialRenderToCurrentBuffer(cameraCopy);
        }
    }


    /**
     * 1. Render scene to the reflection frame buffer.
     * Binds the reflection frame buffer.
     */
    @SuppressWarnings("unused")
    private void partialRenderToWaterReflectionFrameBufferSlower2() {
        waterRenderer.bindReflectionFrameBuffer();

        final Camera originalCamera = new Camera(cameraToRender);
        final Camera cameraCopy = new Camera(cameraToRender);

        float vertDistBetweenCamAndWater;
        for (final WaterTile water:watersToRender) {
            clipPlaneToRender = waterRenderer.createReflectClipPlane(water);

            vertDistBetweenCamAndWater = Math.abs(2 * (water.getHeight() - cameraToRender.getY()));
            cameraCopy.increaseY(vertDistBetweenCamAndWater);
            cameraCopy.invertPitch();

            cameraToRender = cameraCopy;
            partialRenderToCurrentBuffer();
            cameraToRender = originalCamera;
        }
    }


    /**
     * 1. Render scene to the reflection frame buffer.
     * Binds the reflection frame buffer.
     */
    private void partialRenderToWaterReflectionFrameBufferFaster() {
        waterRenderer.bindReflectionFrameBuffer();

        float vertDistBetweenCamAndWater;
        for (final WaterTile water:watersToRender) {
            clipPlaneToRender = waterRenderer.createReflectClipPlane(water);

            vertDistBetweenCamAndWater = Math.abs(2 * (water.getHeight() - cameraToRender.getY()));

            cameraToRender.increaseY(vertDistBetweenCamAndWater);
            cameraToRender.invertPitch();
            partialRenderToCurrentBuffer();
            cameraToRender.invertPitch();
            cameraToRender.increaseY(-vertDistBetweenCamAndWater);
        }
    }


    /**
     * 2. Render scene to the refraction frame buffer.
     * Binds the refraction frame buffer.
     */
    private void partialRenderToWaterRefractionFrameBuffer() {
        waterRenderer.bindRefractionFrameBuffer();

        for (final WaterTile water:watersToRender) {
            clipPlaneToRender = waterRenderer.createRefractClipPlane(water);
            partialRenderToCurrentBuffer();
        }
    }


    /**
     * 3. Render scene, watersToRender, and guisToRender to the screen.
     * 
     * Disables the clipping distance.
     * Binds the default frame buffer.
     */
    private void fullRenderToDefaultFrameBuffer() {
        MasterRenderer.disableClippingDistance(); // doesn't always work!
        MasterRenderer.bindDefaultFrameBuffer();

        clipPlaneToRender = DEFAULT_CLIP_PLANE;
        partialRenderToCurrentBuffer();

        renderWaters();
        renderGuis();
    }


    /**
     * TODO figure out if using this is truly more efficient.
     * 
     * Temporarily sets the passed Camera to be rendered, then
     * switches the Camera back to what it was before rendering.
     * 
     * @param camera
     */
    protected void partialRenderToCurrentBuffer(Camera camera) {
        final Camera originalCamera = cameraToRender;
        // final Camera originalCamera = new Camera(cameraToRender);
        cameraToRender = camera;
        partialRenderToCurrentBuffer();
        cameraToRender = originalCamera;
    }


    /**
     * Renders everything except for:
     * - the watersToRender, and
     * - the guisToRender.
     */
    @Override
    protected void partialRender() {
        renderTerrains();
        renderEnvironmentals();
        renderPlayers();
        renderSkybox();
    }


    /**
     * Clear all of the renderable data.
     */
    @Override
    protected void clearAllRenderableCollections() {
        guisToRender.clear();
        watersToRender.clear();
        terrainsToRender.clear();
        playersToRender.clear();
        environmentalsToRender.clear();

        // those without shaders and renderers
        lightsToRender.clear();
        cameraToRender = null;
        clipPlaneToRender = null;
        sunToRender = null;
    }


    /**
     * Clean up them shaders.
     */
    @Override
    public void cleanUpShaders() {
        guiShader.cleanUp();
        waterShader.cleanUp();
        terrainShader.cleanUp();
        playerShader.cleanUp();
        environmentalShader.cleanUp();
    }


    @Override
    protected void finalize() throws Throwable {
        clearAllRenderableCollections();
        cleanUpShaders();
        super.finalize();
    }


    //////////////////////////////
    // DON'T TOUCH ANYTHING BELOW


    private void renderGuis() {
        guiRenderer.run(guisToRender);
    }


    private void renderSkybox() {
        skyboxRenderer.run(cameraToRender, SKY_COLOR);
    }


    private void renderWaters() {
        waterRenderer.run(watersToRender, cameraToRender, sunToRender);
    }


    private void renderTerrains() {
        terrainRenderer.run(terrainsToRender, lightsToRender, cameraToRender,
                clipPlaneToRender, SKY_COLOR);
    }


    private void renderPlayers() {
        playerRenderer.run(playersToRender, lightsToRender, cameraToRender,
                clipPlaneToRender, SKY_COLOR);
    }


    private void renderEnvironmentals() {
        environmentalRenderer.run(environmentalsToRender, lightsToRender, cameraToRender,
                clipPlaneToRender, SKY_COLOR);
    }


    private void processCamera(Camera camera) {
        cameraToRender = camera;
    }


    private void processSun(Light sun) {
        sunToRender = sun;
    }


    private void processLight(Light light) {
        addRenderableToList(lightsToRender, light);
    }


    private void processGui(GuiElement gui) {
        addRenderableToList(guisToRender, gui);
    }


    private void processWater(WaterTile water) {
        addRenderableToList(watersToRender, water);
    }


    private void processTerrain(ITerrainable<?, ?> terrain) {
        addRenderableToList(terrainsToRender, terrain);
    }


    private void processEnvironmental(EnvironmentalEntity environmental) {
        addRenderableToMap(environmentalsToRender, environmental);
    }


    private void processPlayer(PlayerEntity player) {
        // if (player.extremeFOVEnabledQ()) {
        // setFieldOfViewInDegs(170);
        // } else {
        // setFieldOfViewInDegs(MasterRendererParameters.FOV);
        // }
        addRenderableToMap(playersToRender, player);
    }


    /////////////////
    // For debugging.

    protected <T> void printBeforeState(List<T> memberVar, List<T> passedVar) {
        H.pln("Member var state BEFORE:", "\t" + memberVar);
        H.pln("Passed var state BEFORE:", "\t" + passedVar);
    }


    protected <T> void printAfterState(List<T> memberVar, List<T> passedVar) {
        H.pln("Member var state AFTER:", "\t" + memberVar);
        H.pln("Passed var state AFTER:", "\t" + passedVar);
    }

}


// public class MasterRenderer
// extends AbstractMasterRenderer {
//
// // Matrix4f projectionMatrix;
//
// private final EnvironmentalShader environmentalShader;
// private final PlayerShader playerShader;
// private final TerrainShader terrainShader;
// private final WaterShader waterShader;
// private final SkyboxShader skyboxShader;
// private final GuiShader guiShader;
//
// private final EnvironmentalRenderer environmentalRenderer;
// private final PlayerRenderer playerRenderer;
// private final TerrainRenderer terrainRenderer;
// private final WaterRenderer waterRenderer;
// private final SkyboxRenderer skyboxRenderer;
// private final GuiRenderer guiRenderer;
//
// private final
// LightAffectedTexturedModelToListOfEntitiesHashMap<EnvironmentalTexturedModel>
// environmentals;
// private final
// LightAffectedTexturedModelToListOfEntitiesHashMap<PlayerTexturedModel>
// players;
// // private final Map<EnvironmentalTexturedModel,
// // List<EnvironmentalEntity>> environmentals;
// // private final Map<PlayerTexturedModel, List<Entity<PlayerTexturedModel>>>
// // players;
//
// private final List<ITerrainable<?, ?>> terrains;
// private final List<WaterTile> waters;
// private final List<GuiElement> guis;
//
//
// public MasterRenderer() {
// super(); // creates the projection matrix
// enableCulling();
//
// environmentals = new LightAffectedTexturedModelToListOfEntitiesHashMap<>();
// // environmentals = new HashMap<>();
// environmentalShader = new EnvironmentalShader();
// environmentalRenderer = new EnvironmentalRenderer(environmentalShader,
// projectionMatrix);
//
// players = new LightAffectedTexturedModelToListOfEntitiesHashMap<>();
// // players = new HashMap<>();
// playerShader = new PlayerShader();
// playerRenderer = new PlayerRenderer(playerShader, projectionMatrix);
//
// terrains = new ArrayList<>();
// terrainShader = new TerrainShader();
// terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
//
// waters = new ArrayList<>();
// waterShader = new WaterShader();
// waterRenderer = new WaterRenderer(waterShader, projectionMatrix);
//
// // just one sky - no need for a collection
// skyboxShader = new SkyboxShader();
// skyboxRenderer = new SkyboxRenderer(skyboxShader, projectionMatrix);
//
// guis = new ArrayList<>();
// guiShader = new GuiShader();
// guiRenderer = new GuiRenderer(guiShader);
// }
//
//
// public void runMultiplayer(List<PlayerEntity> players,
// List<EnvironmentalEntity> environmentals,
// List<ITerrainable<?, ?>> terrains, List<Light> lights, List<WaterTile>
// waters,
// List<GuiElement> guis, Camera camera, Light sun) {
// processAllRenderableCollectionsMuliplayer(players, environmentals,
// terrains, lights, waters, guis, camera, sun);
// partialRenderToWaterFrameBuffers(); // 1 and 2
// fullRenderToDefaultFrameBuffer(); // 3
// clearAllRenderableCollections();
// }
// private void processAllRenderableCollectionsMuliplayer(List<PlayerEntity>
// players,
// List<EnvironmentalEntity> environmentals, List<ITerrainable<?, ?>>
// terrains,
// List<Light> lights, List<WaterTile> waters, List<GuiElement> guis, Camera
// camera, Light sun) {
// for (final PlayerEntity player:players)
// processPlayer(player);
// processAllRenderableCollectionsExceptPlayers(environmentals, terrains,
// lights, waters, guis, camera, sun);
// }
//
//
// /**
// * Render the scene 3 times:
// *
// * 1. To the reflection frame buffer,
// * ie everything above the waters's surface.
// *
// * 2. To the refraction frame buffer,
// * ie everything below the waters's surface.
// *
// * 3. To the screen.
// * Note that this doesn't always work:
// * 'GL11.glDisable(GL30.GL_CLIP_DISTANCE0)'
// * So use kludgy workaround instead, rendering with the
// * clip plane at an 'unfathomableHeight.'
// */
// public void run(PlayerEntity plyr, List<EnvironmentalEntity> envrnmntls,
// List<ITerrainable<?, ?>> ters, List<Light> lhts, List<WaterTile> wtrs,
// List<GuiElement> guiElts, Camera cam, Light sun) {
// // printWatersStateBefore(wtrs);
// // printGuisStateBefore(guiElts);
//
// processAllRenderableCollections(plyr, envrnmntls, ters, wtrs, guiElts);
//
// partialRenderToWaterFrameBuffers(plyr, lhts, wtrs, cam); // 1 and 2
// fullRenderToDefaultFrameBuffer(plyr, lhts, wtrs, cam, sun); // 3
//
// clearAllRenderableCollections();
// // printGuisStateAfter(guiElts);
// // printWatersStateAfter(wtrs);
// }
//
//
// /**
// * @param plyr
// * @param lhts
// * @param wtrs
// * @param cam
// * @param sun
// */
// private void fullRenderToDefaultFrameBuffer(
// PlayerEntity plyr, List<Light> lhts, List<WaterTile> wtrs, Camera cam, Light
// sun) {
// MasterRenderer.disableClippingDistance(); // doesn't always work!
// MasterRenderer.bindDefaultFrameBuffer();
// // final Vector4f unfathomableHeight = Maths.v4(0, -1, 0, 100000);
// partialRenderToCurrentBuffer(plyr, lhts, cam, DEFAULT_CLIP_PLANE);
// renderWaters(wtrs, cam, sun);
// renderGuis();
// }
//
// // unfathomableHeight
// final private static Vector4f DEFAULT_CLIP_PLANE = Maths.v4(0, -1, 0,
// 100000);
//
//
// private void partialRenderToWaterFrameBuffers(
// PlayerEntity plyr, List<Light> lhts, List<WaterTile> wtrs, Camera cam) {
// MasterRenderer.enableClippingDistance();
// partialRenderToWaterReflectionFrameBuffer(plyr, lhts, wtrs, cam);
// partialRenderToWaterRefractionFrameBuffer(plyr, lhts, wtrs, cam);
// }
//
//
// private void partialRenderToWaterReflectionFrameBuffer(
// PlayerEntity plyr, List<Light> lhts, List<WaterTile> wtrs, Camera cam) {
// waterRenderer.bindReflectionFrameBuffer();
// final Camera cam1 = new Camera(cam);
// for (final WaterTile water:wtrs) {
// final Vector4f clipPlane = waterRenderer.createReflectClipPlane(water);
// final float distance = 2 * (cam1.getPosition().y - water.getHeight());
// cam1.getPosition().y -= distance;
// cam1.invertPitch();
// partialRenderToCurrentBuffer(plyr, lhts, cam1, clipPlane);
// }
// }
//
//
// private void partialRenderToWaterRefractionFrameBuffer(
// PlayerEntity plyr, List<Light> lhts, List<WaterTile> wtrs, Camera cam) {
// waterRenderer.bindRefractionFrameBuffer();
// for (final WaterTile water:wtrs) {
// final Vector4f clipPlane = waterRenderer.createReflectClipPlane(water);
// partialRenderToCurrentBuffer(plyr, lhts, cam, clipPlane);
// }
// }
//
//
// public void printGuisStateBefore(List<GuiElement> guiElts) {
// H.pln("GuiElts BEFORE:", "\t" + guiElts);
// H.pln("Guis BEFORE:", "\t" + guis);
// }
//
//
// public void printGuisStateAfter(List<GuiElement> guiElts) {
// H.pln("GuiElts AFTER:", "\t" + guiElts);
// H.pln("Guis AFTER:", "\t" + guis);
// }
//
//
// public void printWatersStateBefore(List<WaterTile> wtrs) {
// H.pln("wtrs BEFORE:", "\t" + wtrs);
// H.pln("waters BEFORE:", "\t" + waters);
// }
//
//
// public void printWatersStateAfter(List<WaterTile> wtrs) {
// H.pln("wtrs AFTER:", "\t" + wtrs);
// H.pln("waters AFTER:", "\t" + waters);
// }
//
//
// private void processAllRenderableCollections(PlayerEntity plyr,
// List<EnvironmentalEntity> envrnmntls, List<ITerrainable<?, ?>> ters,
// List<WaterTile> wtrs, List<GuiElement> guiElts) {
// processPlayer(plyr);
// for (final EnvironmentalEntity entity:envrnmntls)
// processEnvironmental(entity);
// for (final ITerrainable<?, ?> terrain:ters)
// processTerrain(terrain);
// for (final WaterTile water:wtrs)
// processWater(water);
// for (final GuiElement gui:guiElts)
// processGui(gui);
// }
//
//
// /**
// * Renders everything except for the guis.
// */
// @Override
// protected void partialRender(PlayerEntity plyr, List<Light> lhts, Camera cam,
// Vector4f clpPln) {
// renderTerrains(lhts, cam, clpPln);
// renderEnvironmentals(lhts, cam, clpPln);
// renderPlayers(lhts, cam, clpPln);
// renderSkybox(cam);
// }
//
//
// @Override
// protected void clearAllRenderableCollections() {
// guis.clear();
// waters.clear();
// terrains.clear();
// players.clear();
// environmentals.clear();
// }
//
//
// @Override
// public void cleanUpShaders() {
// guiShader.cleanUp();
// waterShader.cleanUp();
// terrainShader.cleanUp();
// playerShader.cleanUp();
// environmentalShader.cleanUp();
// }
//
//
// @Override
// protected void finalize() throws Throwable {
// clearAllRenderableCollections();
// cleanUpShaders();
// super.finalize();
// }
//
//
// private void renderGuis() {
// // H.pln("Guis about to be rendered:", "\t" + guis);
// guiRenderer.run(guis);
// }
//
//
// private void renderSkybox(Camera camera) {
// skyboxRenderer.run(camera, SKY_COLOR);
// }
//
//
// private void renderWaters(List<WaterTile> wtrs, Camera cam, Light sun) {
// // H.pln("WaterTiles about to be rendered:", "\t" + wtrs);
// waterRenderer.run(wtrs, cam, sun);
// }
//
//
// private void renderTerrains(List<Light> lights, Camera camera, Vector4f
// clipPlane) {
// // H.pln("Terrains about to be rendered:", "\t" + terrains);
// terrainRenderer.run(terrains, lights, camera, clipPlane, SKY_COLOR);
// }
//
//
// private void renderPlayers(List<Light> lights, Camera camera, Vector4f
// clipPlane) {
// // H.pln("Players about to be rendered:", "\t" + players);
// playerRenderer.run(players, lights, camera, clipPlane, SKY_COLOR);
// }
//
//
// private void renderEnvironmentals(List<Light> lights, Camera camera, Vector4f
// clipPlane) {
// // H.pln("Environmentals about to be rendered:", "\t" + environmentals);
// environmentalRenderer.run(environmentals, lights, camera, clipPlane,
// SKY_COLOR);
// }
//
//
// private void processGui(GuiElement guiElt) {
// processRenderableList(guis, guiElt);
// }
//
//
// private void processWater(WaterTile water) {
// processRenderableList(waters, water);
// }
//
//
// private void processTerrain(ITerrainable<?, ?> terrain) {
// processRenderableList(terrains, terrain);
// }
//
//
// private void processEnvironmental(EnvironmentalEntity environmental) {
// processRenderableMap(environmentals, environmental);
// }
//
//
// private void processPlayer(PlayerEntity player) {
// processRenderableMap(players, player);
// }
//
//
// }
//
//// public void run(PlayerEntity plyr, List<EnvironmentalEntity> envrnmntls,
//// List<ITerrainable<?, ?>> ters, List<Light> lhts, List<WaterTile> wtrs,
//// List<GuiElement> guiElts, Camera cam, Light sun) {
////
//// processRenderableCollections(plyr, envrnmntls, ters, wtrs, guiElts);
////
//// MasterRenderer.prepareToRenderToDisplay();
////
//// /**
//// * Render the scene 3 times:
//// *
//// * 1. To the reflection frame buffer,
//// * ie everything above the waters's surface.
//// *
//// * 2. To the refraction frame buffer,
//// * ie everything below the waters's surface.
//// *
//// * 3. To the screen.
//// * Note that this doesn't always work:
//// * 'GL11.glDisable(GL30.GL_CLIP_DISTANCE0)'
//// * So useu kludgy workaround instead, rendering with the
//// * clip plane at an 'unfathomableHeight.'
//// */
//// MasterRenderer.enableClippingDistance();
//// renderWaterReflections(plyr, envrnmntls, ters, lhts, wtrs, guiElts, cam);
//// // 1
//// renderWaterRefractions(plyr, envrnmntls, ters, lhts, wtrs, guiElts, cam);
//// // 2
//// MasterRenderer.disableClippingDistance(); // doesn't always work!
//// renderToScreen(plyr, envrnmntls, ters, lhts, wtrs, guiElts, cam, sun); //
//// 3
////
////
//// renderWaters(wtrs, cam, sun);
////
//// // for (final GuiElement gui:guis)
//// // processGui(gui);
//// // H.pln("ABOUT TO RENDER GUIS.");
//// renderGuis();
////
//// printGuisStateBefore(guiElts);
//// clearRenderableCollections();
//// printGuisStateAfter(guiElts);
//// }
//// private void renderWaterReflections(PlayerEntity plyr,
//// List<EnvironmentalEntity> envrnmntls,
//// List<ITerrainable<?, ?>> ters, List<Light> lhts, List<WaterTile> wtrs,
//// List<GuiElement> guiElts, Camera camera) {
//// waterRenderer.bindReflectionFrameBuffer();
//// final Camera cam = new Camera(camera);
//// for (final WaterTile water:wtrs) {
//// final float distance = 2 * (cam.getPosition().y - water.getHeight());
//// cam.getPosition().y -= distance;
//// cam.invertPitch();
//// processAndRender(plyr, envrnmntls, ters, lhts, wtrs, guiElts, cam,
//// waterRenderer.createReflectClipPlane(water));
//// }
//// }
//// private void renderWaterRefractions(PlayerEntity plyr,
//// List<EnvironmentalEntity> envrnmntls,
//// List<ITerrainable<?, ?>> ters, List<Light> lhts, List<WaterTile> wtrs,
//// List<GuiElement> guiElts, Camera camera) {
//// waterRenderer.bindRefractionFrameBuffer();
//// for (final WaterTile water:wtrs)
//// processAndRender(plyr, envrnmntls, ters, lhts, wtrs, guiElts, camera,
//// waterRenderer.createRefractClipPlane(water));
//// }
//// private void renderToScreen(PlayerEntity plyr, List<EnvironmentalEntity>
//// envrnmntls,
//// List<ITerrainable<?, ?>> ters, List<Light> lhts, List<WaterTile> wtrs,
//// List<GuiElement> guiElts, Camera camera, Light sun) {
//// waterRenderer.unbindCurrentFrameBuffer();
//// // for (final WaterTile water:wtrs) {
//// processAndRender(plyr, envrnmntls, ters, lhts, wtrs, guiElts, camera,
//// Maths.v4(0, -1, 0, 100000)); // super high
//// // }
//// // renderWaters(wtrs, camera, sun);
//// }
//// private void processAndRender(PlayerEntity plyr,
//// List<EnvironmentalEntity> envrnmntls,
//// List<ITerrainable<?, ?>> ters, List<Light> lhts, List<WaterTile> wtrs,
//// List<GuiElement> guiElts, Camera cam, Vector4f clipPlane) {
//// // processAll(plyr, envrnmntls, ters, wtrs, guiElts);
//// partialRenderToCurrentBuffer(plyr, lhts, cam, clipPlane);
//// // clearRenderableCollections();
//// }
////
////
//
////
////
////
////
////
////
////
////
//
////
////
//// // private void renderWaters(List<Light> lights, Camera cam, Vector4f
//// // clipPlane) {
//// private void renderWaters(MasterRenderer master, List<Light> lhts,
//// Camera cam, PlayerEntity plyr, List<Environmentals> envrnmntls,
//// List<ITerrainable<?,?>> ters) {
////
//// for (final WaterTile water:waters) {
//// waterShader.start();
////
//// final Light sun = H.getFirst(lights);
//// final ITerrainable<?,?> terrain = H.getFirst(terrains);
////
//// waterRenderer.renderReflection(this, water, cam, plyr, envrnmntls, ters,
//// lhts);
////
//// GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
//// final float errorRoom = 1.0f;
//// final Vector4f unfathomableCP = H.v4(0, -1, 0, 100000);
////
//// final Vector4f reflectCP =
//// waterRenderer.createReflectClipPlane(water, errorRoom);
//// final Vector4f refractCP =
//// waterRenderer.createRefractClipPlane(water, errorRoom);
////
//// // waters' reflection
//// waterRenderer.bindReflectionFrameBuffer();
//// final float distance = 2 * (cam.getPosition().y -
//// water.getHeight());
//// cam.getPosition().y -= distance;
//// cam.invertPitch();
//// this.renderScene(player, environmentals, terrains, lights, cam,
//// reflectCP);
//// cam.getPosition().y += distance;
//// cam.invertPitch();
////
//// // waters' refraction
//// waterRenderer.bindRefractionFrameBuffer();
//// this.renderScene(player, environmentals, terrains, lights, cam,
//// refractCP);
////
//// GL11.glDisable(GL30.GL_CLIP_DISTANCE0); // doesn't always work!
//// waterRenderer.unbindCurrentFrameBuffer();
//// this.renderScene(player, environmentals, terrains, lights, cam,
//// unfathomableCP);
////
////
//// // GUI_R.render(GUI_ELTS); // render guis last
//// waterRenderer.prepareRender(cam, sun);
////
//// waterShader.loadModelMatrix(waterRenderer.createModelMatrix(water));
//// GL11.glDrawArrays(GL11.GL_TRIANGLES, 0,
//// waterRenderer.quad.getVertexCount());
////
//// waterRenderer.unbind();
//// waterShader.stop();
//// }
//// }
