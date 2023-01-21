package event.events;

import org.lwjgl.util.vector.Vector3f;

import event.IEvent;
import lib.json.JSONObject;
import networking.Packet;
import renderEngine.MasterRenderer;

public class PlayerMoveEvent implements IEvent {

	@Override
	public String name() {
		return Packet.SMovePacket;
	}

	@Override
	public boolean run(JSONObject json) {
		MasterRenderer.entity.setPosition(new Vector3f(json.getFloat("x"), json.getFloat("y"), json.getFloat("z")));
		MasterRenderer.entity.setRotation(new Vector3f(json.getFloat("pitch"), json.getFloat("yaw"), json.getFloat("roll")));
		return false;
	}

}
