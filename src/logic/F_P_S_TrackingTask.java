package logic;

import main.MainGameLoop;
import renderEngine.DisplayManager;

public class F_P_S_TrackingTask extends Scheduler {

	public int fps = 60;
	
	public F_P_S_TrackingTask(long delay) {
		super(delay);
	}
	
	@Override
	public void run() {
		MainGameLoop.gameClock.tick();
		fps = MainGameLoop.fps;
		MainGameLoop.fps = 0;
		DisplayManager.updateTitleWithFPS(fps, MainGameLoop.gameClock.tps);
		this.setEpochTimeToRun(this.getEpochTimeToRun()+this.delay);
	}

	@Override
	public boolean shouldRepeat() {
		return true;
	}

}
