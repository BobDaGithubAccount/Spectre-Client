package main;

import java.util.ArrayList;
import java.util.Date;

import org.lwjgl.opengl.Display;

import logic.F_P_S_TrackingTask;
import logic.InputTask;
import logic.Scheduler;
import logic.TPS_Task;
import networking.NetworkingThread;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.Renderer;

public class MainGameLoop {
	
	public static int fps = 60;
	
	private static ArrayList<Scheduler> tasksToRun = new ArrayList<Scheduler>();
	
	public static InputTask it = new InputTask(10);
	public static F_P_S_TrackingTask fpstask = new F_P_S_TrackingTask(1000);
	public static TPS_Task gameClock = new TPS_Task(50); //20 tps
	
	public static NetworkingThread nt = new NetworkingThread();
	
	public static void main(String[] args) {
		
//		DisplayManager.createDisplay();
//		
//		init();
//		
//		MasterRenderer.init();
//		Renderer.initRenderer();
//		
		nt.start();
		
//		
//		while (!Display.isCloseRequested()) {
//			fps++;
//			pollEvents();
//			MasterRenderer.render();
//		}
//
//		MasterRenderer.cleanUp();
//		Loader.cleanUp();
//		DisplayManager.closeDisplay();
//		System.exit(0);
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
	}
	
	public static void pushTaskToStack(Scheduler s) {
		tasksToRun.add(s);
	}
}