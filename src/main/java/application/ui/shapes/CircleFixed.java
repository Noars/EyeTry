package application.ui.shapes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CircleFixed extends Circle {

    public CircleFixed(double posX, double posY, int radius, Color color, double opacity){
        super(posX, posY, radius, color);
        this.setOpacity(opacity);
    }
}
