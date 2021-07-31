package jomato;

public interface JomatoState {
    public void initialize();
    public void next(PomodoroTimer timer);
    public void prev(PomodoroTimer timer);
    public void stop(PomodoroTimer timer);
    public String getStateName();
}
