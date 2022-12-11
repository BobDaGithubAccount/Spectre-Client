package logic;

import java.util.Calendar;
import java.util.Date;

public abstract class Scheduler {
	
	public long delay;
	public Calendar calendar;
	public long epochTimeToRun;
	
	public Scheduler(long delay) {
		this.delay = delay;
		calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		this.epochTimeToRun = calendar.getTimeInMillis() + delay;
	}
	
	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public long getEpochTimeToRun() {
		return epochTimeToRun;
	}

	public void setEpochTimeToRun(long epochTimeToRun) {
		this.epochTimeToRun = epochTimeToRun;
	}

	public abstract void run();
	
	public abstract boolean shouldRepeat();
	
}
