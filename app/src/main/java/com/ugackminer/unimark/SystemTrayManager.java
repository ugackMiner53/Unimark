package com.ugackminer.unimark;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SystemTrayManager {
    
    SystemTray systemTray;

    public SystemTrayManager(Toolkit toolkit) {
        if (!SystemTray.isSupported()) {
            System.err.println("System tray is not supported!");
            return;
        }

        this.systemTray = SystemTray.getSystemTray();
        PopupMenu popupMenu = new PopupMenu();
        Image icon = toolkit.getImage("icon-128.gif");


        // MenuItem action = new MenuItem("Action");
        // action.addActionListener(actionClicked); 
        
        // popupMenu.add(action);

        TrayIcon trayIcon = new TrayIcon(icon, "Markdown", popupMenu);
        trayIcon.setImageAutoSize(true);
        trayIcon.addActionListener(actionClicked);

        try {
            systemTray.add(trayIcon);
        } catch (AWTException err) {
            System.err.println("Could not add system tray icon.");
            System.err.println(err);
        }
    }

    ActionListener actionClicked = new ActionListener() {
        @Override public void actionPerformed(ActionEvent event) {
            System.out.println("Action clicked");
        }
    };



}