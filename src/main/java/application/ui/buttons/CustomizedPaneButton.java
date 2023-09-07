package application.ui.buttons;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CustomizedPaneButton extends Button {

    public CustomizedPaneButton(String name, String urlImg, String color) {
        super(name);
        getStyleClass().add("customizedButton");
        this.setGraphic(this.createButtonImageView(urlImg));
        this.getStyleClass().add(color);
        this.setContentDisplay(ContentDisplay.TOP);
        this.setPrefHeight(200);
        this.setPrefWidth(495. / 5);
        this.applyCss();
    }

    public ImageView createButtonImageView(String url) {
        ImageView image = new ImageView(new Image(url));
        image.setPreserveRatio(true);
        image.setFitWidth(495. / 6);
        return image;
    }
}
