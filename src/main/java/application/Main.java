package application;

import application.ui.panes.*;
import gaze.GazeDeviceManagerFactory;
import gaze.TobiiGazeDeviceManager;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.Coordinates;
import utils.Save;
import utils.Settings;

public class Main extends Application {

    Coordinates coordinates;
    TobiiGazeDeviceManager tobiiGazeDeviceManager;
    TestPane testPane;
    TestBorderPane testBorderPane;
    MainBorderPane mainBorderPane;
    SettingsBorderPane settingsBorderPane;
    DecoratedBorderPane decoratedBorderPane;
    Save save;
    Settings settings;

    private final static int defaultWidth = 600;
    private final static int defaultHeight = 300;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){

        primaryStage.setWidth(defaultWidth);
        primaryStage.setHeight(defaultHeight);
        primaryStage.setTitle("EyeTry");

        this.save = new Save();
        this.settings = new Settings();
        this.coordinates = new Coordinates(primaryStage.getWidth(), primaryStage.getHeight());
        this.tobiiGazeDeviceManager = GazeDeviceManagerFactory.instance.createNewGazeListener(this.coordinates);
        this.mainBorderPane = new MainBorderPane(this, primaryStage, this.save);
        this.testBorderPane = new TestBorderPane(this, primaryStage);
        this.testPane = new TestPane(this, primaryStage, this.coordinates, this.tobiiGazeDeviceManager, this.save, this.settings);
        this.decoratedBorderPane = new DecoratedBorderPane(primaryStage, this.tobiiGazeDeviceManager, this.coordinates);
        this.decoratedBorderPane.setCenter(this.mainBorderPane);
        this.settingsBorderPane = new SettingsBorderPane(this, primaryStage, this.settings);

        Scene scene = new Scene(this.decoratedBorderPane, primaryStage.getWidth(), primaryStage.getHeight());
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }

    public void returnMain(Stage primaryStage){
        ((BorderPane) primaryStage.getScene().getRoot()).setCenter(this.mainBorderPane);
    }

    public void goToSettings(Stage primaryStage){
        ((BorderPane) primaryStage.getScene().getRoot()).setCenter(this.settingsBorderPane);
    }

    public void goToTest(Stage primaryStage){
        ((BorderPane) primaryStage.getScene().getRoot()).setCenter(this.testBorderPane);
    }

    public void launchTest(Stage primaryStage, String idTest){
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(primaryScreenBounds.getWidth());
        primaryStage.setHeight(primaryScreenBounds.getHeight());
        primaryStage.getScene().setRoot(this.testPane);
        this.testPane.startTest(idTest);
    }

    public void goToMain(Stage primaryStage){
        primaryStage.setX(defaultWidth);
        primaryStage.setY(defaultHeight);
        primaryStage.setWidth(defaultWidth);
        primaryStage.setHeight(defaultHeight);
        primaryStage.getScene().setRoot(this.decoratedBorderPane);
    }
}
