package utils;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Cross extends Group {

    Timeline rotateCross;
    public Cross() {
        Line horizontalLine = new Line();
        horizontalLine.setStartX(-25);
        horizontalLine.setEndX(25);

        Line verticalLine = new Line();
        verticalLine.setStartY(-25);
        verticalLine.setEndY(25);

        Circle center = new Circle();
        center.setRadius(2);

        getChildren().addAll(horizontalLine, verticalLine, center);
        setMouseTransparent(true);

        this.createRotateCrossAnimation();
    }

    public void createRotateCrossAnimation(){
        this.rotateCross = new Timeline();
        this.rotateCross.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(500), // set start position at 0
                        new KeyValue(this.rotateProperty(), 0),
                        new KeyValue(this.rotateProperty(), 360)));
        this.rotateCross.setCycleCount(Timeline.INDEFINITE);
    }

    public void startRotateCrossAnimation(){
        this.rotateCross.play();
    }

    public void stopRotateCrossAnimation(){
        this.rotateCross.pause();
    }
}
