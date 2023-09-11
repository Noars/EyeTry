package gaze;

import javafx.scene.Node;

public interface GazeDeviceManager {

    void destroy();

    void addEventFilter(Node gs);

    void removeEventFilter(Node gs);

    void clear();
}
