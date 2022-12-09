package objects;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import renderEngine.Loader;

public class Dragon extends GameObject {

	public static final String name = "dragon1";
	public static final Entity entity = Loader.loadObj(name, new Vector3f(0f,0f,-25f), new Vector3f(0f,0f,0f), 1);
	
	public Dragon() {
		super(name, entity);
	}

	@Override
	public void run() {
		Entity entity = this.getEntity();
		Vector3f rotation = entity.getRotation();
		rotation.setX(rotation.getX()+1);
		entity.setRotation(rotation);
		this.setEntity(entity);
	}

}
