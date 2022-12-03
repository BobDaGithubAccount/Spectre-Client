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

		float[] vertices = {
				// Left bottom triangle
				-0.5f, 0.5f, 0f, -0.5f, -0.5f, 0f, 0.5f, -0.5f, 0f,
				// Right top triangle
				0.5f, -0.5f, 0f, 0.5f, 0.5f, 0f, -0.5f, 0.5f, 0f };

		int[] indices = { 0, 1, 3, 3, 1, 2 };

		float[] textureCoords = { 0, 0, 0, 1, 1, 1, 1, 0 };

		RawModel model = loader.loadToVAO(vertices, indices, textureCoords);
		ModelTexture texture = new ModelTexture(loader.loadTexture("picture"));
		TexturedModel texturedModel = new TexturedModel(model, texture);
		Entity entity = new Entity(texturedModel, new Vector3f(0, 0, -1), 0f, 0f, 0f, 1f);
		Camera camera = new Camera();

		System.out.println(Keyboard.KEY_W);
		System.out.println(Keyboard.KEY_A);
		System.out.println(Keyboard.KEY_S);
		System.out.println(Keyboard.KEY_D);

		InputThread it = new InputThread(100, camera);
		it.start();
		
		while (!Display.isCloseRequested()) {
			entity.increasePosition(0f, 0f, 0f);
			entity.increaseRoation(1f, 0f, 1f);
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
