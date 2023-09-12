package utils;

import application.ui.panes.TestPane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
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
    private Point2D[] listPointsGet;
    private int index = 0;
    private double targetPosX;
    private double targetPosY;
    private Timeline getAccuracyPrecisionPoints;

    public AccuracyPrecisionMetrics(TestPane testPane, Coordinates coordinates, Save save, Settings settings, String nameTest, int nbTarget){
        this.testPane = testPane;
        this.coordinates = coordinates;
        this.save = save;
        this.settings = settings;
        this.nameTest = nameTest;
        this.nbTarget = nbTarget;
        this.listPointsGet = new Point2D[this.settings.nbPointsToGet];

        this.createCalculations();
    }

    public void createCalculations(){
        this.getAccuracyPrecisionPoints = new Timeline(new KeyFrame(Duration.millis(this.settings.dwellTime), e -> {
            this.listPointsGet[this.index] = new Point2D(this.coordinates.posX, this.coordinates.posY);
            this.index++;
        }));
        this.getAccuracyPrecisionPoints.setCycleCount(this.settings.nbPointsToGet);
        this.getAccuracyPrecisionPoints.setOnFinished(event -> {
            this.calculationAccuracyPrecision();
            this.nbTargetDone++;
            this.testPane.getChildren().removeAll(this.circleTarget, this.crossTarget);
            this.stopCalculations();

            if (this.nbTargetDone == this.nbTarget){
                this.save.createSaveFile();
            }
        });
        this.save.nameTest = this.nameTest;
    }

    public void calculationAccuracyPrecision(){
        double distance = 0.0;
        for (int i=0; i<this.listPointsGet.length; i++){
            distance += Math.sqrt(Math.pow((this.listPointsGet[0].getX() - this.targetPosX),2) + Math.pow((this.listPointsGet[0].getY() - this.targetPosY),2));
        }

        double accuracyPercentage = Math.floor(100 - (distance / this.settings.nbPointsToGet));
        double precisionPercentage = Math.floor(100 - (Math.sqrt(Math.pow(distance, 2) / this.settings.nbPointsToGet)));

        this.save.nameTarget.add(this.circleTargetName);
        this.save.accuracyMetrics.add(accuracyPercentage);
        this.save.precisionMetrics.add(precisionPercentage);
    }

    public void startCalculations(Circle circleTarget, Cross crossTarget, String circleTargetName){
        this.circleTarget = circleTarget;
        this.crossTarget = crossTarget;
        this.circleTargetName = circleTargetName;

        this.targetPosX = this.circleTarget.getCenterX();
        this.targetPosY = this.circleTarget.getCenterY();
        this.getAccuracyPrecisionPoints.playFromStart();
    }

    public void stopCalculations(){
        this.getAccuracyPrecisionPoints.pause();
        this.listPointsGet = new Point2D[this.settings.nbPointsToGet];
        this.index = 0;
    }

    public void dispose(){
        this.getAccuracyPrecisionPoints.stop();
        this.getAccuracyPrecisionPoints.getKeyFrames().clear();
    }
}
