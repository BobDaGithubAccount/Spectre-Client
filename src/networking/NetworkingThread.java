package networking;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import lib.json.JSONObject;
import logging.Logger;

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
	
	@Override
	public void run() {
	    try {
			socket = new Socket("localhost",3000);
			Logger.log("Connection created!");
			is = socket.getInputStream();
			os = socket.getOutputStream();
			
			sendJSON(Packet.CConnectPacket());
			
			while(socket.isConnected() && socket.isBound() && !socket.isClosed()) {
				a++;
				timer.schedule(new TimeoutTask(a), 5000);
				JSONObject json = receiveJSON();
				if(json==null) {continue;}
				System.out.println(json.toString());
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
	        InputStream in = socket.getInputStream();
	        ObjectInputStream i = new ObjectInputStream(in);
	        String line = null;
	        line = (String) i.readObject();
	        JSONObject jsonObject = new JSONObject(line);
	        return jsonObject;
		} catch(Exception e) {
			return null;
		}
    }
	
	public void sendJSON(JSONObject jsonObject) {
		try {
			ObjectOutputStream o = new ObjectOutputStream(os);
	        o.writeObject(jsonObject.toString());
	        os.flush();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
