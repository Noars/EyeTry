package application.ui.shapes;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CircleMoving extends Circle {

    Timeline animation;

    public CircleMoving(double posX, double posY, int radius, Color color, double opacity, Cross cross, String moveType, double xTranslation, double yTranslation){
        super(posX, posY, radius, color);
        this.setOpacity(opacity);

        switch (moveType){
            case "horizontal" -> this.createHorizontalMove(cross, xTranslation);
            case "vertical" -> this.createVerticalMove(cross, yTranslation);
            case "diagonal" -> this.createDiagonalMove(cross, xTranslation, yTranslation);
            default -> log.warn("Error when choosing move type !");
        }
    }

    public void createHorizontalMove(Cross cross, double xTranslation){
        this.animation = new Timeline();
        this.animation.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(2000),
                        new KeyValue(this.translateXProperty(), xTranslation),
                        new KeyValue(cross.translateXProperty(), xTranslation)),
                new KeyFrame(Duration.millis(2000),
                        new KeyValue(this.translateXProperty(), 0),
                        new KeyValue(cross.translateXProperty(), 0))
        );
        this.animation.setCycleCount(Animation.INDEFINITE);
        this.animation.setAutoReverse(true);
        this.animation.playFromStart();
    }

    public void createVerticalMove(Cross cross, double yTranslation){
        this.animation = new Timeline();
        this.animation.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(2000),
                        new KeyValue(this.translateYProperty(), yTranslation),
                        new KeyValue(cross.translateYProperty(), yTranslation)),
                new KeyFrame(Duration.millis(2000),
                        new KeyValue(this.translateYProperty(), 0),
                        new KeyValue(cross.translateYProperty(), 0))
        );
        this.animation.setCycleCount(Animation.INDEFINITE);
        this.animation.setAutoReverse(true);
        this.animation.playFromStart();
    }

    public void createDiagonalMove(Cross cross, double xTranslation, double yTranslation){
        this.animation = new Timeline();
        this.animation.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(2000),
                        new KeyValue(this.translateXProperty(), xTranslation),
                        new KeyValue(this.translateYProperty(), yTranslation),
                        new KeyValue(cross.translateXProperty(), xTranslation),
                        new KeyValue(cross.translateYProperty(), yTranslation)),
                new KeyFrame(Duration.millis(2000),
                        new KeyValue(this.translateXProperty(), 0),
                        new KeyValue(this.translateYProperty(), 0),
                        new KeyValue(cross.translateXProperty(), 0),
                        new KeyValue(cross.translateYProperty(), 0))
        );
        this.animation.setCycleCount(Animation.INDEFINITE);
        this.animation.setAutoReverse(true);
        this.animation.playFromStart();
    }
}
