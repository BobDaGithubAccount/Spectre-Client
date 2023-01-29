package event.events;

import java.util.Map;
import java.util.Map.Entry;
import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import event.IEvent;
import lib.json.JSONObject;
import main.Spectre;
import networking.Packet;
import renderEngine.MasterRenderer;

public class PlayerInitPacketEvent implements IEvent {

	@Override
	public String name() {
		return Packet.SInitPacket;
	}

	@Override
	public boolean run(JSONObject json) {
		Map<String, Object> players = json.getJSONObject("players").toMap();
		System.out.println(players.toString());
		System.out.println(json.toString());
		for(Entry<String, Object> e : players.entrySet()) {
			Entity player = new Entity(e.getKey(), Spectre.dragon, new Vector3f(0f,0f,0f), new Vector3f(0f,0f,0f), 1);
			MasterRenderer.pushInstance(player.getParent(), player);
		}
		return false;
	}

}
