package jomato.states;

import jomato.Breakable;
import jomato.JomatoState;
import jomato.PomodoroTimer;
import jomato.PomodoroTray;

public class BreakState implements JomatoState {

    private final JomatoState prevState;
    private final PomodoroTray tray;
    private final PomodoroTimer pomodoroTimer;

    public BreakState(JomatoState prevState, PomodoroTray tray, PomodoroTimer pomodoroTimer) {
        this.prevState = prevState;
        this.tray = tray;
        this.pomodoroTimer = pomodoroTimer;
        if (prevState instanceof Breakable) ((Breakable) prevState).getTimer().pauseTimer();
    }

    @Override
    public void initialize() {

    }

    @Override
    public void next(PomodoroTimer timer) {

    }

    @Override
    public void prev(PomodoroTimer timer) {
        ((Breakable) prevState).getTimer().resumeTimer();
        timer.setState(prevState);
    }

    @Override
    public void stop(PomodoroTimer timer) {
        ((Breakable) prevState).getTimer().stopTimer();
        timer.setState(new NormalState(tray, pomodoroTimer));
    }
}
