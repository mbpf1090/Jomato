package jomato;

import java.util.Timer;
import java.util.TimerTask;

public class JTimer {

    private static JTimer jTimer;
    private Timer timer;
    private final PomodoroTray tray;
    private final PomodoroTimer pomodoroTimer;

    private int durationState = 0;
    private boolean pauseState;

    private JTimer(PomodoroTray tray, PomodoroTimer pomodoroTimer) {
        this.tray = tray;
        this.pomodoroTimer = pomodoroTimer;
    }

    public static JTimer getInstance(PomodoroTray tray, PomodoroTimer pomodoroTimer) {
        if (jTimer == null) {
            jTimer = new JTimer(tray, pomodoroTimer);

        }
        return jTimer;
    }

    public void startTimer(int duration, boolean pause) {
        this.timer = new Timer();
        pauseState = pause;
        TimerTask task = new TimerTask() {
            int counter = duration;
            @Override
            public void run() {
                counter--;

                String time;
                if (pause) {
                    time = String.format("Pause: %02d:%02d", counter / 60, counter % 60);
                } else {
                    time = String.format("Session %d %02d:%02d", PomodoroTimer.session, counter / 60, counter % 60);
                }

                tray.startItem.setLabel(time);
                durationState = counter;
                if (counter <= 0) {
                    timer.cancel();
                    if (!pause) PomodoroTimer.session++;
                    pomodoroTimer.nextState();

                }
            }
        };

        timer.schedule(task, 0, 1000);

    }

    public void stopTimer() {
        this.timer.cancel();
    }

    public void pauseTimer() {
        timer.cancel();
    }

    public void resumeTimer() {
        startTimer(durationState, pauseState);
    }
}
