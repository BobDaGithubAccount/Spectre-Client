package renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import entities.Camera;
import entities.Entity;
import entities.Light;
import shaders.StaticShader;
import textures.ModelTexture;

public class MasterRenderer {

	public static StaticShader shader = new StaticShader();
	
	public static Camera camera = new Camera(new Vector3f(0f,0f,0f),0f,0f,0f);
	
	public static Light sun = new Light(new Vector3f(0f,20f,10f), new Vector3f(1f,1f,1f));
	
	private static Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	
	private static Entity entity = Loader.loadObj("dragon1", new Vector3f(0f,-5f,-250f), new Vector3f(0f,0f,0f), 1);
		
	public static void init() {
		TexturedModel model = entity.getModel();
		ModelTexture t = model.getTexture();
		t.setShineDamper(1f);
		t.setReflectivity(0.5f);
		model.setTexture(t);
		entity.setModel(model);
	}
	
	public static void render() {
		processEntity(entity);
		Renderer.prepare();
		shader.start();
		shader.loadSkyColour(Renderer.RED, Renderer.GREEN, Renderer.BLUE);
		shader.loadLight(sun);
		shader.loadViewMatrix();
		Renderer.render(entities);
		shader.stop();
		entities.clear();
		DisplayManager.updateDisplay();
	}
	
	public static void processEntity(Entity entity) {
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if(batch!=null) {
			batch.add(entity);
		}
		else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
	}
	
	public static void cleanUp() {
		shader.cleanUp();
	}
	
	
}
