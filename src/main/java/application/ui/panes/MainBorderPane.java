package application.ui.panes;

import application.Main;
import application.ui.buttons.CustomizedPaneButton;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import utils.Save;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainBorderPane extends BorderPane {

    HBox mainBorderPaneBox;

    public MainBorderPane(Main main, Stage primaryStage, Save save) {

        Button test = createTestButton(main, primaryStage);
        Button folder = createFolderButton(save);
        Button settings = createSettingsButton(main, primaryStage);

        this.mainBorderPaneBox = new HBox(test, folder, settings);
        this.mainBorderPaneBox.setSpacing(5);
        this.mainBorderPaneBox.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(this.mainBorderPaneBox, Pos.CENTER);
        this.setCenter(this.mainBorderPaneBox);

        this.setStyle("-fx-background-color: #535e65; -fx-background-radius: 0 0 15 15");
    }

    public Button createTestButton(Main main, Stage primaryStage) {
        Button test = new CustomizedPaneButton("Tests", "images/tests.png", "green");
        test.setOnAction((e) -> main.goToTest(primaryStage));
        return test;
    }

    public Button createFolderButton(Save save){
        Button folder = new CustomizedPaneButton("Dossier", "images/folder.png", "orange");
        folder.setOnAction((e) -> {
            try {
                Desktop.getDesktop().open(new File(save.folderPath));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        return folder;
    }

    public Button createSettingsButton(Main main, Stage primaryStage){
        Button settings = new CustomizedPaneButton("Options", "images/option.png", "red");
        settings.setOnAction((e) -> {
            main.goToSettings(primaryStage);
        });
        return settings;
    }
}
