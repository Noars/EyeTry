package tests;

import application.ui.panes.TestPane;
import application.ui.shapes.CircleMoving;
import application.ui.shapes.Cross;
import gaze.GazeEvent;
import gaze.TobiiGazeDeviceManager;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import utils.*;

public class MovingTest {

    TestPane testPane;
    TobiiGazeDeviceManager tobiiGazeDeviceManager;
    Coordinates coordinates;
    Save save;
    AccuracyPrecisionMetrics accuracyMetrics;

    public MovingTest(TestPane testPane, TobiiGazeDeviceManager tobiiGazeDeviceManager, Coordinates coordinates, Save save, Settings settings){
        this.testPane = testPane;
        this.tobiiGazeDeviceManager = tobiiGazeDeviceManager;
        this.coordinates = coordinates;
        this.save = save;

        this.accuracyMetrics = new AccuracyPrecisionMetrics(this.testPane, this.coordinates, this.save, settings, "Test mouvant", 3);
    }

    public void createTargetMovingTest(){
        this.save.createNewSave();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        double posXHorizontal = (screenBounds.getWidth() / 10);
        double posYHorizontal = (screenBounds.getHeight() / 2);
        double posHorizontalTranslation = (screenBounds.getWidth() / 3);
        String circleHorizontalName = "Target Horizontal";
        Cross crossHorizontal = new Cross(posXHorizontal, posYHorizontal);
        CircleMoving circleHorizontal = new CircleMoving(posXHorizontal, posYHorizontal, 50, Color.GREY, 0.7, crossHorizontal, "horizontal", posHorizontalTranslation, 0);
        this.createGazeEvent(circleHorizontal, crossHorizontal, circleHorizontalName);

        double posXVertical = (screenBounds.getWidth() / 2);
        double posYVertical = (screenBounds.getHeight() / 5);
        double posVerticalTranslation = (screenBounds.getHeight() / 3) * 2;
        String circleVerticalName = "Target Vertical";
        Cross crossVertical = new Cross(posXVertical, posYVertical);
        CircleMoving circleVertical = new CircleMoving(posXVertical, posYVertical, 50, Color.GREY, 0.7, crossVertical, "vertical", 0, posVerticalTranslation);
        this.createGazeEvent(circleVertical, crossVertical, circleVerticalName);

        double posXDiagonal = (screenBounds.getWidth() / 10) * 6;
        double posYDiagonal = (screenBounds.getHeight() / 5);
        String circleDiagonalName = "Target Diagonal";
        Cross crossDiagonal = new Cross(posXDiagonal, posYDiagonal);
        CircleMoving circleDiagonal = new CircleMoving(posXDiagonal, posYDiagonal, 50, Color.GREY, 0.7, crossDiagonal, "diagonal", posHorizontalTranslation, posVerticalTranslation);
        this.createGazeEvent(circleDiagonal, crossDiagonal, circleDiagonalName);

        this.testPane.getChildren().addAll(circleHorizontal, crossHorizontal, circleVertical, crossVertical, circleDiagonal, crossDiagonal);
    }

    public void createGazeEvent(Circle circleTarget, Cross crossTarget, String circleTargetName){
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
