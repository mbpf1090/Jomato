package jomato;

public interface JomatoState {
    void initialize();
    void next(PomodoroTimer timer);
    void prev(PomodoroTimer timer);
    void stop(PomodoroTimer timer);
}
