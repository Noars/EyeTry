package application.ui.panes;

import application.Main;
import application.ui.buttons.CustomizedTestButton;
import gaze.TobiiGazeDeviceManager;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import tests.Test1;
import utils.Coordinates;
import utils.GazePreview;
import utils.Save;

@Slf4j
public class TestPane extends Pane {

    Main main;
    Stage primaryStage;
    TobiiGazeDeviceManager tobiiGazeDeviceManager;
    Coordinates coordinates;
    Test1 test1;
    GazePreview gazePreview;
    Save save;

    public TestPane(Main main, Stage primaryStage, Coordinates coordinates, TobiiGazeDeviceManager tobiiGazeDeviceManager, Save save) {
        super();
        this.main = main;
        this.primaryStage = primaryStage;
        this.coordinates = coordinates;
        this.tobiiGazeDeviceManager = tobiiGazeDeviceManager;
        this.save = save;

        this.test1 = new Test1(this, this.tobiiGazeDeviceManager, this.coordinates, this.save);
        this.gazePreview = new GazePreview(this, this.coordinates);
    }

    public void startTest(String idTest){

        this.getChildren().add(this.createButtonsTest());
        this.tobiiGazeDeviceManager.setPause(false);

        switch (idTest){
            case "1" -> this.test1.createTargetTest1();
            case "2" -> log.info("Wait for more test");
            default -> log.info("Can't load test");
        }

        this.gazePreview.createGazePreview();
    }

    public HBox createButtonsTest(){
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(15,15, 15,15));

        Button btnReturn = new CustomizedTestButton("Retour", "images/back.png", "grey");
        btnReturn.setOnAction((e) -> {
            this.dispose();
            this.tobiiGazeDeviceManager.setPause(true);
            this.main.goToMain(this.primaryStage);
        });

        Button btnVisual = new CustomizedTestButton("Pr\u00e9visu", "images/show.png", "grey");
        btnVisual.setOnAction((e) -> {
            if (this.coordinates.activeGazeVisual){
                this.coordinates.activeGazeVisual = false;
                this.coordinates.opacity = 0;
                ((ImageView) btnVisual.getGraphic()).setImage(new Image("images/hide.png"));
            }else {
                this.coordinates.activeGazeVisual = true;
                this.coordinates.opacity = 1;
                ((ImageView) btnVisual.getGraphic()).setImage(new Image("images/show.png"));
            }
        });

        hBox.getChildren().addAll(btnReturn, btnVisual);

        return hBox;
    }

    public void dispose(){
        this.gazePreview.stopGazePreview();
        this.test1.dispose();
        this.getChildren().clear();
        this.tobiiGazeDeviceManager.clear();
    }
}
