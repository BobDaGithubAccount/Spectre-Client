package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

//import assets.ResourceLoader;

public class DisplayManager {

	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final int FPS_CAP = 60;
	private static final String TITLE = "Spectre";

	public static void createDisplay() {
		ContextAttribs attribs = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle(TITLE);
			Display.setResizable(true);
			Mouse.setGrabbed(true);
//			ResourceLoader.setIcon();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
	}

	public static void updateDisplay() {
		Display.sync(FPS_CAP);
		if (Display.wasResized()) GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		Display.update();
	}

	public static void closeDisplay() {
		Display.destroy();
	}
	
	public static void updateTitleWithFPS(int fps, int tps) {
		Display.setTitle(TITLE + " | FPS: " + fps + " | TPS: " + tps);
	}

}
