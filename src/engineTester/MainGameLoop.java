package engineTester;

import java.util.HashMap;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import gamelogic.GameThread;
import gamelogic.InputThread;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import shaders.StaticShader;

public class MainGameLoop {
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);

		Entity entity1 = loader.loadObj("dragon1", new Vector3f(0f,0f,-25f), new Vector3f(0f,0f,0f), 1);
		Light light1 = new Light(new Vector3f(0f,0f,-20f), new Vector3f(1f,1f,1f));
		Camera camera = new Camera();

		HashMap<String, Entity> entities = new HashMap<String, Entity>();
		entities.put("dragon1",entity1);
		
		HashMap<String, Light> lights = new HashMap<String, Light>();
		lights.put("light1",light1);
		
		GameThread gt = new GameThread(20, camera, entities, lights, loader);
		gt.start();
		
		InputThread it = new InputThread(100, camera);
		it.start();
		
		while (!Display.isCloseRequested()) {
			// game logic
			renderer.prepare();
			shader.start();
			shader.loadLight(light1);
			shader.loadViewMatrix(camera);
			renderer.render(entity1, shader);
			shader.stop();
			DisplayManager.updateDisplay();
		}
		it.stop();
		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}
