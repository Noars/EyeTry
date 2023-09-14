package utils;

import javafx.scene.control.Label;

public class Coordinates {

    public int posX;
    public int posY;
    public int opacity = 1;
    public boolean enableGazePreview = true;
    public String translateText = "125";

    public Coordinates(double posX, double posY){
        this.posX = (int) posX;
        this.posY = (int) posY;
    }

    public Label checkEyeTracker(){
        Label status = new Label();
        if (this.posX == 600 && this.posY == 300){
            status.setText("Pas connecté");
            status.getStyleClass().clear();
            status.getStyleClass().add("gazeOff");
            this.translateText = "125";
        }else {
            status.setText("Connecté");
            status.getStyleClass().clear();
            status.getStyleClass().add("gazeOn");
            this.translateText = "160";
        }
        return status;
    }
}
