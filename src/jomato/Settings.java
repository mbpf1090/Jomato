package jomato;

public class Settings {
    private int duration;
    private int pause;
    private int sessions;
    private boolean notifications;

    public Settings(int duration, int pause, int sessions, boolean notifications) {
        this.duration = duration;
        this.pause = pause;
        this.sessions = sessions;
        this.notifications = notifications;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPause() {
        return pause;
    }

    public void setPause(int pause) {
        this.pause = pause;
    }

    public int getSessions() {
        return sessions;
    }

    public void setSessions(int sessions) {
        this.sessions = sessions;
    }

    public boolean isNotifications() {
        return notifications;
    }

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }
}
