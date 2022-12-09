package main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import gamelogic.InputThread;
import gamelogic.Scheduler;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import shaders.StaticShader;

public class MainGameLoop {
		
	static ArrayList<Scheduler> tasksToRun = new ArrayList<Scheduler>();
	
	static Calendar calendar = Calendar.getInstance();
	
	public static void main(String[] args) {

		System.out.println(Keyboard.KEY_UP);
		System.out.println(Keyboard.KEY_LEFT);
		System.out.println(Keyboard.KEY_DOWN);
		System.out.println(Keyboard.KEY_RIGHT);
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);
		
		Camera camera = new Camera(new Vector3f(0f,0f,0f),0f,0f,0f);
		initControls(camera);

		Entity entity1 = loader.loadObj("dragon1", new Vector3f(0f,0f,-25f), new Vector3f(0f,0f,0f), 1);
		Light light1 = new Light(new Vector3f(0f,0f,10f), new Vector3f(1f,1f,1f));
		
		calendar.setTime(new Date());
		
		while (!Display.isCloseRequested()) {
//			entity1.increasePosition(0f, 0f, 0f);
//			entity1.increaseRoation(0f, 0.1f, 0f);
//			light1.setPosition(new Vector3f(light1.getPosition().x,light1.getPosition().y+0.1f,light1.getPosition().z));
			pollEvents();
			renderer.prepare();
			shader.start();
			shader.loadLight(light1);
			renderer.render(entity1, shader);
			shader.loadViewMatrix(camera);
			shader.stop();
			DisplayManager.updateDisplay();
		}
		shader.cleanUp();
		loader.cleanUp();
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
	
	private static void initControls(Camera camera) {
		pushTaskToStack(new InputThread(camera, 10));
		System.out.println(tasksToRun);
	}
	
	public static void pushTaskToStack(Scheduler s) {
		tasksToRun.add(s);
	}
}
