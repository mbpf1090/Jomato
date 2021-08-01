package jomato.states;

import jomato.*;
import java.awt.*;

public class CounterState implements JomatoState, Breakable {

    private final PomodoroTray tray;
    private final PomodoroTimer pomodoroTimer;
    private int session = 1;
    private JTimer jTimer;

    public JTimer getTimer() {
        return jTimer;
    }

    public CounterState(PomodoroTray tray, PomodoroTimer timer) {
        this.tray = tray;
        this.pomodoroTimer = timer;
        this.initialize();
        this.run();
    }

    private void run() {
        this.jTimer = JTimer.getInstance(tray, pomodoroTimer);
        jTimer.startTimer(tray.settings.getDuration(), false);
    }


    @Override
    public void initialize() {
        tray.stopItem.setEnabled(true);
        tray.startItem.setEnabled(false);
        tray.pauseItem.setEnabled(true);
    }

    @Override
    public void next(PomodoroTimer timer) {
        if (PomodoroTimer.session > tray.settings.getSessions()) {
            timer.setState(new NormalState(tray, pomodoroTimer));
            tray.showNotification(Message.END);
        } else {
            tray.showNotification(Message.SESSION);
            timer.setState(new PauseState(tray, pomodoroTimer));
        }
    }

    @Override
    public void prev(PomodoroTimer timer) {
        timer.setState(new PauseState(tray, pomodoroTimer));
    }

    @Override
    public void stop(PomodoroTimer timer) {
        jTimer.stopTimer();
        timer.setState(new NormalState(tray, pomodoroTimer));
    }
}
