package jomato.states;

import jomato.*;
import java.awt.*;
public class PauseState implements JomatoState {

    private final PomodoroTray tray;
    private final PomodoroTimer pomodoroTimer;
    private JTimer jTimer;

    public PauseState(PomodoroTray tray, PomodoroTimer timer) {
        this.tray = tray;
        this.pomodoroTimer = timer;
        initialize();
        run();

    }
    private void run() {
        jTimer = JTimer.getInstance(tray, pomodoroTimer);
        jTimer.startTimer(tray.settings.getPause(), true);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void next(PomodoroTimer timer) {
        if (tray.settings.isNotifications()) tray.trayIcon.displayMessage("Gambate!", "Do your best in 25!", TrayIcon.MessageType.INFO);
        timer.setState(new CounterState(tray, timer));
    }

    @Override
    public void prev(PomodoroTimer timer) {
        timer.setState(new CounterState(tray, timer));
    }

    @Override
    public void stop(PomodoroTimer timer) {
        jTimer.stopTimer();
        timer.setState(new NormalState(tray, timer));
    }

    @Override
    public String getStateName() {
        return "PauseState";
    }
}
