package logging;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

	public static void log(String text) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		System.out.println("[" + formatter.format(date) + "] " + text);
	}

}
