package application.ui.panes;

import application.Main;
import application.ui.buttons.CustomizedTestButton;
import gaze.GazeEvent;
import gaze.TobiiGazeDeviceManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import utils.Coordinates;
import utils.Cross;

@Slf4j
public class TestPane extends Pane {

    Main main;
    Stage primaryStage;
    TobiiGazeDeviceManager tobiiGazeDeviceManager;
    Coordinates coordinates;
    Timeline updateGazeVisualTimeline;

    public TestPane(Main main, Stage primaryStage, Coordinates coordinates, TobiiGazeDeviceManager tobiiGazeDeviceManager) {
        super();
        this.main = main;
        this.primaryStage = primaryStage;
        this.coordinates = coordinates;
        this.tobiiGazeDeviceManager = tobiiGazeDeviceManager;
    }

    public void startTest(String idTest){

        this.getChildren().add(this.createButtonsTest());
        this.tobiiGazeDeviceManager.setPause(false);

        switch (idTest){
            case "1" -> this.createTargetTest1();
            case "2" -> log.info("Wait for more test");
            default -> log.info("Can't load test");
        }

        this.createGazeVisual();
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

        Button btnVisual = new CustomizedTestButton("Visuel", "images/show.png", "grey");
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

    public void createTargetTest1(){

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        double posXTopCenter = (screenBounds.getWidth() / 2);
        double posYTopCenter = (screenBounds.getHeight() / 5);

        Circle circleTopCenter = new Circle(posXTopCenter, posYTopCenter, 50);
        circleTopCenter.setFill(Color.LIGHTGREY);
        circleTopCenter.setOpacity(0.7);

        Cross crossTopCenter = new Cross();
        crossTopCenter.setLayoutX(posXTopCenter);
        crossTopCenter.setLayoutY(posYTopCenter);

        this.createRotateEvent(circleTopCenter, crossTopCenter);

        double posXLeftBottom = (screenBounds.getWidth() / 8);
        double posYLeftBottom = ((screenBounds.getHeight() / 10) * 8);

        Circle circleBottomLeft = new Circle(posXLeftBottom, posYLeftBottom, 50);
        circleBottomLeft.setFill(Color.LIGHTGREY);
        circleBottomLeft.setOpacity(0.7);

        Cross crossLeftBottom = new Cross();
        crossLeftBottom.setLayoutX(posXLeftBottom);
        crossLeftBottom.setLayoutY(posYLeftBottom);

        this.createRotateEvent(circleBottomLeft, crossLeftBottom);

        double posXRightBottom = ((screenBounds.getWidth() / 8) * 7);
        double posYRightBottom = ((screenBounds.getHeight() / 10) * 8);

        Circle circleBottomRight = new Circle(posXRightBottom, posYRightBottom, 50);
        circleBottomRight.setFill(Color.LIGHTGREY);
        circleBottomRight.setOpacity(0.7);

        Cross crossRightBottom = new Cross();
        crossRightBottom.setLayoutX(posXRightBottom);
        crossRightBottom.setLayoutY(posYRightBottom);

        this.createRotateEvent(circleBottomRight, crossRightBottom);

        this.getChildren().addAll(circleTopCenter, crossTopCenter, circleBottomLeft, crossLeftBottom, circleBottomRight, crossRightBottom);
    }

    public void createGazeVisual(){
        Arc gazeCircle = new Arc(this.coordinates.posX, this.coordinates.posY, 50, 50, 0, 360);
        gazeCircle.setFill(Color.TRANSPARENT);
        gazeCircle.setStroke(Color.BLACK);
        gazeCircle.setType(ArcType.OPEN);

        this.getChildren().add(gazeCircle);
        this.updateGazeVisual(gazeCircle);
    }

    public void updateGazeVisual(Arc gazeVisual) {
        this.updateGazeVisualTimeline = new Timeline(new KeyFrame(Duration.millis(10), ev -> {
            gazeVisual.setCenterX(this.coordinates.posX);
            gazeVisual.setCenterY(this.coordinates.posY);
            gazeVisual.setOpacity(this.coordinates.opacity);
        }));
        this.updateGazeVisualTimeline.setCycleCount(Animation.INDEFINITE);
        this.updateGazeVisualTimeline.play();
    }

    public void createRotateEvent(Circle circleTarget, Cross crossTarget){
        /*EventHandler<Event> eventMouseCircleTarget = e -> {
            if (e.getEventType() == MouseEvent.MOUSE_ENTERED) {
                crossTarget.startRotateCrossAnimation();
            }else if (e.getEventType() == MouseEvent.MOUSE_EXITED){
                crossTarget.stopRotateCrossAnimation();
            }
        };
        circleTarget.addEventHandler(MouseEvent.ANY, eventMouseCircleTarget);*/

        EventHandler<Event> eventGazeCircleTarget = e -> {
            if (e.getEventType() == GazeEvent.GAZE_ENTERED) {
                crossTarget.startRotateCrossAnimation();
            }
            else if (e.getEventType() == GazeEvent.GAZE_EXITED){
                crossTarget.stopRotateCrossAnimation();
            }
        };
        circleTarget.addEventHandler(GazeEvent.ANY, eventGazeCircleTarget);
        this.tobiiGazeDeviceManager.addEventFilter(circleTarget);
    }

    public void dispose(){
        this.getChildren().clear();
        this.tobiiGazeDeviceManager.clear();
    }
}
