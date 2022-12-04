package engineTester;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import gamelogic.InputThread;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);

		RawModel model = OBJLoader.loadObjModel("stall", loader);
		ModelTexture texture = new ModelTexture(loader.loadTexture("stall"));
		TexturedModel texturedModel = new TexturedModel(model, texture);
		Entity entity = new Entity(texturedModel, new Vector3f(0, 0, -50), 0f, 0f, 0f, 1f);
		Camera camera = new Camera();

		System.out.println(Keyboard.KEY_W);
		System.out.println(Keyboard.KEY_A);
		System.out.println(Keyboard.KEY_S);
		System.out.println(Keyboard.KEY_D);

		InputThread it = new InputThread(100, camera);
		it.start();
		
		while (!Display.isCloseRequested()) {
			entity.increasePosition(0f, 0f, 0f);
			entity.increaseRoation(0f, 1f, 0f);
			// game logic
			renderer.prepare();
			shader.start();
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
