package jomato;

import java.awt.*;

public class Jomato {

    public static void main(String arg[]){
        //Check the SystemTray is supported
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        PomodoroTray pomodoroTray = new PomodoroTray();
        pomodoroTray.run();
    }
}

