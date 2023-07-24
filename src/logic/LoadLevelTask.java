package logic;

import event.EventHandler;
import main.Spectre;
import renderEngine.Loader;

public class LoadLevelTask extends Scheduler {

    public LoadLevelTask(long delay) {
        super(delay);
    }
    @Override
    public void run() {
        if(EventHandler.levelLoaded) {
            Loader.loadLevel(Spectre.levelFile);
        }
    }

    @Override
    public boolean shouldRepeat() {
        return !EventHandler.levelLoaded;
    }
}
