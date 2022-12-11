package objects;

import org.lwjgl.util.vector.Vector3f;
import entities.Entity;
import models.TexturedModel;
import renderEngine.Loader;
import textures.ModelTexture;

public class Dragon extends GameObject {

	public static String name = "dragon1";
	public static Entity entity = Loader.loadObj(name, new Vector3f(0f,-5f,-25f), new Vector3f(0f,0f,0f), 1);
	
	public Dragon() {
		super(name, entity);
		TexturedModel model = entity.getModel();
		ModelTexture t = model.getTexture();
		t.setShineDamper(1f);
		t.setReflectivity(0.5f);
		model.setTexture(t);
		entity.setModel(model);
	}

	@Override
	public void run() {
		Entity entity = this.getEntity();
		Vector3f rotation = entity.getRotation();
//		rotation.setX(rotation.getX()+1);
		entity.setRotation(rotation);
		this.setEntity(entity);
	}

}
