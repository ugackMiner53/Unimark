package com.ugackminer.unimark;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class KeyboardListener implements NativeKeyListener
{
    boolean ctrlKeyPressed;

    public void nativeKeyPressed(NativeKeyEvent event) {        
        if (event.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
            ctrlKeyPressed = true;
        } else if (ctrlKeyPressed && event.getKeyCode() == NativeKeyEvent.VC_M) {
            try {
                App.despacito();
                ctrlKeyPressed = false;
            } catch (Exception err) {
                System.err.println("There was an error!");
                System.err.println(err.toString());
            }
        }
    }   

    public void nativeKeyReleased(NativeKeyEvent event) {
        if (event.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
            ctrlKeyPressed = false;
        }
    }
}