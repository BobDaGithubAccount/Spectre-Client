package gamelogic.keys;

import org.lwjgl.util.vector.Vector3f;

import entities.Camera;

public class WKey extends AbstractKey {

	public WKey(int id, Camera camera) {
		super(id, camera);
	}

	@Override
	public void run() {
		Camera camera = super.getCamera();
		Vector3f position = camera.getPosition();
		position.z -= 0.02f;
		camera.setPosition(position);
	}

}