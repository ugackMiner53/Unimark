package com.ugackminer.unimark;

import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.net.URL;
import java.awt.PopupMenu;
import java.awt.MenuItem;
import java.awt.TrayIcon;
import java.awt.AWTException;

import javax.swing.UIManager;

public class SystemTrayManager {
    
    static final URL markdownImageURL = SystemTrayManager.class.getResource("/markdown-mark.png");

    // SystemTray systemTray;

    public SystemTrayManager() {
        if (!SystemTray.isSupported()) return;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {}

        SystemTray tray = SystemTray.getSystemTray();
        Image markdownImage = Toolkit.getDefaultToolkit().getImage(markdownImageURL);


        PopupMenu popup = new PopupMenu();

        MenuItem defaultItem = new MenuItem();
        popup.add(defaultItem);

        TrayIcon trayIcon = new TrayIcon(markdownImage, "Tray Demo", popup);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.err.println(e);
        }

    }


    ActionListener menuEntryCallback = e -> {
        System.out.println("Menu Entry Clicked");
    };


}