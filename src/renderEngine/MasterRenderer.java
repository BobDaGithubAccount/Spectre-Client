package renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import entities.Camera;
import entities.Entity;
import entities.Light;
import main.Spectre;
import shaders.StaticShader;
import textures.ModelTexture;

public class MasterRenderer {

	public static StaticShader shader = new StaticShader();
	
	public static Camera camera = new Camera(new Vector3f(0f,0f,0f),0f,0f,0f);
	
	public static Light sun = new Light(new Vector3f(0f,20f,10f), new Vector3f(1f,1f,1f));
	
	private static Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	
	private static Map<String, RenderObject> objects = new HashMap<String, RenderObject>();
	
	public static Entity entity = Loader.loadObj(Spectre.dragon, new Vector3f(0f,-5f,-250f), new Vector3f(0f,0f,0f), 1);
		
	public static void init() {
		if(objects.containsKey(entity.getParent())) {
			RenderObject ro = objects.get(entity.getParent());
			TexturedModel model = ro.model;
			ModelTexture t = model.getTexture();
			t.setShineDamper(1f);
			t.setReflectivity(0.5f);
			model.setTexture(t);
			ro.model = model;
			objects.put(entity.getParent(), ro);
		}
	}
	
	public static void render() {
		Map<String, RenderObject> objectsCopy = objects;
		entities.clear();
		for(Entry<String, RenderObject> e : objectsCopy.entrySet()) {
			processEntity(e.getValue());
		}
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
	
	public static void processEntity(RenderObject object) {
		TexturedModel entityModel = object.model;
		List<Entity> batch = new ArrayList<>(object.instances.values());
		entities.put(entityModel, batch);
	}
	
	public static void setObject(String parent, RenderObject object) {
		if(objects.containsKey(parent) && objects.get(parent).model.equals(object.model)) {
			RenderObject ro = objects.get(parent);
			for(Entry<String, Entity> instance : object.instances.entrySet()) {
				ro.instances.put(instance.getKey(), instance.getValue());
			}
			objects.put(parent, ro);
		}
		else {
			objects.put(parent, object);
		}
	}
	
	public static void delObject(String parent) {
		objects.remove(parent);
	}
	
	public static Set<String> getKeys() {
		return objects.keySet();
	}
	
	public static boolean pushInstance(String parent, Entity entity) {
		if(objects.containsKey(parent)) {
			RenderObject ro = objects.get(parent);
			ro.instances.put(entity.getName(), entity);
			objects.put(parent, ro);
			return true;
		}
		return false;
	}
	
	public static boolean setInstance(String parent, Entity entity) {
		if(objects.containsKey(parent)) {
			RenderObject ro = objects.get(parent);
			ArrayList<String> toRemove = new ArrayList<String>();
			for(String name : ro.instances.keySet()) {
				if(name.equals(entity.getName())) {
					toRemove.add(name);
				}
			}
			for(String s : toRemove) {
				ro.instances.remove(s);
			}
			ro.instances.put(entity.getName(), entity);
			objects.put(parent, ro);
			return true;
		}
		return false;
	}
	
	public static Entity getInstance(String parent, String name) {
		System.out.println(getKeys());
		if(objects.containsKey(parent)) {
			RenderObject ro = objects.get(parent);
			System.out.println(name + " | " + ro.instances.toString());
			for(String s : ro.instances.keySet()) {
				if(s.equals(name)) {
					return ro.instances.get(s);
				}
			}
		}
		return null;
	}
	
	public static void cleanUp() {
		shader.cleanUp();
	}
	
	
}
