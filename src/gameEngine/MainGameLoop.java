package gameEngine;


import core.display.DisplayManager;
import core.utils.helpers.H;

/**
 * @author storm
 *
 */
public final class MainGameLoop {

    private Game game;


    private MainGameLoop() {}


    public static void main(final String[] args) {
        new MainGameLoop().run();
        H.pln("Done.");
    }


    private void run() {
        initialize();
        mainLoop();
        destroy();
    }


    /**
     * Must create a display before instantiating a new Game!
     */
    private void initialize() {
        DisplayManager.createDisplay();
        game = new Game();
    }


    private void destroy() {
        game.cleanUp();
        DisplayManager.closeDisplay();
    }


    @Override
    protected void finalize() throws Throwable {
        destroy();
        super.finalize();
    }


    private void mainLoop() {
        while (!DisplayManager.closeRequestedQ()) {

            // H.pln(DisplayManager.getFrameTimeSeconds());

            // H.pln(game.LIGHTS.size(),
            // game.TERRAINS.size(),
            // game.WATERS.size(),
            // game.ENV_ENTS.size());

            game.mainLoopLogic();

            DisplayManager.updateDisplay();
        }
    }
}
