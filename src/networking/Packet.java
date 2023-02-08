package networking;

import lib.json.JSONObject;

import java.util.Date;

public class Packet {

	public static final int protocolVersion = 1;
	public static final String packet_type = "packet_type";
	public static final String protocol_version = "protocol_version";

	//
	//	SERVER
	//

	public static String SPingPacket = "S-PING";

	public static String SStatusPacket = "S-STATUS";

	public static String SConnectPacket = "S-CONNECT";

	public static String SInitPacket = "S-INIT";
	
	public static String SDisconnectPacket = "S-DISCONNECT";

	public static String SMovePacket = "S-MOVE";

	//
	//	CLIENT
	//

	public static String CPingPacket = "C-PING";
	public static JSONObject CPingPacket() {
		JSONObject packet = new JSONObject();
		packet.put(packet_type, CPingPacket);
		packet.put(protocol_version, protocolVersion);
		packet.put("time", new Date().getTime());
		return packet;
	}

	public static String CConnectPacket = "C-CONNECT";
	public static JSONObject CConnectPacket(String name) {
		JSONObject packet = new JSONObject();
		packet.put(packet_type, CConnectPacket);
		packet.put(protocol_version, protocolVersion);
		packet.put("name", name);
		return packet;
	}
	public static String CDisconnectPacket = "C-DISCONNECT";
	public static JSONObject CDisconnectPacket() {
		JSONObject packet = new JSONObject();
		packet.put(packet_type, CDisconnectPacket);
		packet.put(protocol_version, protocolVersion);
		return packet;
	}

	public static String CMovePacket = "C-MOVE";
	public static JSONObject CMovePacket(float x, float y, float z, float pitch, float yaw, float roll) {
		JSONObject packet = new JSONObject();
		packet.put(packet_type, CMovePacket);
		packet.put(protocol_version, protocolVersion);
		packet.put("x", x);
		packet.put("y", y);
		packet.put("z", z);
		packet.put("pitch", pitch);
		packet.put("yaw", yaw);
		packet.put("roll", roll);
		return packet;
	}

}
