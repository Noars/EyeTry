package tests;

import application.ui.panes.TestPane;
import application.ui.shapes.CircleFixed;
import application.ui.shapes.Cross;
import gaze.GazeEvent;
import gaze.TobiiGazeDeviceManager;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import utils.*;

public class FixedTest {

    TestPane testPane;
    TobiiGazeDeviceManager tobiiGazeDeviceManager;
    Coordinates coordinates;
    Save save;
    AccuracyPrecisionMetrics accuracyMetrics;

    public FixedTest(TestPane testPane, TobiiGazeDeviceManager tobiiGazeDeviceManager, Coordinates coordinates, Save save, Settings settings){
        this.testPane = testPane;
        this.tobiiGazeDeviceManager = tobiiGazeDeviceManager;
        this.coordinates = coordinates;
        this.save = save;

        this.accuracyMetrics = new AccuracyPrecisionMetrics(this.testPane, this.coordinates, this.save, settings, "Test fixe", 3);
    }

    public void createTargetFixedTest(){

        this.save.createNewSave();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        double posXTopCenter = (screenBounds.getWidth() / 2);
        double posYTopCenter = (screenBounds.getHeight() / 5);
        String circleTopCenterName = "Target top center";
        CircleFixed circleTopCenter = new CircleFixed(posXTopCenter, posYTopCenter, 50, Color.GREY, 0.7);
        Cross crossTopCenter = new Cross(posXTopCenter, posYTopCenter);
        this.createGazeEvent(circleTopCenter, crossTopCenter, circleTopCenterName);

        double posXLeftBottom = (screenBounds.getWidth() / 8);
        double posYLeftBottom = ((screenBounds.getHeight() / 10) * 8);
        String circleBottomLeftName = "Target bottom left";
        CircleFixed circleBottomLeft = new CircleFixed(posXLeftBottom, posYLeftBottom, 50, Color.GREY, 0.7);
        Cross crossLeftBottom = new Cross(posXLeftBottom, posYLeftBottom);
        this.createGazeEvent(circleBottomLeft, crossLeftBottom, circleBottomLeftName);

        double posXRightBottom = ((screenBounds.getWidth() / 8) * 7);
        double posYRightBottom = ((screenBounds.getHeight() / 10) * 8);
        String circleBottomRightName = "Target bottom right";
        CircleFixed circleBottomRight = new CircleFixed(posXRightBottom, posYRightBottom, 50, Color.GREY, 0.7);
        Cross crossRightBottom = new Cross(posXRightBottom, posYRightBottom);
        this.createGazeEvent(circleBottomRight, crossRightBottom, circleBottomRightName);

        this.testPane.getChildren().addAll(circleTopCenter, crossTopCenter, circleBottomLeft, crossLeftBottom, circleBottomRight, crossRightBottom);
    }

    public void createGazeEvent(CircleFixed circleTarget, Cross crossTarget, String circleTargetName){
        EventHandler<Event> eventGazeCircleTarget = e -> {
            if (e.getEventType() == GazeEvent.GAZE_ENTERED) {
                crossTarget.startRotateCrossAnimation();
                this.accuracyMetrics.startCalculations(circleTarget, crossTarget, circleTargetName);
            }
            else if (e.getEventType() == GazeEvent.GAZE_EXITED){
                crossTarget.stopRotateCrossAnimation();
                this.accuracyMetrics.stopCalculations();
            }
        };
        circleTarget.addEventHandler(GazeEvent.ANY, eventGazeCircleTarget);
        this.tobiiGazeDeviceManager.addEventFilter(circleTarget);
    }

    public void dispose(){
        this.accuracyMetrics.dispose();
    }
}
