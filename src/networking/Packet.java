package networking;

import java.util.Date;
import java.util.UUID;

import lib.json.JSONObject;

public class Packet {

	public static final int protocolVersion = 1;
	
	public static JSONObject CPingPacket() {
		JSONObject packet = new JSONObject();
		packet.put("packet_type", "C-PING");
		packet.put("time", new Date().getTime());
		return packet;
	}
	
	public static JSONObject CConnectPacket() {
		JSONObject packet = new JSONObject();
		packet.put("packet_type", "C-CONNECT");
		packet.put("protocol_version", protocolVersion);
		packet.put("player_name", UUID.randomUUID().toString());
		return packet;
	}
	
}
