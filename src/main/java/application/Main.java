package application;

import application.ui.panes.DecoratedPane;
import application.ui.panes.MainPane;
import application.ui.panes.TestPane;
import gaze.GazeDeviceManagerFactory;
import gaze.TobiiGazeDeviceManager;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.Coordinates;
import utils.Save;

public class Main extends Application {

    Coordinates coordinates;
    TobiiGazeDeviceManager tobiiGazeDeviceManager;
    TestPane testPane;
    MainPane mainPane;
    DecoratedPane decoratedPane;
    Save save;

    private final int defaultWidth = 600;
    private final int defaultHeight = 300;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){

        primaryStage.setWidth(this.defaultWidth);
        primaryStage.setHeight(this.defaultHeight);
        primaryStage.setTitle("EyeTry");

        this.save = new Save();
        this.coordinates = new Coordinates(primaryStage.getWidth(), primaryStage.getHeight());
        this.tobiiGazeDeviceManager = GazeDeviceManagerFactory.instance.createNewGazeListener(this.coordinates);
        this.mainPane = new MainPane(this, primaryStage);
        this.testPane = new TestPane(this, primaryStage, this.coordinates, this.tobiiGazeDeviceManager, this.save);
        this.decoratedPane = new DecoratedPane(primaryStage, this.tobiiGazeDeviceManager);
        this.decoratedPane.setCenter(this.mainPane);

        Scene scene = new Scene(this.decoratedPane, primaryStage.getWidth(), primaryStage.getHeight());
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }

    public void goToMain(Stage primaryStage){
        primaryStage.setX(this.defaultWidth);
        primaryStage.setY(this.defaultHeight);
        primaryStage.setWidth(this.defaultWidth);
        primaryStage.setHeight(this.defaultHeight);
        primaryStage.getScene().setRoot(this.decoratedPane);
    }
    public void goToTest(Stage primaryStage, String idTest){
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(primaryScreenBounds.getWidth());
        primaryStage.setHeight(primaryScreenBounds.getHeight());
        primaryStage.getScene().setRoot(this.testPane);
        this.testPane.startTest(idTest);
    }
}
