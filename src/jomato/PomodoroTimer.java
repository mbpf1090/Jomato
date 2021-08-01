package jomato;

import jomato.states.BreakState;
import jomato.states.NormalState;

public class PomodoroTimer {

    // Fields
    private PomodoroTray trayController;
    public static int session = 1;

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
        if (state instanceof Breakable) {
            trayController.pauseItem.setLabel("Resume");
            state = new BreakState(state, trayController, this);
        } else {
            trayController.pauseItem.setLabel("Pause");
            prevState();
        }
    }
}