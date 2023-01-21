package event;

import event.events.PlayerConnectEvent;
import event.events.PlayerMoveEvent;
import lib.json.JSONObject;
import networking.Packet;

import java.util.ArrayList;
import java.util.HashMap;

public class EventHandler {
    public static HashMap<String, ArrayList<IEvent>> events = new HashMap<String, ArrayList<IEvent>>();

    public static void pollPacket(JSONObject json) {
        try {
            ArrayList<IEvent> eventsToPoll = events.get(json.getString(Packet.packet_type));
            if (eventsToPoll == null) {
                return;
            }
            for (IEvent eventHook : eventsToPoll) {
                boolean shouldCancel = eventHook.run(json);
                if (shouldCancel) {
                    break;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public static void addEventListener(String packetType, IEvent event) {
        registeredEvents.add(event);
        ArrayList<IEvent> eventsToPoll = events.get(packetType);
        if(eventsToPoll==null) {
            events.put(packetType, new ArrayList<IEvent>());
            eventsToPoll = new ArrayList<IEvent>();
        }
        eventsToPoll.add(event);
        events.put(packetType, eventsToPoll);
    }

    public static ArrayList<IEvent> registeredEvents = new ArrayList<IEvent>();

    public static void init() {
    	addEventListener(Packet.SConnectPacket, new PlayerConnectEvent());
        addEventListener(Packet.SMovePacket, new PlayerMoveEvent());
    }

}
