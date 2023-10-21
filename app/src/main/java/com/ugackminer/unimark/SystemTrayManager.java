package com.ugackminer.unimark;

import java.net.URL;

import javax.swing.UIManager;

import java.awt.event.ActionListener;

import dorkbox.systemTray.Menu;
import dorkbox.systemTray.MenuItem;
import dorkbox.systemTray.SystemTray;
import dorkbox.systemTray.SystemTray.TrayType;

public class SystemTrayManager {
    
    static final URL markdownImage = SystemTrayManager.class.getResource("/markdown-mark.png");

    SystemTray systemTray;
    
    public SystemTrayManager() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {}

        this.systemTray = SystemTray.get("Unimark");
        if (systemTray == null) {
            return;
        }

        systemTray.setTooltip("Unimark");
        systemTray.setStatus(null);
        systemTray.setImage(markdownImage);

        Menu mainMenu = systemTray.getMenu();

        MenuItem menuEntry = new MenuItem("Menu Item", e -> {
            final MenuItem entry = (MenuItem) e.getSource();

            entry.setCallback(menuEntryCallback);
            entry.setTooltip(null); // remove the tooltip
        });
        mainMenu.add(menuEntry);
    }


    ActionListener menuEntryCallback = e -> {
        System.out.println("Menu Entry Clicked");
    };


}