package logic;

import org.lwjgl.input.Keyboard;
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
		
		if(Keyboard.isKeyDown(17)) { //W
			position.z -= (6f/fps);
		}
		
		if(Keyboard.isKeyDown(30)) { //A
			position.x -= (6f/fps);
		}
		
		if(Keyboard.isKeyDown(31)) { //S
			position.z += (6f/fps);
		}
			
		if(Keyboard.isKeyDown(32)) { //D
			position.x += (6f/fps);
		}
		
		
		
		if(Keyboard.isKeyDown(200)) { //UP
			camera.setPitch(camera.getPitch() - (60f/fps));
		}
		
		if(Keyboard.isKeyDown(203)) { //LEFT
			camera.setYaw(camera.getYaw() - (60f/fps));
		}
		
		if(Keyboard.isKeyDown(208)) { //DOWN
			camera.setPitch(camera.getPitch() + (60f/fps));
		}
			
		if(Keyboard.isKeyDown(205)) { //RIGHT
			camera.setYaw(camera.getYaw() + (60f/fps));
		}
		
		camera.setPosition(position);
		
		this.setEpochTimeToRun(this.getEpochTimeToRun()+this.delay);
	}
	
	@Override
	public boolean shouldRepeat() {
		return true;
	}

}
