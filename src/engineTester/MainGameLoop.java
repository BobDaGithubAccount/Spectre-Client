package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
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

		Entity entity = loader.loadObj("dragon1", new Vector3f(0f,0f,-35f), new Vector3f(0f,0f,0f), 1);
		Light light = new Light(new Vector3f(0f,0f,-30f), new Vector3f(1f,1f,1f));
		Camera camera = new Camera();

		InputThread it = new InputThread(100, camera);
		it.start();
		
		while (!Display.isCloseRequested()) {
			entity.increasePosition(0f, 0f, 0f);
			entity.increaseRoation(0f, 0.1f, 0f);
			light.setPosition(new Vector3f(light.getPosition().x,light.getPosition().y+0.1f,light.getPosition().z));
			// game logic
			renderer.prepare();
			shader.start();
			shader.loadLight(light);
			shader.loadViewMatrix(camera);
			renderer.render(entity, shader);
			shader.stop();
			DisplayManager.updateDisplay();
		}
		it.stop();
		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}
