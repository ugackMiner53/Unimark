package com.ugackminer.unimark;

import java.net.URL;

import javax.swing.UIManager;

import java.awt.event.ActionListener;

// import dorkbox.systemTray.Menu;
// import dorkbox.systemTray.MenuItem;
// import dorkbox.systemTray.SystemTray;
// import dorkbox.systemTray.SystemTray.TrayType;

public class SystemTrayManager {
    
    static final URL markdownImage = SystemTrayManager.class.getResource("/markdown-mark.png");

    // SystemTray systemTray;

    public SystemTrayManager() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {}

        
    }


    ActionListener menuEntryCallback = e -> {
        System.out.println("Menu Entry Clicked");
    };


}