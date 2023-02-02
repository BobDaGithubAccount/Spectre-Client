package event.events;

import event.IEvent;
import lib.json.JSONObject;
import main.Spectre;
import networking.Packet;
import renderEngine.MasterRenderer;

public class PlayerDisconnectEvent implements IEvent {

	@Override
	public String name() {
		return Packet.SDisconnectPacket;
	}

	@Override
	public boolean run(JSONObject json) {
		MasterRenderer.deleteInstance(Spectre.dragon, json.getString("uuid"));
		return false;
	}
	
}
