package com.ugackminer.unimark.tray;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.JCheckBox;
import javax.swing.border.EmptyBorder;


import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.dispatcher.SwingDispatchService;
import com.ugackminer.unimark.App;
import com.ugackminer.unimark.robot.KeyboardListener;

public class SystemTrayManager extends JFrame implements WindowListener {
    
    static final URL markdownImageURL = SystemTrayManager.class.getResource("/markdown-mark.png");
    static final URL disabledMarkdownImageURL = SystemTrayManager.class.getResource("/markdown-mark-disabled.png");

    Image markdownImage;
    Image disabledMarkdownImage;

    TrayIcon trayIcon;

    // Static labels and buttons
    JLabel shortcutLabel;
    JButton shortcutButton;
    JLabel enabledTitle;
    JButton isDisabledButton;

    MenuItem disableItem;


    public SystemTrayManager(Toolkit toolkit) {
        if (!SystemTray.isSupported()) return;
        GlobalScreen.setEventDispatcher(new SwingDispatchService());

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {}

        markdownImage = toolkit.getImage(markdownImageURL);
        disabledMarkdownImage = toolkit.getImage(disabledMarkdownImageURL);
        PopupMenu popup = new PopupMenu();

        trayIcon = new TrayIcon(markdownImage, "Unimark", popup);
        trayIcon.setImageAutoSize(true);
        trayIcon.addActionListener(e -> {
            setVisible(true);
        });

        MenuItem showConfigItem = new MenuItem("Show Config", new MenuShortcut(KeyEvent.VK_S));
        showConfigItem.addActionListener(e -> {
            setVisible(true);
        });
        popup.add(showConfigItem);

        disableItem = new MenuItem();
        disableItem.addActionListener(toggleDisabledStatus);
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
		setSize(300, 375);
		setDefaultCloseOperation(App.configManager.minimizeOnClose ? WindowConstants.HIDE_ON_CLOSE : WindowConstants.DISPOSE_ON_CLOSE);
        
		addWindowListener(this);
        setIconImage(markdownImage);
        setVisible(App.configManager.showConfigOnStart);

        createPanelLayout();
        updateDisabledButtons();
        updateKeyboardShortcutLabel();
        startKeyboardHook();
    }

    private void createPanelLayout() {

        // Create the main panel and title
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setAlignmentX(TOP_ALIGNMENT);
        mainPanel.setAlignmentY(CENTER_ALIGNMENT);
        add(mainPanel);

        JLabel label = new JLabel("Unimark");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Sans-Serif", Font.BOLD, 32));
        mainPanel.add(label);

        // Create the enabled panel
        JPanel enabledPanel = new JPanel();
        enabledPanel.setLayout(new BoxLayout(enabledPanel, BoxLayout.Y_AXIS));
        enabledPanel.setAlignmentY(CENTER_ALIGNMENT);
        enabledPanel.setBorder(new EmptyBorder(32, 0, 0, 0));
        mainPanel.add(enabledPanel);

        enabledTitle = new JLabel();
        enabledTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        enabledTitle.setFont(new Font("Sans-Serif", Font.PLAIN, 16));
        enabledPanel.add(enabledTitle);

        isDisabledButton = new JButton();
        isDisabledButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        isDisabledButton.addActionListener(toggleDisabledStatus);
        enabledPanel.add(isDisabledButton);

        // Create the keyboard shortcut panel
        JPanel shortcutPanel = new JPanel();
        shortcutPanel.setLayout(new BoxLayout(shortcutPanel, BoxLayout.Y_AXIS));
        shortcutPanel.setAlignmentY(CENTER_ALIGNMENT);
        shortcutPanel.setBorder(new EmptyBorder(16, 0, 0, 0));
        mainPanel.add(shortcutPanel);

        JLabel shortcutTitle = new JLabel("Keyboard Shortcut");
        shortcutTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        shortcutTitle.setFont(new Font("Sans-Serif", Font.PLAIN, 16));
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

        // Create the hide on close menu
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        settingsPanel.setAlignmentY(CENTER_ALIGNMENT);
        settingsPanel.setBorder(new EmptyBorder(16, 0, 0, 0));
        mainPanel.add(settingsPanel);

        JLabel settingsTitle = new JLabel("Settings");
        settingsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsTitle.setFont(new Font("Sans-Serif", Font.PLAIN, 16));
        settingsPanel.add(settingsTitle);

        JCheckBox closeBehavior = new JCheckBox("Minimize on Close");
        closeBehavior.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeBehavior.setSelected(App.configManager.minimizeOnClose);
        closeBehavior.addItemListener(e -> {
            App.configManager.minimizeOnClose = closeBehavior.isSelected();
            setDefaultCloseOperation(App.configManager.minimizeOnClose ? WindowConstants.HIDE_ON_CLOSE : WindowConstants.DISPOSE_ON_CLOSE);
        });
        settingsPanel.add(closeBehavior);

        JCheckBox showConfigOnStart = new JCheckBox("Show Config When Opened");
        showConfigOnStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        showConfigOnStart.setSelected(App.configManager.showConfigOnStart);
        showConfigOnStart.addItemListener(e -> {
            App.configManager.showConfigOnStart = showConfigOnStart.isSelected();
        });
        settingsPanel.add(showConfigOnStart);

        // Create the license and about pages
        JPanel aboutPanel = new JPanel();
        aboutPanel.setLayout(new BoxLayout(aboutPanel, BoxLayout.Y_AXIS));
        aboutPanel.setAlignmentY(CENTER_ALIGNMENT);
        aboutPanel.setBorder(new EmptyBorder(32, 0, 0, 0));
        mainPanel.add(aboutPanel);

        JButton license = new JButton("License");
        license.setAlignmentX(Component.CENTER_ALIGNMENT);
        license.addActionListener(e -> {
            EventQueue.invokeLater(() -> {
                try {
                    JFrame frame = new JFrame("License");
                    frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    frame.setIconImage(markdownImage);
                    frame.setVisible(true);
                    frame.setAlwaysOnTop(true);

                    JPanel main = new JPanel();
                    frame.add(main);

                    JTextArea licenseText = new JTextArea();
                    licenseText.read(new BufferedReader(new InputStreamReader(App.class.getResourceAsStream("/COPYING"))), null);
                    licenseText.setEditable(false);

                    JScrollPane scroll = new JScrollPane(licenseText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    frame.add(scroll);


                    frame.pack();
                } catch (Exception err) {
                    System.err.println("The COPYING file does not exist! This project requires the license text of the GNU GPL v3 found at https://www.gnu.org/licenses/gpl-3.0 to function!");
                    System.exit(1);
                }
            });
        });
        aboutPanel.add(license);
    }

    ActionListener toggleDisabledStatus = e -> {
        App.configManager.isDisabled = !App.configManager.isDisabled;
        updateDisabledButtons();
    };

    public void updateDisabledButtons() {
        String newLabel = App.configManager.isDisabled ? "Enable Unimark" : "Disable Unimark";
        disableItem.setLabel(newLabel);
        enabledTitle.setText(App.configManager.isDisabled ? "Currently Disabled" : "Currently Enabled");
        isDisabledButton.setText(newLabel);
        trayIcon.setImage(App.configManager.isDisabled ? disabledMarkdownImage : markdownImage);
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
        System.out.println("windows closed");
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