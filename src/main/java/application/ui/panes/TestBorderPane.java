package application.ui.panes;

import application.Main;
import application.ui.buttons.CustomizedPaneButton;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TestBorderPane extends BorderPane {

    HBox testBorderPaneBox;

    public TestBorderPane(Main main, Stage primaryStage){

        Button returnBtn = createReturnButton(main, primaryStage);
        Button fixedTest = createFixedTestButton(main, primaryStage);
        Button movingTest = createMovingTestButton(main, primaryStage);

        this.testBorderPaneBox = new HBox(returnBtn, fixedTest, movingTest);
        this.testBorderPaneBox.setSpacing(5);
        this.testBorderPaneBox.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(this.testBorderPaneBox, Pos.CENTER);
        this.setCenter(this.testBorderPaneBox);

        this.setStyle("-fx-background-color: #535e65; -fx-background-radius: 0 0 15 15");
    }

    public Button createReturnButton(Main main, Stage primaryStage){
        Button returnBtn = new CustomizedPaneButton("Retour", "images/back.png", "grey");
        returnBtn.setOnAction((e) -> main.returnMain(primaryStage));
        return returnBtn;
    }

    public Button createFixedTestButton(Main main, Stage primaryStage) {
        Button fixedTest = new CustomizedPaneButton("Fixe", "images/play.png", "green");
        fixedTest.setOnAction((e) -> main.launchTest(primaryStage, "1"));
        return fixedTest;
    }

    public Button createMovingTestButton(Main main, Stage primaryStage) {
        Button movingTest = new CustomizedPaneButton("Mouvant", "images/play.png", "green");
        movingTest.setOnAction((e) -> main.launchTest(primaryStage, "2"));
        return movingTest;
    }
}
