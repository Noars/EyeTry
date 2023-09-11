package gaze;

import tobii.Tobii;
import utils.Coordinates;

import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TobiiGazeDeviceManager extends AbstractGazeDeviceManager{

    Coordinates coordinates;
    PositionPollerRunnable positionPollerRunnable;
    ExecutorService executorService;


    public TobiiGazeDeviceManager(Coordinates coordinates){
        super();
        this.coordinates = coordinates;
    }

    public void setPause(boolean status) {
        this.positionPollerRunnable.pauseRequested = status;
    }

    public void init(){

        Tobii.gazePosition();

        try {
            this.positionPollerRunnable = new PositionPollerRunnable(this, this.coordinates);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        this.executorService = Executors.newFixedThreadPool(4,
                (Runnable r) -> {
                    Thread t = new Thread(r);
                    t.setDaemon(true);
                    return t;
                }
        );
        this.executorService.submit(this.positionPollerRunnable);
    }

    public void destroy() {
        this.positionPollerRunnable.stopRequested = true;
        this.executorService.shutdown();
    }
}
