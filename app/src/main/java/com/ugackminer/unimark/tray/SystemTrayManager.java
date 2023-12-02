package com.ugackminer.unimark.tray;

import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.awt.PopupMenu;
import java.awt.MenuItem;
import java.awt.TrayIcon;
import java.awt.AWTException;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.WindowConstants;

import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.imageio.ImageIO;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.dispatcher.SwingDispatchService;
import com.ugackminer.unimark.robot.KeyboardListener;

import javax.swing.JFrame;

public class SystemTrayManager extends JFrame implements WindowListener {
    
    static final URL markdownImageURL = SystemTrayManager.class.getResource("/markdown-mark.png");

    // SystemTray systemTray;

    public SystemTrayManager(Toolkit toolkit) {
        if (!SystemTray.isSupported()) return;
        GlobalScreen.setEventDispatcher(new SwingDispatchService());

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {}

        Image markdownImage = toolkit.getImage(markdownImageURL.getPath());
        PopupMenu popup = new PopupMenu();

        MenuItem defaultItem = new MenuItem("Exit");
        defaultItem.addActionListener(menuEntryCallback);
        popup.add(defaultItem);

        try {
            TrayIcon trayIcon = new TrayIcon(ImageIO.read(markdownImageURL), "Tray Demo", popup);
            SystemTray.getSystemTray().add(trayIcon);
        } catch (Exception e) {
            System.err.println(e);
        }

        setTitle("Unimark Configuration Window");
		setSize(300, 150);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		addWindowListener(this);
        setIconImage(markdownImage);
		setVisible(true);


        JLabel label = new JLabel("Unimark");
        add(label);
    }

    public void windowOpened(WindowEvent e) {
        System.out.println("Windows opened");
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException err) {
            System.err.println("The program could not register the native keybind hooking (jnativehook library issue)!");
            System.err.println(err.getMessage());
            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new KeyboardListener());
    }

    ActionListener menuEntryCallback = e -> {
        System.exit(0);
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