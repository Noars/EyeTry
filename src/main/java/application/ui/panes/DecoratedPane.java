package application.ui.panes;

import application.ui.buttons.CustomizedDecoratedButton;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class DecoratedPane extends BorderPane {

    private double xOffset = 0;
    private double yOffset = 0;

    public DecoratedPane(Stage primaryStage) {

        Button exit = new CustomizedDecoratedButton("fermer", "images/close.png");
        exit.setOnAction((e) -> {
            System.exit(0);
        });

        Button minimize = new CustomizedDecoratedButton("minimiser", "images/minimize.png");
        minimize.setOnAction((e) -> {
            primaryStage.setIconified(true);
        });

        HBox topBar = new HBox(minimize, exit);
        topBar.setAlignment(Pos.CENTER_RIGHT);
        BorderPane.setAlignment(topBar, Pos.CENTER_RIGHT);
        this.setTop(topBar);
        this.setStyle("-fx-background-color: #282e35; -fx-background-radius: 15");

        topBar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        topBar.setOnMouseDragged(event -> {
            double tempX, tempY;
            ObservableList<Screen> screens = Screen.getScreensForRectangle(primaryStage.getX(),primaryStage.getY(), primaryStage.getWidth(),primaryStage.getHeight());
            Rectangle2D primaryScreenBounds = screens.get(0).getVisualBounds();

            tempX = event.getScreenX() - xOffset;

            if (event.getScreenY() - yOffset < 0) {
                tempY = 0;
            } else
                tempY = Math.min(event.getScreenY() - yOffset, primaryScreenBounds.getHeight() - primaryStage.getHeight() / 3);

            primaryStage.setX(tempX);
            primaryStage.setY(tempY);
        });
    }
}
