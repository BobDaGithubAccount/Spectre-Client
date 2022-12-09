package objects;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import main.MainGameLoop;
import models.RawModels;

public class Skybox extends GameObject {

	public static final String name = "skybox";
	public static final Entity entity = RawModels.getSkyboxCube(new Vector3f(0,0,0), new Vector3f(0,0,0));

	
	public Skybox() {
		super(name, entity);
	}


	@Override
	public void run() {
		Entity entity = this.getEntity();
		entity.setPosition(MainGameLoop.camera.getPosition());
		this.setEntity(entity);
	}

}
