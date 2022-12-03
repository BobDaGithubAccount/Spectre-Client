package gamelogic;

import java.util.HashMap;
import java.util.Map.Entry;

import org.lwjgl.input.Keyboard;
import entities.Camera;
import gamelogic.keys.AbstractKey;
import gamelogic.keys.*;

public class InputThread extends Thread {
	
	private HashMap<Integer, AbstractKey> keys = new HashMap<Integer, AbstractKey>();
	
	private int tps;
	private Camera camera;
	
	public InputThread(int tps, Camera camera) {
		this.tps = tps;
		this.camera = camera;
		keys.put(17, new WKey(17, this.camera));
		keys.put(30, new AKey(30, this.camera));
		keys.put(31, new SKey(31, this.camera));
		keys.put(32, new DKey(32, this.camera));
	}

	@Override
	public void run() {
		while(true) {
			for(Entry<Integer, AbstractKey> e : keys.entrySet()) {
				if(Keyboard.isKeyDown(e.getKey())) {
					e.getValue().run();
				}
			}
			try {
				Thread.sleep(1000/tps);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
