package gamelogic;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import main.MainGameLoop;

public class InputThread extends Scheduler {	

	
	public InputThread(long delay) {
		super(delay);
	}

	long oldTime = 0;
	
	@Override
	public void run() {		
		Camera camera = MainGameLoop.camera;
		Vector3f position = camera.getPosition();
		
		if(Keyboard.isKeyDown(17)) { //W
			position.z -= 0.1f;
		}
		
		if(Keyboard.isKeyDown(30)) { //A
			position.x -= 0.1f;
		}
		
		if(Keyboard.isKeyDown(31)) { //S
			position.z += 0.1f;
		}
			
		if(Keyboard.isKeyDown(32)) { //D
			position.x += 0.1f;
		}
		
		
		
		if(Keyboard.isKeyDown(200)) { //UP
			camera.setPitch(camera.getPitch() - 0.36f);
		}
		
		if(Keyboard.isKeyDown(203)) { //LEFT
			camera.setYaw(camera.getYaw() - 0.36f);
		}
		
		if(Keyboard.isKeyDown(208)) { //DOWN
			camera.setPitch(camera.getPitch() + 0.36f);
		}
			
		if(Keyboard.isKeyDown(205)) { //RIGHT
			camera.setYaw(camera.getYaw() + 0.36f);
		}
		
		camera.setPosition(position);
		
		this.setDelay(this.calendar.getTimeInMillis()+this.delay);
	}
	
	@Override
	public boolean shouldRepeat() {
		return true;
	}

}
