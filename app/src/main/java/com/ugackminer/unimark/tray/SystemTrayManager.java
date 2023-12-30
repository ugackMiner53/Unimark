package com.ugackminer.unimark.tray;

import java.awt.Image;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.KeyEvent;
import java.net.URL;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.dispatcher.SwingDispatchService;
import com.ugackminer.unimark.App;
import com.ugackminer.unimark.robot.KeyboardListener;

public class SystemTrayManager extends JFrame implements WindowListener {
    
    static final URL markdownImageURL = SystemTrayManager.class.getResource("/markdown-mark.png");
    static final URL disabledMarkdownImageURL = SystemTrayManager.class.getResource("/markdown-mark-disabled.png");

    // SystemTray systemTray;

    public SystemTrayManager(Toolkit toolkit) {
        if (!SystemTray.isSupported()) return;
        GlobalScreen.setEventDispatcher(new SwingDispatchService());

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {}

        Image markdownImage = toolkit.getImage(markdownImageURL);
        Image disabledMarkdownImage = toolkit.getImage(disabledMarkdownImageURL);
        PopupMenu popup = new PopupMenu();

        TrayIcon trayIcon = new TrayIcon(markdownImage, "Unimark", popup);
        trayIcon.setImageAutoSize(true);
        trayIcon.addActionListener(trayIconClickedCallback);

        MenuItem disableItem = new MenuItem("Disable Unimark", new MenuShortcut(KeyEvent.VK_D));
        disableItem.addActionListener(e -> {
            App.isDisabled = !App.isDisabled;
            ((MenuItem)e.getSource()).setLabel(App.isDisabled ? "Enable Unimark" : "Disable Unimark");
            trayIcon.setImage(App.isDisabled ? disabledMarkdownImage : markdownImage);
        });
        popup.add(disableItem);

        MenuItem exitItem = new MenuItem("Exit", new MenuShortcut(KeyEvent.VK_X));
        exitItem.addActionListener(exitMenuEntryCallback);
        popup.add(exitItem);


        try {
            SystemTray.getSystemTray().add(trayIcon);
        } catch (Exception e) {
            System.err.println(e);
        }

        setTitle("Unimark Configuration Window");
		setSize(300, 150);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		addWindowListener(this);
        setIconImage(markdownImage);
        


        JLabel label = new JLabel("Unimark");
        add(label);

        startKeyboardHook();
    }

    private void startKeyboardHook() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException err) {
            System.err.println("The program could not register the native keybind hooking (jnativehook library issue)!");
            System.err.println(err.getMessage());
            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new KeyboardListener());
    }

    public void windowOpened(WindowEvent e) {
        System.out.println("Windows opened");

    }

    ActionListener disableMenuEntryCallback = e -> {
        App.isDisabled = !App.isDisabled;
        ((MenuItem)e.getSource()).setLabel(App.isDisabled ? "Enable Unimark" : "Disable Unimark");
    };

    ActionListener exitMenuEntryCallback = e -> {
        windowClosed(null);
    };

    ActionListener trayIconClickedCallback = e -> {
        setVisible(true);
    };

    public void windowClosing(WindowEvent e) { /* Unimplemented */ }
    public void windowIconified(WindowEvent e) { /* Unimplemented */ }
    public void windowDeiconified(WindowEvent e) { /* Unimplemented */ }
    public void windowActivated(WindowEvent e) { /* Unimplemented */ }
    public void windowDeactivated(WindowEvent e) { /* Unimplemented */ }


    
	public void windowClosed(WindowEvent e) {
		//Clean up the native hook.
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException ex) {
            ex.printStackTrace();
        }
		System.runFinalization();
		System.exit(0);
	}

}