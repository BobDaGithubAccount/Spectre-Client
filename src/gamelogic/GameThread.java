package gamelogic;

import java.util.HashMap;

import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import renderEngine.Loader;

@SuppressWarnings("unused")
public class GameThread extends Thread {

	private int tps;
	private Camera camera;
	private HashMap<String, Entity> entities;
	private HashMap<String, Light> lights;
	private Loader loader;
	
	public GameThread(int tps, Camera camera, HashMap<String, Entity> entities, HashMap<String, Light> lights, Loader loader) {
		this.tps = tps;
		this.camera = camera;
		this.entities = entities;
		this.lights = lights;
		this.loader = loader;
	}

	@Override
	public void run() {
		Entity entity1 = entities.get("dragon1");
		Light light1 = lights.get("light1");
		while(true) {
			try {
				Thread.sleep(1000/tps);
				entity1.increasePosition(0f, 0f, 0f);
				entity1.increaseRoation(0f, 0.1f, 0f);
				light1.setPosition(new Vector3f(light1.getPosition().x,light1.getPosition().y+0.1f,light1.getPosition().z));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
}
