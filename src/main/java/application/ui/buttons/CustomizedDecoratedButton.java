package application.ui.buttons;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CustomizedDecoratedButton extends Button {

    public CustomizedDecoratedButton(String name, String urlImg){
        super(name);
        getStyleClass().add("topBarButton");
        ImageView img = new ImageView(new Image(urlImg));
        img.setPreserveRatio(true);
        img.setFitWidth(50);
        this.setGraphic(img);
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }
}
