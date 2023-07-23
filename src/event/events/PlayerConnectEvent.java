package event.events;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import event.IEvent;
import lib.json.JSONObject;
import main.Spectre;
import networking.Packet;
import renderEngine.MasterRenderer;

public class PlayerConnectEvent implements IEvent {

	@Override
	public String name() {
		return Packet.SConnectPacket;
	}

	@Override
	public boolean run(JSONObject json) {
		Entity player = new Entity(json.getString("uuid"), Spectre.playerModelName, new Vector3f(0f,0f,0f), new Vector3f(0f,0f,0f), 1);
		MasterRenderer.pushInstance(player.getParent(), player);
		return false;
	}
	
}
