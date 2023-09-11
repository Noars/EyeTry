package utils;

import application.ui.panes.TestPane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class AccuracyMetrics {

    TestPane testPane;
    Coordinates coordinates;
    Save save;
    Circle circleTarget;
    Cross crossTarget;
    String circleTargetName;

    private final String nameTest;
    private final int nbPointsToGet = 10;
    private final int nbTarget;
    private int nbTargetDone = 0;
    private Point2D[] listPointsGet;
    private int index = 0;
    private double targetPosX;
    private double targetPosY;
    private Timeline getAccuracyPoints;

    public  AccuracyMetrics(TestPane testPane, Coordinates coordinates, Save save, String nameTest, int nbTarget){
        this.testPane = testPane;
        this.coordinates = coordinates;
        this.save = save;
        this.nameTest = nameTest;
        this.nbTarget = nbTarget;
        this.listPointsGet = new Point2D[this.nbPointsToGet];

        this.createCalculations();
    }

    public void createCalculations(){
        this.getAccuracyPoints = new Timeline(new KeyFrame(Duration.millis(500), e -> {
            this.listPointsGet[this.index] = new Point2D(this.coordinates.posX, this.coordinates.posY);
            this.index++;
        }));
        this.getAccuracyPoints.setCycleCount(10);
        this.getAccuracyPoints.setOnFinished(event -> {
            this.calculationAccuracy();
            this.nbTargetDone++;
            this.testPane.getChildren().removeAll(this.circleTarget, this.crossTarget);
            this.stopCalculations();

            if (this.nbTargetDone == this.nbTarget){
                this.save.createSaveFile();
            }
        });
        this.save.nameTest = this.nameTest;
    }

    public void calculationAccuracy(){
        double distance = 0.0;
        for (int i=0; i<this.listPointsGet.length; i++){
            distance += Math.sqrt(Math.pow((this.listPointsGet[0].getX() - this.targetPosX),2) + Math.pow((this.listPointsGet[0].getY() - this.targetPosY),2));
        }
        distance = distance / this.nbPointsToGet;
        double distancePercentage = Math.floor(100 - distance);
        this.save.nameTarget.add(this.circleTargetName);
        this.save.metrics.add(distancePercentage);
    }

    public void startCalculations(Circle circleTarget, Cross crossTarget, String circleTargetName){
        this.circleTarget = circleTarget;
        this.crossTarget = crossTarget;
        this.circleTargetName = circleTargetName;

        this.targetPosX = this.circleTarget.getCenterX();
        this.targetPosY = this.circleTarget.getCenterY();
        this.getAccuracyPoints.playFromStart();
    }

    public void stopCalculations(){
        this.getAccuracyPoints.pause();
        this.listPointsGet = new Point2D[this.nbPointsToGet];
        this.index = 0;
    }

    public void dispose(){
        this.getAccuracyPoints.stop();
        this.getAccuracyPoints.getKeyFrames().clear();
    }
}
