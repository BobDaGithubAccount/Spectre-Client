package logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

	private static FileWriter fw;
	
	public static void init() {
		try {
		File jarFile = new File(Logger.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		File logFile = new File(jarFile.getParent() + "/logs.txt");
		logFile.delete();
		logFile.createNewFile();
		fw = new FileWriter(logFile);
		log("----------");
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public static void shutdown() {
		try {
			log("----------");
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void log(String text) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		System.out.println("[" + formatter.format(date) + "] " + text);
		try {
			fw.append(text + System.lineSeparator());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
