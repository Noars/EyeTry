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
import lombok.Getter;
import utils.Coordinates;

public class Main extends Application {

    Coordinates coordinates;
    TobiiGazeDeviceManager tobiiGazeDeviceManager;
    TestPane testPane;
    MainPane mainPane;
    DecoratedPane decoratedPane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){

        primaryStage.setWidth(600);
        primaryStage.setHeight(300);
        primaryStage.setTitle("EyeTry");

        coordinates = new Coordinates(primaryStage.getWidth(), primaryStage.getHeight());
        tobiiGazeDeviceManager = GazeDeviceManagerFactory.instance.createNewGazeListener(coordinates);
        mainPane = new MainPane(this, primaryStage);
        testPane = new TestPane(this, primaryStage, coordinates, tobiiGazeDeviceManager);
        decoratedPane = new DecoratedPane(primaryStage);
        decoratedPane.setCenter(mainPane);

        Scene scene = new Scene(decoratedPane, primaryStage.getWidth(), primaryStage.getHeight());
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }

    public void goToTest(Stage primaryStage){
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(primaryScreenBounds.getWidth());
        primaryStage.setHeight(primaryScreenBounds.getHeight());
        primaryStage.getScene().setRoot(this.testPane);
        this.testPane.startTest();
    }
}
