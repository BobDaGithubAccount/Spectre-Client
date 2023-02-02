package event.events;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import event.IEvent;
import lib.json.JSONObject;
import main.Spectre;
import networking.Packet;
import renderEngine.MasterRenderer;

public class PlayerMoveEvent implements IEvent {

	@Override
	public String name() {
		return Packet.SMovePacket;
	}

	@Override
	public boolean run(JSONObject json) {
		Entity e = MasterRenderer.getInstance(Spectre.dragon, json.getString("uuid"));
		e.setPosition(new Vector3f(json.getFloat("x"), json.getFloat("y"), json.getFloat("z")));
		e.setRotation(new Vector3f(0, json.getFloat("yaw"), 0));
		MasterRenderer.setInstance(Spectre.dragon, e);
		return false;
	}

}
