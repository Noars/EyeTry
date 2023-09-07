package gaze;

import tobii.Tobii;
import utils.Coordinates;

import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TobiiGazeDeviceManager{

    Coordinates coordinates;
    public PositionPollerRunnable positionPollerRunnable;

    public TobiiGazeDeviceManager(Coordinates coordinates){
        super();
        this.coordinates = coordinates;
    }

    public void setPause(boolean status) {
        positionPollerRunnable.pauseRequested = status;
    }

    public void init(){
        System.out.println("Init Tobii Gaze Device Manager");

        Tobii.gazePosition();

        try {
            positionPollerRunnable = new PositionPollerRunnable(this, coordinates);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        ExecutorService executorService = Executors.newFixedThreadPool(4,
                (Runnable r) -> {
                    Thread t = new Thread(r);
                    t.setDaemon(true);
                    return t;
                }
        );
        executorService.submit(positionPollerRunnable);
    }

    /*@Override
    public void destroy() {
        positionPollerRunnable.setStopRequested(true);
        ExecutorService executorService = this.executorService;
        if (executorService != null) {
            executorService.shutdown();
        }
    }*/
}
