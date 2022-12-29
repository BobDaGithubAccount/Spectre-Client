package networking;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import org.lwjgl.util.vector.Vector3f;

import lib.json.JSONObject;
import logging.Logger;
import renderEngine.MasterRenderer;

public class NetworkingThread extends Thread {
	
	Socket socket;
	InputStream is;
	OutputStream os;
	
	boolean isRunning = true;
	Timer timer = new Timer();
	public long a = 0;
	class TimeoutTask extends TimerTask {
		long b;
		TimeoutTask(long a) {
			this.b = a;
		}
		@Override
		public void run() {
			if((a==b) || ((a-b) > 8192)) {
				try {
					isRunning = false;
					is.close();
					os.close();
					socket.close();
					Logger.log("Connection timed out!");
					System.exit(0);
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
		}
	}
	
	class TickTask extends TimerTask {
		@Override
		public void run() {
			sendJSON(Packet.CPingPacket());
			timer.schedule(new TickTask(), 1000);
		}
	}
	
	class MoveTask extends TimerTask {
		@Override
		public void run() {
			Vector3f position = MasterRenderer.camera.getPosition();
			sendJSON(Packet.CMovePacket(position.x, position.y, position.z, MasterRenderer.camera.getPitch(), MasterRenderer.camera.getYaw(), MasterRenderer.camera.getRoll()));
			timer.schedule(new MoveTask(), 50);
		}
	}
	
	@Override
	public void run() {
	    try {
			socket = new Socket("localhost",3000);
			Logger.log("Connection created!");
			is = socket.getInputStream();
			os = socket.getOutputStream();
			
			sendJSON(Packet.CConnectPacket());
			
			timer.schedule(new TickTask(), 1000);
			timer.schedule(new MoveTask(), 50);
			while(socket.isConnected() && socket.isBound() && !socket.isClosed()) {
				a++;
				timer.schedule(new TimeoutTask(a), 5000);
				JSONObject json = receiveJSON();
				if(json==null) {continue;}
				System.out.println(json.toString(1));
			}
			
			if(!isRunning) {
				return;
			}
			
			is.close();
			os.close();
			socket.close();
			Logger.log("Connection destroyed!");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public JSONObject receiveJSON() {
		try {
			if(is==null) {
				return null;
			}
			ObjectInputStream i = new ObjectInputStream(is);
			String line = null;
			line = (String) i.readObject();
			JSONObject jsonObject = new JSONObject(line);
			return jsonObject;
		} catch (Exception e) {
			return null;
		}
	}
	
	public void sendJSON(JSONObject jsonObject) {
		try {
			if(os==null) {
				return;
			}
			ObjectOutputStream o = new ObjectOutputStream(os);
			Logger.log(jsonObject.toString());
	        o.writeObject(jsonObject.toString());
	        os.flush();
		} catch(Exception e) {}
	}
	
}
