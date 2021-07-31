package jomato.states;

import jomato.JomatoState;
import jomato.PomodoroTimer;
import jomato.PomodoroTray;

public class NormalState implements JomatoState {

    private final PomodoroTray tray;
    private final PomodoroTimer timer;

    public NormalState(PomodoroTray tray, PomodoroTimer timer) {
        this.tray = tray;
        this.timer = timer;
        initialize();
    }

    @Override
    public void initialize() {
        tray.stopItem.setEnabled(false);
        tray.startItem.setLabel("Start Timer");
        tray.startItem.setEnabled(true);
        PomodoroTimer.session = 1;
    }

    @Override
    public void next(PomodoroTimer timer) {
        timer.setState(new CounterState(tray, timer));
    }

    @Override
    public void prev(PomodoroTimer timer) {
        timer.setState(new NormalState(tray, timer));
    }

    @Override
    public void stop(PomodoroTimer timer) {

    }

    @Override
    public String getStateName() {
        return "NormalState";
    }
}
