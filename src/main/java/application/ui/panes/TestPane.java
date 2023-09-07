package application.ui.panes;

import application.Main;
import gaze.TobiiGazeDeviceManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.Coordinates;

public class TestPane extends Pane {

    Main main;
    Stage primaryStage;
    TobiiGazeDeviceManager tobiiGazeDeviceManager;
    Coordinates coordinates;

    public TestPane(Main main, Stage primaryStage, Coordinates coordinates, TobiiGazeDeviceManager tobiiGazeDeviceManager) {
        super();
        this.main = main;
        this.primaryStage = primaryStage;
        this.coordinates = coordinates;
        this.tobiiGazeDeviceManager = tobiiGazeDeviceManager;
    }

    public void startTest(){
        this.tobiiGazeDeviceManager.setPause(false);

        Arc gazeCircle = new Arc(this.coordinates.posX, this.coordinates.posY, 50, 50, 0, 360);
        gazeCircle.setFill(Color.TRANSPARENT);
        gazeCircle.setStroke(Color.BLACK);
        gazeCircle.setType(ArcType.OPEN);

        this.getChildren().add(gazeCircle);
        this.updateGazeCircle(gazeCircle);
    }

    public void updateGazeCircle(Arc arc) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), ev -> {
            arc.setCenterX(this.coordinates.posX);
            arc.setCenterY(this.coordinates.posY);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
