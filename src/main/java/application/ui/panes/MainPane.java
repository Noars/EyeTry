package application.ui.panes;

import application.Main;
import application.ui.buttons.CustomizedPaneButton;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainPane extends BorderPane {

    HBox mainPaneBox;

    public MainPane(Main main, Stage primaryStage) {

        Button test1 = createTestButton(main, primaryStage);

        this.mainPaneBox = new HBox(test1);
        this.mainPaneBox.setSpacing(5);
        this.mainPaneBox.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(this.mainPaneBox, Pos.CENTER);
        this.setCenter(this.mainPaneBox);

        this.setStyle("-fx-background-color: #535e65; -fx-background-radius: 0 0 15 15");
    }

    public Button createTestButton(Main main, Stage primaryStage) {
        Button test1 = new CustomizedPaneButton("Test1", "images/play.png", "green");
        test1.setOnAction((e) -> {
            main.goToTest(primaryStage, "1");
        });
        return test1;
    }
}
