package core.display;


import static settings.GameConstants.Display.FPS_CAP;
import static settings.GameConstants.Display.HEIGHT;
import static settings.GameConstants.Display.TITLE;
import static settings.GameConstants.Display.WIDTH;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public final class DisplayManager {
	private final static Clock FRAME_CLOCK = new Clock();


	private DisplayManager() {}


	public static int getWidth() {
		return Display.getWidth();
	}


	public static int getHeight() {
		return Display.getHeight();
	}


	public static float getAspectRatio() {
		return (float) getWidth() / (float) getHeight();
	}


	public static void createDisplay() {
		final ContextAttribs attribs = new ContextAttribs(3, 2)
				.withForwardCompatible(true)
				.withProfileCore(true);

		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle(TITLE);
		} catch (final LWJGLException e) {
			e.printStackTrace();
		}

		GL11.glViewport(0, 0, WIDTH, HEIGHT);
	}


	public static void updateDisplay() {
		Display.sync(FPS_CAP);
		Display.update();

		FRAME_CLOCK.update();
	}


	public static boolean closeRequestedQ() {
		return Display.isCloseRequested();
	}


	public static float getFrameTimeSeconds() {
		return FRAME_CLOCK.getTimeSinceLastUpdateInSeconds();
	}


	public static void closeDisplay() {
		Display.destroy();
	}


}
