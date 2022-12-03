package gamelogic.keys;

import entities.Camera;

public abstract class AbstractKey {

	private int id;
	@SuppressWarnings("unused")
	private Camera camera;
	
	public AbstractKey(int id, Camera camera) {
		this.id = id;
		this.camera = camera;
	}
	
	public abstract void run();
	
	public int getID() {
		return this.id;
	}
	
	public Camera getCamera() {
		return this.camera;
	}
	
}
