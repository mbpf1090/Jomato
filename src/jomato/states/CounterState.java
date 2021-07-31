package jomato.states;

import jomato.*;
import java.awt.*;

public class CounterState implements JomatoState {

    private final PomodoroTray tray;
    private final PomodoroTimer pomodoroTimer;
    private int session = 1;
    private JTimer jTimer;

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
    }

    @Override
    public void next(PomodoroTimer timer) {
        if (PomodoroTimer.session > tray.settings.getSessions()) {
            timer.setState(new NormalState(tray, pomodoroTimer));
            if (tray.settings.isNotifications()) tray.trayIcon.displayMessage("That's a wrap!", "Great job!", TrayIcon.MessageType.INFO);
        } else {
            if (tray.settings.isNotifications()) tray.trayIcon.displayMessage("Sessions done", "Time to chill!", TrayIcon.MessageType.INFO);
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

    @Override
    public String getStateName() {
        return "CounterState";
    }
}
