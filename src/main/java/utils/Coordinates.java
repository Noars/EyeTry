package utils;

public class Coordinates {

    public int posX;
    public int posY;
    public int opacity = 1;
    public boolean enableGazePreview = true;

    public Coordinates(double posX, double posY){
        this.posX = (int) posX;
        this.posY = (int) posY;
    }
}
