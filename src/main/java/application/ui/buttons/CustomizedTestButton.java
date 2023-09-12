package application.ui.buttons;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CustomizedTestButton extends Button {

    private final static int widthButton = 100;
    private final static int heightButton = 100;

    public CustomizedTestButton(String name, String urlImg, String color){
        super(name);
        getStyleClass().add("customizedTestButton");
        this.setGraphic(this.createButtonImageView(urlImg));
        this.getStyleClass().add(color);
        this.setContentDisplay(ContentDisplay.TOP);
        this.setPrefHeight(heightButton);
        this.setPrefWidth(widthButton);
    }

    public ImageView createButtonImageView(String url) {
        ImageView image = new ImageView(new Image(url));
        image.setPreserveRatio(true);
        image.setFitWidth(widthButton / 2.0);
        return image;
    }
}
