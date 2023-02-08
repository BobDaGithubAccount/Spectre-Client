package networking;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.lwjgl.util.vector.Vector3f;

import event.EventHandler;
import lib.json.JSONObject;
import logging.Logger;
import renderEngine.MasterRenderer;

public class NetworkingThread extends Thread {
	
	Socket socket;
	InputStream is;
	OutputStream os;
	Timer timer = new Timer();
	
	class MoveTask extends TimerTask {
		@Override
		public void run() {
			Vector3f position = MasterRenderer.camera.getPosition();
			JSONObject json = Packet.CMovePacket(position.x, position.y, position.z, MasterRenderer.camera.getPitch(), MasterRenderer.camera.getYaw(), MasterRenderer.camera.getRoll());
			sendJSON(json);
			timer.schedule(new MoveTask(), 50);
		}
	}
	
	class TickTask extends TimerTask {
		@Override
		public void run() {
			sendJSON(Packet.CPingPacket());
			timer.schedule(new TickTask(), 1000);
		}
	}
	
	public long lastPingReceived = new Date().getTime();
	class TimeoutTask extends TimerTask {
		@Override
		public void run() {
			if((new Date().getTime() - lastPingReceived) > 10000) {
				System.out.println("Connection timed out!");
				System.exit(0);
				return;
			}
			timer.schedule(new TimeoutTask(), 1000);
		}
	}
	
	//TODO make networking more efficient
	@Override
	public void run() {
	    try {
			socket = new Socket("localhost",3000);
			Logger.log("Connection created!");
			is = socket.getInputStream();
			os = socket.getOutputStream();
			sendJSON(Packet.CConnectPacket(UUID.randomUUID().toString()));
			timer.schedule(new MoveTask(), 50);
			timer.schedule(new TickTask(), 1000);
			timer.schedule(new TimeoutTask(), 1000);
			while(socket.isConnected() && socket.isBound() && !socket.isClosed()) {
				JSONObject json = receiveJSON();
				if(json==null) {continue;}
				System.out.println(json.toString(1));
				if(json.getString(Packet.packet_type).equals(Packet.SPingPacket)) {
					lastPingReceived = new Date().getTime();
				}
				EventHandler.pollPacket(json);
			}
			sendJSON(Packet.CDisconnectPacket());
			is.close();
			os.close();
			socket.close();
			Logger.log("Connection destroyed!");
			System.exit(0);
		} catch (Exception e) {
			sendJSON(Packet.CDisconnectPacket());
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public void sendJSON(JSONObject json) {
		if(!(socket.isConnected() && socket.isBound() && !socket.isClosed())) {
			return;
		}
		try {
			ObjectOutputStream o = new ObjectOutputStream(os);
			o.writeUTF(json.toString());
			o.flush();
			os.flush();
		} catch(Exception ignored) {}
	}

	public JSONObject receiveJSON() {
		if(!(socket.isConnected() && socket.isBound() && !socket.isClosed())) {
			return null;
		}
		try {
			ObjectInputStream i = new ObjectInputStream(is);
			String line;
			line = i.readUTF();
			return new JSONObject(line);
		} catch (Exception ignored) {
			return null;
		}
	}
	
}
