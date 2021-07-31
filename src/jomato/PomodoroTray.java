package jomato;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;
import java.util.prefs.PreferencesFactory;

public class PomodoroTray {

    public TrayIcon trayIcon;
    public MenuItem startItem;
    public MenuItem stopItem;
    public MenuItem quitItem;
    public MenuItem pauseItem;
    public MenuItem settingsItem;
    private Preferences preferences;

    public Settings settings;

    public PomodoroTray() {
        preferences = Preferences.userRoot().node(this.getClass().getName());
        settings = prefsToSettings();
    }

    public void run() {
        final ImageLoader imageLoader = new ImageLoader();
        final PopupMenu popup = new PopupMenu();

        startItem = new MenuItem("Start Timer");
        pauseItem = new MenuItem("Pause timer");
        stopItem = new MenuItem("Stop Timer");
        quitItem = new MenuItem("Quit");
        settingsItem = new MenuItem("Settings");
        stopItem.setEnabled(false);
        pauseItem.setEnabled(false);

        //Add components to pop-up menu
        popup.add(startItem);
        popup.add(pauseItem);
        popup.add(stopItem);
        popup.addSeparator();
        popup.add(settingsItem);
        popup.add(quitItem);

        // Create tray and set image
        final SystemTray tray = SystemTray.getSystemTray();
        final Image icon = imageLoader.loadImage("img/logo.png");
        trayIcon = new TrayIcon(icon,  "Pomodoro");

        final PomodoroTimer timer = new PomodoroTimer(this);

        // Action Listeners
        // Quit
        ActionListener quitListener = e -> System.exit(0);

        // Start
        ActionListener startListener = e -> timer.startSession();

        // Stop
        ActionListener stopListener = e -> timer.stopSession();


        // Pause
        ActionListener pauseListener = e -> timer.pauseSession();

        //Settings
        ActionListener settingsListener = e -> showSettings(settings);

        quitItem.addActionListener(quitListener);
        startItem.addActionListener(startListener);
        stopItem.addActionListener(stopListener);
        settingsItem.addActionListener(settingsListener);

        trayIcon.setPopupMenu(popup);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
    }

    private void showSettings(Settings settings) {

        Color background = new Color(40, 40, 40);
        Color lightBG = new Color(52, 52, 52);
        Color foreground = new Color(199, 188, 158);
        Color highlight = new Color(162, 125, 54);

        JFrame f = new JFrame();
        f.getContentPane().setBackground(background);

        // Buttons
        JButton saveButton = new JButton("Save");
        saveButton.setBounds(95, 120, 100, 40);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(205, 120, 100, 40);

        // Labels
        JLabel durationLabel = new JLabel("Duration");
        durationLabel.setBounds(20, 20, 100, 40);
        durationLabel.setForeground(foreground);

        JLabel pauseLabel = new JLabel("Pause");
        pauseLabel.setBounds(20, 70, 100, 40);
        pauseLabel.setForeground(foreground);

        JLabel sessionsLabel = new JLabel("Sessions");
        sessionsLabel.setBounds(190, 20, 100, 40);
        sessionsLabel.setForeground(foreground);

        JLabel notificationsLabel = new JLabel("Notifications");
        notificationsLabel.setBounds(190, 70, 100, 40);
        notificationsLabel.setForeground(foreground);

        // Input
        JTextField duration = new JTextField();
        duration.setBounds(130, 20, 50, 40);
        duration.setText(String.valueOf(settings.getDuration()));
        duration.setBackground(lightBG);
        duration.setForeground(foreground);
        duration.setBorder(BorderFactory.createLineBorder(background));


        JTextField pause = new JTextField();
        pause.setBounds(130, 70, 50, 40);
        pause.setText(String.valueOf(settings.getPause()));
        pause.setBackground(lightBG);
        pause.setForeground(foreground);
        pause.setBorder(BorderFactory.createLineBorder(background));

        JTextField sessions = new JTextField();
        sessions.setBounds(300, 20, 50, 40);
        sessions.setText(String.valueOf(settings.getSessions()));
        sessions.setBackground(lightBG);
        sessions.setForeground(foreground);
        sessions.setBorder(BorderFactory.createLineBorder(background));

        JCheckBox notifications =  new JCheckBox("", true);
        notifications.setBounds(300, 70, 50, 40);
        notifications.setSelected(settings.isNotifications());
        notifications.setBackground(lightBG);
        notifications.setForeground(foreground);
        notifications.setBorder(BorderFactory.createLineBorder(background));

        // Action Listeners
        ActionListener saveAction = e -> {
            if (duration.getText() != null)  System.out.println(duration.getText());
            save(Integer.parseInt(duration.getText()), Integer.parseInt(pause.getText()), Integer.parseInt(sessions.getText()), notifications.isSelected());
            f.dispose();
        };
        saveButton.addActionListener(saveAction);

        ActionListener cancelAction = e -> f.dispose();
        cancelButton.addActionListener(cancelAction);


        f.add(saveButton);
        f.add(cancelButton);
        f.add(durationLabel);
        f.add(pauseLabel);
        f.add(sessionsLabel);
        f.add(notificationsLabel);
        f.add(duration);
        f.add(pause);
        f.add(sessions);
        f.add(notifications);

        f.setSize(400, 200);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        f.setLocation(dim.width/2-f.getSize().width/2, dim.height/2-f.getSize().height/2);
        f.setAlwaysOnTop(true);
        f.setResizable(false);
        f.setLayout(null);
        f.setVisible(true);
    }

    private void save(int duration, int pause, int sessions, boolean notifications) {
        if (duration != 0 ) preferences.putInt("duration", duration);
        if (pause != 0) preferences.putInt("pause", pause);
        if (sessions != 0) preferences.putInt("sessions", sessions);
        preferences.putBoolean("notifications", notifications);
        // TODO: save as preferences
        settings = prefsToSettings();
    }

    private Settings prefsToSettings() {
        settings = new Settings(preferences.getInt("duration", 10),
                preferences.getInt("pause", 5),
                preferences.getInt("sessions", 4),
                preferences.getBoolean("notifications", true));
        return settings;
    }
}
