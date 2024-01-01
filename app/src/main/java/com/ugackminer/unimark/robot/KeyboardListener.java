package com.ugackminer.unimark.robot;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.github.kwhat.jnativehook.keyboard.SwingKeyAdapter;
import com.ugackminer.unimark.App;

public class KeyboardListener extends SwingKeyAdapter
{
    static ArrayList<Integer> newKeyboardShortcut;
    int pressedHoldKeyIndex = 0;

    /**
     * Runs every time a key gets pressed, and triggers the robot conversion when it detects CTRL+M
     * @param event The KeyEvent event
     */
    public void keyPressed(KeyEvent event) {   
        if (newKeyboardShortcut != null) {
            if (newKeyboardShortcut.contains(event.getKeyCode())) {
                System.out.println("Not Adding " + KeyEvent.getKeyText(event.getKeyCode()) + " because it is already there");
            } else {
                System.out.println("Adding " + KeyEvent.getKeyText(event.getKeyCode()));
                newKeyboardShortcut.add(event.getKeyCode());
            }
            return;
        }

        if (event.getKeyCode() == App.configManager.keyboardShortcut[pressedHoldKeyIndex]) {
            pressedHoldKeyIndex++;
        } else {
            pressedHoldKeyIndex = 0;
        }

        if (pressedHoldKeyIndex == App.configManager.keyboardShortcut.length) {
            try {
                App.startRobotConversion();
                pressedHoldKeyIndex = 0;
            } catch (Exception err) {
                System.err.println("There was an error!");
                System.err.println(err.toString());
            }
        }
    }   

    /**
     * Runs every time a key gets released, and disables the CTRL key toggle if CTRL is released
     * @param event The KeyEvent event
     */
    public void keyReleased(KeyEvent event) {
        if (newKeyboardShortcut != null) {
            System.out.println("Stopping! Key released!");
            int[] newKeyboardShortcutArr = new int[newKeyboardShortcut.size()];
            for (int i=0; i<newKeyboardShortcutArr.length; i++) {
                newKeyboardShortcutArr[i] = newKeyboardShortcut.get(i);
            }
            App.configManager.keyboardShortcut = newKeyboardShortcutArr;
            newKeyboardShortcut = null;
            App.systemTrayManager.updateKeyboardShortcutLabel();
        }

        pressedHoldKeyIndex = 0;
    }

    /**
     * Begins the process of changing the keyboard shortcut by initalizing the {@code newKeyboardShortcut}
     */
    public static void changeKeyboardShotcut() {
        System.out.println("Changing shortcut!");
        newKeyboardShortcut = new ArrayList<Integer>();
    }
}