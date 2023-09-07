package application.ui.panes;

import application.Main;
import application.ui.buttons.CustomizedPaneButton;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainPane extends BorderPane {

    HBox mainPaneBox;

    public MainPane(Main main, Stage primaryStage) {

        Button tryEye = createTestButton(main, primaryStage);

        mainPaneBox = new HBox(tryEye);
        mainPaneBox.setSpacing(5);
        mainPaneBox.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(mainPaneBox, Pos.CENTER);
        this.setCenter(mainPaneBox);

        this.setStyle("-fx-background-color: #535e65; -fx-background-radius: 0 0 15 15");
    }

    public Button createTestButton(Main main, Stage primaryStage) {
        Button tryEye = new CustomizedPaneButton("Play", "images/play.png", "green");
        tryEye.setOnAction((e) -> {
            main.goToTest(primaryStage);
        });
        return tryEye;
    }
}
