package utils;

import application.ui.panes.TestPane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class AccuracyPrecisionMetrics {

    TestPane testPane;
    Coordinates coordinates;
    Save save;
    Settings settings;
    Circle circleTarget;
    Cross crossTarget;
    String circleTargetName;

    private final String nameTest;
    private final int nbTarget;
    private int nbTargetDone = 0;
    private Timeline getAccuracyPrecisionPoints;
    private double distance = 0.0;

    public AccuracyPrecisionMetrics(TestPane testPane, Coordinates coordinates, Save save, Settings settings, String nameTest, int nbTarget){
        this.testPane = testPane;
        this.coordinates = coordinates;
        this.save = save;
        this.settings = settings;
        this.nameTest = nameTest;
        this.nbTarget = nbTarget;

        this.createCalculations();
    }

    public void createCalculations(){
        int frame = this.settings.dwellTime / this.settings.nbPointsToGet;
        this.getAccuracyPrecisionPoints = new Timeline(new KeyFrame(Duration.millis(frame), e -> this.distance += Math.sqrt(Math.pow((this.coordinates.posX - this.circleTarget.getCenterX()),2) + Math.pow((this.coordinates.posY - this.circleTarget.getCenterY()),2))));
        this.getAccuracyPrecisionPoints.setCycleCount(this.settings.nbPointsToGet);
        this.getAccuracyPrecisionPoints.setOnFinished(event -> {
            this.calculationAccuracyPrecision();
            this.testPane.getChildren().removeAll(this.circleTarget, this.crossTarget);
            this.nbTargetDone++;
            this.stopCalculations();

            if (this.nbTargetDone == this.nbTarget){
                this.save.saveMetricsValues();
                this.testPane.returnMain();
            }
        });
        this.save.nameTest = this.nameTest;
    }

    public void calculationAccuracyPrecision(){
        double accuracyPercentage = Math.floor(100 - (this.distance / this.settings.nbPointsToGet));
        double precisionPercentage = Math.floor(100 - (Math.sqrt(Math.pow(this.distance, 2) / this.settings.nbPointsToGet)));

        this.save.nameTarget.add(this.circleTargetName);
        this.save.accuracyMetrics.add(accuracyPercentage);
        this.save.precisionMetrics.add(precisionPercentage);
    }

    public void startCalculations(Circle circleTarget, Cross crossTarget, String circleTargetName){
        this.circleTarget = circleTarget;
        this.crossTarget = crossTarget;
        this.circleTargetName = circleTargetName;

        this.getAccuracyPrecisionPoints.playFromStart();
    }

    public void stopCalculations(){
        this.getAccuracyPrecisionPoints.pause();
        this.distance = 0.0;
    }

    public void dispose(){
        this.getAccuracyPrecisionPoints.stop();
        this.getAccuracyPrecisionPoints.getKeyFrames().clear();
    }
}
