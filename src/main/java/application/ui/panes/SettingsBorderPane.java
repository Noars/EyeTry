package application.ui.panes;

import application.Main;
import application.ui.buttons.CustomizedPaneButton;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import utils.Settings;

public class SettingsBorderPane extends BorderPane {

    HBox settingsBorderPaneBox;

    public SettingsBorderPane(Main main, Stage primaryStage, Settings settings){

        Button returnBtn = createReturnButton(main, primaryStage);

        this.settingsBorderPaneBox = new HBox(returnBtn, this.createSettingsGridPane(settings));
        this.settingsBorderPaneBox.setSpacing(25);
        this.settingsBorderPaneBox.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(this.settingsBorderPaneBox, Pos.CENTER);
        this.setCenter(this.settingsBorderPaneBox);

        this.setStyle("-fx-background-color: #535e65; -fx-background-radius: 0 0 15 15");
    }

    public Button createReturnButton(Main main, Stage primaryStage){
        Button returnBtn = new CustomizedPaneButton("Retour", "images/back.png", "grey");
        returnBtn.setOnAction((e) -> main.returnMain(primaryStage));
        return returnBtn;
    }

    public GridPane createSettingsGridPane(Settings settings){
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(10);
        {
            Label fixationLabel = new Label("Temps de fixation:");
            Label timeLabel = new Label("ms");
            TextField dwellTime = new TextField("" + settings.dwellTime);
            dwellTime.setMaxWidth(50);
            gridPane.add(fixationLabel, 0, 0);
            gridPane.add(dwellTime, 1, 0);
            gridPane.add(timeLabel, 2, 0);

            fixationLabel.getStyleClass().add("text");
            timeLabel.getStyleClass().add("text");

            dwellTime.textProperty().addListener((observable, oldValue, newValue) -> {
                dwellTime.setText(settings.checkValue(newValue));
                settings.dwellTime = Integer.parseInt(dwellTime.getText());

            });

            Label nbFixationLabel = new Label("Nombre de fixation:");
            TextField nbFixation = new TextField("" + settings.nbPointsToGet);
            nbFixation.setMaxWidth(50);
            gridPane.add(nbFixationLabel, 0, 1);
            gridPane.add(nbFixation, 1, 1);

            nbFixationLabel.getStyleClass().add("text");

            nbFixation.textProperty().addListener((observable, oldValue, newValue) -> {
                nbFixation.setText(settings.checkValue(newValue));
                settings.nbPointsToGet = Integer.parseInt(nbFixation.getText());

            });
        }
        return gridPane;
    }
}
