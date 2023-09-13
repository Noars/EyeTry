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
import tests.FixedTest;
import tests.MovingTest;
import utils.Coordinates;
import utils.GazePreview;
import utils.Save;
import utils.Settings;

@Slf4j
public class TestPane extends Pane {

    Main main;
    Stage primaryStage;
    TobiiGazeDeviceManager tobiiGazeDeviceManager;
    Coordinates coordinates;
    FixedTest fixedTest;
    MovingTest movingTest;
    GazePreview gazePreview;
    Save save;
    Settings settings;

    String actualTest;

    public TestPane(Main main, Stage primaryStage, Coordinates coordinates, TobiiGazeDeviceManager tobiiGazeDeviceManager, Save save, Settings settings) {
        super();
        this.main = main;
        this.primaryStage = primaryStage;
        this.coordinates = coordinates;
        this.tobiiGazeDeviceManager = tobiiGazeDeviceManager;
        this.save = save;
        this.settings = settings;

        this.gazePreview = new GazePreview(this, this.coordinates);
    }

    public void startTest(String idTest){

        this.actualTest = idTest;
        this.getChildren().add(this.createButtonsTest());
        this.tobiiGazeDeviceManager.setPause(false);

        switch (this.actualTest) {
            case "1" -> {
                this.fixedTest = new FixedTest(this, this.tobiiGazeDeviceManager, this.coordinates, this.save, this.settings);
                this.fixedTest.createTargetFixedTest();
            }
            case "2" -> {
                this.movingTest = new MovingTest();
                this.movingTest.createTargetMovingTest();
            }
            default -> log.warn("Error when starting test -> " + this.actualTest);
        }

        this.gazePreview.createGazePreview();
    }

    public void stopTest(){
        switch (this.actualTest) {
            case "1" -> {
                this.fixedTest.dispose();
                this.returnMain();
            }
            case "2" -> {
                this.movingTest.dispose();
                this.returnMain();
            }
            default -> log.warn("Error when stopping test -> " + this.actualTest);
        }
    }

    public HBox createButtonsTest(){
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(15,15, 15,15));

        Button btnReturn = new CustomizedTestButton("Retour", "images/back.png", "grey");
        btnReturn.setOnAction((e) -> this.stopTest());

        Button btnVisual = new CustomizedTestButton("Pr\u00e9visu", "images/show.png", "grey");
        btnVisual.setOnAction((e) -> {
            if (this.coordinates.enableGazePreview){
                this.coordinates.enableGazePreview = false;
                this.coordinates.opacity = 0;
                ((ImageView) btnVisual.getGraphic()).setImage(new Image("images/hide.png"));
            }else {
                this.coordinates.enableGazePreview = true;
                this.coordinates.opacity = 1;
                ((ImageView) btnVisual.getGraphic()).setImage(new Image("images/show.png"));
            }
        });

        hBox.getChildren().addAll(btnReturn, btnVisual);

        return hBox;
    }

    public void returnMain(){
        this.dispose();
        this.tobiiGazeDeviceManager.setPause(true);
        this.main.goToMain(this.primaryStage);
    }

    public void dispose(){
        this.gazePreview.stopGazePreview();
        this.getChildren().clear();
        this.tobiiGazeDeviceManager.clear();
    }
}
