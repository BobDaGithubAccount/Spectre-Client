package main;

import java.util.ArrayList;
import java.util.Date;

import org.lwjgl.opengl.Display;

import logic.F_P_S_TrackingTask;
import logic.InputTask;
import logic.Scheduler;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.Renderer;

public class MainGameLoop {
	
	public static int fps = 0;
	
	private static ArrayList<Scheduler> tasksToRun = new ArrayList<Scheduler>();
	
	private static InputTask it = new InputTask(10);
	public static F_P_S_TrackingTask fpstask = new F_P_S_TrackingTask(1000);
	
	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		
		initControls();
		initFps();
		
		MasterRenderer.init();
		Renderer.initRenderer();
		
		while (!Display.isCloseRequested()) {
			fps++;
			pollEvents();
			MasterRenderer.render();
		}

		MasterRenderer.cleanUp();
		Loader.cleanUp();
		DisplayManager.closeDisplay();
	}

	private static void pollEvents() {
		long currentTime = new Date().getTime();
		ArrayList<Scheduler> toRemove = new ArrayList<Scheduler>();
		for(Scheduler s : tasksToRun) {
			if(s.getEpochTimeToRun() < currentTime) {
				s.run();
				if(!s.shouldRepeat()) {
					toRemove.add(s);
				}
			}
		}
		tasksToRun.removeAll(toRemove);
	}
	
	private static void initControls() {
		pushTaskToStack(it);
	}
	
	private static void initFps() {
		pushTaskToStack(fpstask);
	}
	
	public static void pushTaskToStack(Scheduler s) {
		tasksToRun.add(s);
	}
}
