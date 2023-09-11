package gaze;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * Created by schwab on 04/10/2017.
 */
@Slf4j
public abstract class AbstractGazeDeviceManager implements GazeDeviceManager {

    @Getter
    private final Map<IdentityKey<Node>, GazeInfos> shapesEventFilter = Collections.synchronizedMap(new HashMap<>());
    @Getter
    private final Map<IdentityKey<Node>, GazeInfos> shapesEventHandler = Collections.synchronizedMap(new HashMap<>());
    private final List<Node> toRemove = new LinkedList<>();
    private final List<Node> toAdd = new LinkedList<>();

    public AbstractGazeDeviceManager() {}

    @Override
    public abstract void destroy();

    @Override
    public void addEventFilter(Node gs) {
        toAdd.add(gs);
    }

    public void add() {
        synchronized (shapesEventFilter) {
            List<Node> temp = new LinkedList<>(toAdd);
            for (Node node : temp) {
                shapesEventFilter.put(new IdentityKey<>(node), new GazeInfos(node));
                toAdd.remove(node);
            }
        }
    }

    @Override
    public void removeEventFilter(Node gs) {
        toRemove.add(gs);
        if (gs instanceof Pane) {
            for (Node child : ((Pane) gs).getChildren()) {
                removeEventFilter(child);
            }
        }
    }

    public void delete() {
        synchronized (shapesEventFilter) {
            List<Node> temp = new LinkedList<>(toRemove);
            for (Node node : temp) {
                GazeInfos removed = shapesEventFilter.remove(new IdentityKey<>(node));
                if (removed == null) {
                    log.info("EventFilter to remove not found");
                } else {
                    if (removed.isOnGaze() || removed.isOnMouse()) {
                        Platform.runLater(
                                () ->
                                        removed.getNode().fireEvent(new GazeEvent(GazeEvent.GAZE_EXITED, System.currentTimeMillis(), 0, 0))
                        );
                    }
                }
                toRemove.remove(node);
            }
        }
    }

    /**
     * Clear all Nodes in both EventFilter and EventHandler. There is no more event after this function is called
     */
    @Override
    public void clear() {
        synchronized (shapesEventFilter) {
            shapesEventFilter.clear();
            shapesEventHandler.clear();
        }
    }

    public synchronized void onGazeUpdate(Point2D gazePositionOnScreen, String event) {
        final double positionX = gazePositionOnScreen.getX();
        final double positionY = gazePositionOnScreen.getY();
        updatePosition(positionX, positionY, event);
    }

    void updatePosition(double positionX, double positionY, String event) {
        add();
        delete();

        synchronized (shapesEventFilter) {
            Collection<GazeInfos> c = shapesEventFilter.values();
            for (GazeInfos gi : c) {
                eventFire(positionX, positionY, gi, event);
            }
        }
    }

    public boolean contains(Node node, double positionX, double positionY) {
        Point2D localPosition = node.screenToLocal(positionX, positionY);
        if (localPosition != null) {
            return node.contains(localPosition.getX(), localPosition.getY());
        }
        return false;
    }


    public void eventFire(double positionX, double positionY, GazeInfos gi, String event) {
        Node node = gi.getNode();
        if (!node.isDisable()) {

            Point2D localPosition = node.screenToLocal(positionX, positionY);

            if (localPosition != null && contains(node, positionX, positionY)) {
                if (event.equals("gaze")) {
                    if (gi.isOnGaze()) {
                        Platform.runLater(
                                () ->
                                        node.fireEvent(new GazeEvent(GazeEvent.GAZE_MOVED, gi.getTime(), localPosition.getX(), localPosition.getY()))
                        );
                    } else {

                        gi.setOnGaze(true);
                        gi.setTime(System.currentTimeMillis());
                        Platform.runLater(
                                () ->
                                        node.fireEvent(new GazeEvent(GazeEvent.GAZE_ENTERED, gi.getTime(), localPosition.getX(), localPosition.getY()))
                        );
                    }
                } else {
                    if (gi.isOnMouse()) {
                        Platform.runLater(
                                () ->
                                        node.fireEvent(new GazeEvent(GazeEvent.GAZE_MOVED, gi.getTime(), localPosition.getX(), localPosition.getY()))

                        );
                    } else {

                        gi.setOnMouse(true);
                        gi.setTime(System.currentTimeMillis());
                        Platform.runLater(
                                () ->
                                        node.fireEvent(new GazeEvent(GazeEvent.GAZE_ENTERED, gi.getTime(), localPosition.getX(), localPosition.getY()))

                        );
                    }
                }
            } else {
                if (event.equals("gaze")) {
                    if (gi.isOnGaze()) {
                        gi.setOnGaze(false);
                        gi.setTime(-1);
                        if (localPosition != null) {
                            Platform.runLater(
                                    () ->
                                            node.fireEvent(new GazeEvent(GazeEvent.GAZE_EXITED, gi.getTime(), localPosition.getX(), localPosition.getY()))
                            );
                        }
                    }
                } else {
                    if (gi.isOnMouse()) {
                        gi.setOnMouse(false);
                        gi.setTime(-1);
                        if (localPosition != null) {
                            Platform.runLater(
                                    () ->
                                            node.fireEvent(new GazeEvent(GazeEvent.GAZE_EXITED, gi.getTime(), localPosition.getX(), localPosition.getY()))

                            );
                        }
                    }
                }

            }
        }
    }

}
