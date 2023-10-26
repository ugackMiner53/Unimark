package com.ugackminer.unimark;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class KeyboardListener implements NativeKeyListener
{
    boolean ctrlKeyPressed;

    /**
     * Runs every time a key gets pressed, and triggers the robot conversion when it detects CTRL+M
     * @param event The jnativehook NativeKeyEvent
     */
    public void nativeKeyPressed(NativeKeyEvent event) {        
        if (event.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
            ctrlKeyPressed = true;
        } else if (ctrlKeyPressed && event.getKeyCode() == NativeKeyEvent.VC_M) {
            try {
                App.startRobotConversion();
                ctrlKeyPressed = false;
            } catch (Exception err) {
                System.err.println("There was an error!");
                System.err.println(err.toString());
            }
        }
    }   

    /**
     * Runs every time a key gets released, and disables the CTRL key toggle if CTRL is released
     * @param event The jnativehook NativeKeyEvent
     */
    public void nativeKeyReleased(NativeKeyEvent event) {
        if (event.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
            ctrlKeyPressed = false;
        }
    }
}