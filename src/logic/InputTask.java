package logic;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import main.MainGameLoop;
import renderEngine.MasterRenderer;

public class InputTask extends Scheduler {	
	
	public InputTask(long delay) {
		super(delay);
	}
	
	@Override
	public void run() {		
		Camera camera = MasterRenderer.camera;
		Vector3f position = camera.getPosition();
		int fps = MainGameLoop.fpstask.fps;
		
		float speed = 6f/fps;
		
		if(Mouse.isGrabbed()) {
			float width = Display.getWidth();
			float height = Display.getHeight();
			float dx = Mouse.getX() - width/2;
			float dy = Mouse.getY() - height/2;
			Mouse.setCursorPosition((int)width/2, (int)height/2);
			camera.setYaw(camera.getYaw() + (dx/fps));
			camera.setPitch(camera.getPitch() - (dy/fps));
		}
		
		float yaw = camera.getYaw();
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
	        position.z += -(float)Math.cos(Math.toRadians(yaw)) * speed;
	        position.x += (float)Math.sin(Math.toRadians(yaw)) * speed;   
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
	        position.z -= (float)Math.sin(Math.toRadians(yaw)) * speed;
	        position.x -= (float)Math.cos(Math.toRadians(yaw)) * speed;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
	        position.z -= -(float)Math.cos(Math.toRadians(yaw)) * speed;
	        position.x -= (float)Math.sin(Math.toRadians(yaw)) * speed;
		}
			
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
	        position.z += (float)Math.sin(Math.toRadians(yaw)) * speed;
	        position.x += (float)Math.cos(Math.toRadians(yaw)) * speed;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			Mouse.setGrabbed(!Mouse.isGrabbed());
		}
		
		camera.setPosition(position);
		
		this.setEpochTimeToRun(this.getEpochTimeToRun()+this.delay);
	}
	
	@Override
	public boolean shouldRepeat() {
		return true;
	}

}
