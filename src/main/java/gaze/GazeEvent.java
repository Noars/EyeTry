package gaze;

/*
 * Created by schwab on 14/08/2016.
 */

import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.scene.input.InputEvent;
import lombok.Getter;
import lombok.ToString;

@ToString
public class GazeEvent extends InputEvent {

    public static final EventType<GazeEvent> ANY = new EventType<>(InputEvent.ANY, "GAZE");

    public static final EventType<GazeEvent> GAZE_MOVED = new EventType<>(GazeEvent.ANY, "GAZE_MOVED");

    public static final EventType<GazeEvent> GAZE_ENTERED_TARGET = new EventType<GazeEvent>(GazeEvent.ANY,
            "GAZE_ENTERED_TARGET");

    public static final EventType<GazeEvent> GAZE_EXITED_TARGET = new EventType<GazeEvent>(GazeEvent.ANY,
            "GAZE_EXITED_TARGET");

    public static final EventType<GazeEvent> GAZE_ENTERED = new EventType<>(GazeEvent.GAZE_ENTERED_TARGET,
            "GAZE_ENTERED");

    public static final EventType<GazeEvent> GAZE_EXITED = new EventType<>(GazeEvent.GAZE_EXITED_TARGET, "GAZE_EXITED");

    @Getter
    private final long time;

    @Getter
    private final double x;

    @Getter
    private final double y;

    public GazeEvent(EventType<GazeEvent> et, long time, double x, double y) {
        super(et);
        this.time = time;
        this.x = x;
        this.y = y;
    }

}
