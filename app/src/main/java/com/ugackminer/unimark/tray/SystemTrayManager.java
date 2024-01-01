package com.ugackminer.unimark.tray;

import java.awt.Image;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.dispatcher.SwingDispatchService;
import com.ugackminer.unimark.App;
import com.ugackminer.unimark.robot.KeyboardListener;

public class SystemTrayManager extends JFrame implements WindowListener {
    
    static final URL markdownImageURL = SystemTrayManager.class.getResource("/markdown-mark.png");
    static final URL disabledMarkdownImageURL = SystemTrayManager.class.getResource("/markdown-mark-disabled.png");

    // SystemTray systemTray;
    // Static labels and buttons
    JLabel shortcutLabel;
    JButton shortcutButton;



    public SystemTrayManager(Toolkit toolkit) {
        if (!SystemTray.isSupported()) return;
        GlobalScreen.setEventDispatcher(new SwingDispatchService());

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {}

        Image markdownImage = toolkit.getImage(markdownImageURL);
        Image disabledMarkdownImage = toolkit.getImage(disabledMarkdownImageURL);
        PopupMenu popup = new PopupMenu();

        TrayIcon trayIcon = new TrayIcon(App.configManager.isDisabled ? disabledMarkdownImage : markdownImage, "Unimark", popup);
        trayIcon.setImageAutoSize(true);
        trayIcon.addActionListener(e -> {
            setVisible(true);
        });

        MenuItem disableItem = new MenuItem(App.configManager.isDisabled ? "Enable Unimark" : "Disable Unimark", new MenuShortcut(KeyEvent.VK_D));
        disableItem.addActionListener(e -> {
            App.configManager.isDisabled = !App.configManager.isDisabled;
            ((MenuItem)e.getSource()).setLabel(App.configManager.isDisabled ? "Enable Unimark" : "Disable Unimark");
            trayIcon.setImage(App.configManager.isDisabled ? disabledMarkdownImage : markdownImage);
        });
        popup.add(disableItem);

        MenuItem exitItem = new MenuItem("Exit", new MenuShortcut(KeyEvent.VK_X));
        exitItem.addActionListener(e -> {
            windowClosed(null);
        });
        popup.add(exitItem);


        try {
            SystemTray.getSystemTray().add(trayIcon);
        } catch (Exception e) {
            System.err.println(e);
        }

        setTitle("Unimark Configuration Window");
		setSize(300, 800);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		addWindowListener(this);
        setIconImage(markdownImage);
        setVisible(true);

        createPanelLayout();
        updateKeyboardShortcutLabel();
        startKeyboardHook();
    }

    private void createPanelLayout() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setAlignmentX(TOP_ALIGNMENT);
        mainPanel.setAlignmentY(CENTER_ALIGNMENT);
        add(mainPanel);

        JLabel label = new JLabel("Unimark");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Sans-Serif", Font.BOLD, 32));
        mainPanel.add(label);


        JPanel shortcutPanel = new JPanel();
        shortcutPanel.setLayout(new BoxLayout(shortcutPanel, BoxLayout.Y_AXIS));
        shortcutPanel.setAlignmentY(CENTER_ALIGNMENT);
        shortcutPanel.setBorder(new EmptyBorder(32, 0, 0, 0));
        mainPanel.add(shortcutPanel);

        JLabel shortcutTitle = new JLabel("Keyboard Shortcut");
        shortcutTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        shortcutTitle.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        shortcutPanel.add(shortcutTitle);

        shortcutLabel = new JLabel();
        shortcutLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        shortcutLabel.setFont(new Font("MONOSPACED", 0, 12));
        shortcutPanel.add(shortcutLabel);

        shortcutButton = new JButton("Change Shortcut");
        shortcutButton.setSize(100, 33);
        shortcutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        shortcutButton.addActionListener(e -> {
            shortcutButton.setEnabled(false);
            shortcutButton.setText("Enter New Shortcut...");
            KeyboardListener.changeKeyboardShotcut();
        });
        shortcutPanel.add(shortcutButton);

    }

    public void updateKeyboardShortcutLabel() {
        StringBuilder newLabel = new StringBuilder();
        for (int keycode : App.configManager.keyboardShortcut) {
            newLabel.append(KeyEvent.getKeyText(keycode));
            newLabel.append(" + ");
        }
        newLabel.subSequence(0, newLabel.length()-3);
        shortcutLabel.setText(newLabel.substring(0, newLabel.length()-3));
        shortcutButton.setText("Change Shortcut");
        shortcutButton.setEnabled(true);
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
        App.configManager.saveConfig();
		System.runFinalization();
		System.exit(0);
	}

}