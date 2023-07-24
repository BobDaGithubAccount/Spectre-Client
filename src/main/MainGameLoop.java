package main;

import logging.Logger;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import logic.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import event.EventHandler;
import networking.NetworkingThread;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.GeometryLoader;
import renderEngine.Renderer;
//import renderEngine.sourceEngineCompatibility.ValveMapFormatLoader;

public class MainGameLoop {
	
	public static int fps = 60;
	
	private static ArrayList<Scheduler> tasksToRun = new ArrayList<Scheduler>();
	
	public static InputTask it = new InputTask(10);
	public static F_P_S_TrackingTask fpstask = new F_P_S_TrackingTask(1000);
	public static TPS_Task gameClock = new TPS_Task(50); //20 tps
	public static LoadLevelTask loadLevelTask = new LoadLevelTask(20);

	public static String ip = "localhost";
	public static int port = 3000;

	public static NetworkingThread nt = new NetworkingThread(ip,port);

	public static void main(String[] args) throws Exception {
		Logger.init();
		EventHandler.init();

		DisplayManager.createDisplay();

		init();

		MasterRenderer.init();
		Renderer.initRenderer();

		nt.start();

		while (!Display.isCloseRequested()) {
			fps++;
			pollEvents();
			MasterRenderer.render();
		}

		MasterRenderer.cleanUp();
		Loader.cleanUp();
		DisplayManager.closeDisplay();
		Logger.shutdown();
		System.exit(0);
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
	
	private static void init() {
		pushTaskToStack(it);
		pushTaskToStack(fpstask);
		pushTaskToStack(gameClock);
		pushTaskToStack(loadLevelTask);
	}
	
	public static void pushTaskToStack(Scheduler s) {
		tasksToRun.add(s);
	}
}