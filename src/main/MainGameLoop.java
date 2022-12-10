package main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Light;
import gamelogic.InputThread;
import gamelogic.Scheduler;
import objects.Dragon;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import shaders.StaticShader;

public class MainGameLoop {
		
	private static ArrayList<Scheduler> tasksToRun = new ArrayList<Scheduler>();
	
	private static Calendar calendar = Calendar.getInstance();
	
	public static Camera camera = new Camera(new Vector3f(0f,0f,0f),0f,0f,0f);
	
	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);
		
		initControls();

		Dragon dragon = new Dragon();
//		Skybox skybox = new Skybox();
		
		Light light1 = new Light(new Vector3f(0f,20f,10f), new Vector3f(1f,1f,1f));
		
		calendar.setTime(new Date());
		
		while (!Display.isCloseRequested()) {
			pollEvents();
			renderer.prepare();
			shader.start();
			shader.loadLight(light1);
			renderer.render(dragon.getEntity(), shader);
			dragon.run();
//			renderer.render(skybox.getEntity(), shader);
//			skybox.run();
			shader.loadViewMatrix();
			shader.stop();
			DisplayManager.updateDisplay();
		}
		shader.cleanUp();
		Loader.cleanUp();
		DisplayManager.closeDisplay();
	}
	
	private static void pollEvents() {
		long currentTime = calendar.getTimeInMillis();
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
		pushTaskToStack(new InputThread(10));
	}
	
	public static void pushTaskToStack(Scheduler s) {
		tasksToRun.add(s);
	}
}
