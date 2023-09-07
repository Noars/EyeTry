package gaze;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import lombok.extern.slf4j.Slf4j;
import tobii.Tobii;
import utils.Coordinates;

import java.awt.*;

@Slf4j
public class PositionPollerRunnable implements Runnable{

    Coordinates coordinates;
    private final TobiiGazeDeviceManager tobiiGazeDeviceManager;
    public transient boolean stopRequested = false;
    public transient boolean pauseRequested = true;

    public PositionPollerRunnable(final TobiiGazeDeviceManager tobiiGazeDeviceManager, Coordinates coordinates) throws AWTException {
        this.coordinates = coordinates;
        this.tobiiGazeDeviceManager = tobiiGazeDeviceManager;
    }

    @Override
    public void run() {
        while (!stopRequested) {
            try {
                if (!pauseRequested) {
                    poll();
                }
            } catch (final RuntimeException e) {
                System.out.println("Exception while polling position of main.gaze -> " + e);
            }
            // sleep is mandatory to avoid too much calls to gazePosition()
            try {
                Thread.sleep(10);
            } catch (InterruptedException | RuntimeException e) {
                System.out.println("Exception while sleeping until next poll -> " + e);
            }
        }
    }

    private void poll(){
        final float[] pointAsFloatArray = Tobii.gazePosition();

        final float xRatio = pointAsFloatArray[0];
        final float yRatio = pointAsFloatArray[1];

        final Rectangle2D screenDimension = Screen.getPrimary().getBounds();
        final double positionX = xRatio * screenDimension.getWidth();
        final double positionY = yRatio * screenDimension.getHeight();

        double offsetX = 0;
        double offsetY = 0;

        if ( xRatio != 0.5 || yRatio != 0.5 ) {
            final Point2D point = new Point2D(positionX + offsetX, positionY + offsetY);
            this.coordinates.posX = (int) point.getX();
            this.coordinates.posY = (int) point.getY();
        }
    }
}
