package event;

import event.events.PlayerConnectEvent;
import event.events.PlayerDisconnectEvent;
import event.events.PlayerMoveEvent;
import event.events.s2cInitEvent;
import lib.json.JSONObject;
import logging.Logger;
import networking.Packet;

import java.util.ArrayList;
import java.util.HashMap;

public class EventHandler {
	public static HashMap<String, ArrayList<IEvent>> events = new HashMap<String, ArrayList<IEvent>>();

	public static boolean levelLoaded = false;

	public static void pollPacket(JSONObject json) {
		try {
			String packet_type = json.getString(Packet.packet_type);
			if (!events.containsKey(packet_type)) {
				return;
			}
			ArrayList<IEvent> eventsToPoll = events.get(packet_type);
			if (eventsToPoll == null) {
				return;
			}
			for (IEvent eventHook : eventsToPoll) {
				boolean shouldCancel = eventHook.run(json);
				if (shouldCancel) {
					break;
				}
			}
		} catch (Exception e) {
			Logger.log(e.getMessage());
			e.printStackTrace();
			return;
		}
	}

	public static void addEventListener(String packetType, IEvent event) {
		registeredEvents.add(event);
		ArrayList<IEvent> eventsToPoll = events.get(packetType);
		if (eventsToPoll == null) {
			events.put(packetType, new ArrayList<IEvent>());
			eventsToPoll = new ArrayList<IEvent>();
		}
		eventsToPoll.add(event);
		events.put(packetType, eventsToPoll);
	}

	public static ArrayList<IEvent> registeredEvents = new ArrayList<IEvent>();

	public static void init() {
		addEventListener(Packet.SConnectPacket, new PlayerConnectEvent());
		addEventListener(Packet.SDisconnectPacket, new PlayerDisconnectEvent());
		addEventListener(Packet.SMovePacket, new PlayerMoveEvent());
		addEventListener(Packet.SInitPacket, new s2cInitEvent());
	}

}
