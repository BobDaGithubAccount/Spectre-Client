package logic;

public class TPS_Task extends Scheduler {

	public int tps = 20;
	private int tpsCounter = 0;
	
	public TPS_Task(long delay) {
		super(delay);
	}

	public void tick() {
		tps = tpsCounter;
		tpsCounter = 0;
	}
	
	@Override
	public void run() {
		tpsCounter++;
		
		//TODO
		this.setEpochTimeToRun(this.getEpochTimeToRun()+this.delay);
	}

	@Override
	public boolean shouldRepeat() {
		return true;
	}

}
