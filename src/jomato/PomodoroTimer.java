package jomato;

import jomato.states.NormalState;
import java.util.Timer;

public class PomodoroTimer {

    // Fields
    private Timer timer;
    private PomodoroTray trayController;
    public static int session = 1;
    public static final int TIMER_DURATION = 10;
    public static final int PAUSE_DURATION = 5;
    private boolean isRunning = false;
    private boolean isPause = false;
    private JomatoState state;

    // Constructor
    public PomodoroTimer(PomodoroTray taskTray) {
        this.trayController = taskTray;
        this.state = new NormalState(trayController, this);
    }

    // State
    public JomatoState getState() {
        return state;
    }

    public void setState(JomatoState state) {
        this.state = state;
    }

    public void nextState() {
        state.next(this);
    }

    public void prevState() {
        state.prev(this);
    }

    public void stopState() {
        state.stop(this);
    }

    public void startSession() {
        nextState();
    }

    public void stopSession() {
        stopState();
    }

    public void pauseSession() {
    }
}