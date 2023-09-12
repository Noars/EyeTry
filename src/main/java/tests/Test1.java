package tests;

import application.ui.panes.TestPane;
import gaze.GazeEvent;
import gaze.TobiiGazeDeviceManager;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import utils.*;

public class Test1 {

    TestPane testPane;
    TobiiGazeDeviceManager tobiiGazeDeviceManager;
    Coordinates coordinates;
    Save save;
    AccuracyPrecisionMetrics accuracyMetrics;

    public Test1(TestPane testPane, TobiiGazeDeviceManager tobiiGazeDeviceManager, Coordinates coordinates, Save save, Settings settings){
        this.testPane = testPane;
        this.tobiiGazeDeviceManager = tobiiGazeDeviceManager;
        this.coordinates = coordinates;
        this.save = save;

        this.accuracyMetrics = new AccuracyPrecisionMetrics(this.testPane, this.coordinates, this.save, settings, "Test1", 3);
    }

    public void createTargetTest1(){

        this.save.createNewSave();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        double posXTopCenter = (screenBounds.getWidth() / 2);
        double posYTopCenter = (screenBounds.getHeight() / 5);

        Circle circleTopCenter = new Circle(posXTopCenter, posYTopCenter, 50);
        String circleTopCenterName = "Target top center";
        circleTopCenter.setFill(Color.GREY);
        circleTopCenter.setOpacity(0.7);

        Cross crossTopCenter = new Cross();
        crossTopCenter.setLayoutX(posXTopCenter);
        crossTopCenter.setLayoutY(posYTopCenter);

        this.createRotateEvent(circleTopCenter, crossTopCenter, circleTopCenterName);

        double posXLeftBottom = (screenBounds.getWidth() / 8);
        double posYLeftBottom = ((screenBounds.getHeight() / 10) * 8);

        Circle circleBottomLeft = new Circle(posXLeftBottom, posYLeftBottom, 50);
        String circleBottomLeftName = "Target bottom left";
        circleBottomLeft.setFill(Color.GREY);
        circleBottomLeft.setOpacity(0.7);

        Cross crossLeftBottom = new Cross();
        crossLeftBottom.setLayoutX(posXLeftBottom);
        crossLeftBottom.setLayoutY(posYLeftBottom);

        this.createRotateEvent(circleBottomLeft, crossLeftBottom, circleBottomLeftName);

        double posXRightBottom = ((screenBounds.getWidth() / 8) * 7);
        double posYRightBottom = ((screenBounds.getHeight() / 10) * 8);

        Circle circleBottomRight = new Circle(posXRightBottom, posYRightBottom, 50);
        String circleBottomRightName = "Target bottom right";
        circleBottomRight.setFill(Color.GREY);
        circleBottomRight.setOpacity(0.7);

        Cross crossRightBottom = new Cross();
        crossRightBottom.setLayoutX(posXRightBottom);
        crossRightBottom.setLayoutY(posYRightBottom);

        this.createRotateEvent(circleBottomRight, crossRightBottom, circleBottomRightName);

        this.testPane.getChildren().addAll(circleTopCenter, crossTopCenter, circleBottomLeft, crossLeftBottom, circleBottomRight, crossRightBottom);
    }

    public void createRotateEvent(Circle circleTarget, Cross crossTarget, String circleTargetName){
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
