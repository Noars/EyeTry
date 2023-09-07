package gaze;

import lombok.Getter;
import utils.Coordinates;

public class GazeDeviceManagerFactory {

    @Getter
    public static final GazeDeviceManagerFactory instance = new GazeDeviceManagerFactory();

    private GazeDeviceManagerFactory() {
    }

    public TobiiGazeDeviceManager createNewGazeListener(Coordinates coordinates) {
        final TobiiGazeDeviceManager tobiiGazeDeviceManager;
        tobiiGazeDeviceManager = new TobiiGazeDeviceManager(coordinates);

        tobiiGazeDeviceManager.init();
        return tobiiGazeDeviceManager;
    }
}
