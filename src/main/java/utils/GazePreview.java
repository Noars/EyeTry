package utils;

import application.ui.panes.TestPane;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.util.Duration;

public class GazePreview {

    TestPane testPane;
    Coordinates coordinates;
    Timeline updateGazeVisualTimeline;

    public GazePreview(TestPane testPane, Coordinates coordinates){
        this.testPane = testPane;
        this.coordinates = coordinates;
    }

    public void createGazePreview(){
        Arc gazeCircle = new Arc(this.coordinates.posX, this.coordinates.posY, 50, 50, 0, 360);
        gazeCircle.setFill(Color.TRANSPARENT);
        gazeCircle.setStroke(Color.BLACK);
        gazeCircle.setType(ArcType.OPEN);

        this.testPane.getChildren().add(gazeCircle);
        this.updateGazePreview(gazeCircle);
    }

    public void updateGazePreview(Arc gazeVisual) {
        this.updateGazeVisualTimeline = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            gazeVisual.setCenterX(this.coordinates.posX);
            gazeVisual.setCenterY(this.coordinates.posY);
            gazeVisual.setOpacity(this.coordinates.opacity);
        }));
        this.updateGazeVisualTimeline.setCycleCount(Animation.INDEFINITE);
        this.updateGazeVisualTimeline.play();
    }

    public void stopGazePreview(){
        this.updateGazeVisualTimeline.stop();
        this.updateGazeVisualTimeline.getKeyFrames().clear();
    }
}
