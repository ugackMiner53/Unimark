package com.ugackminer.unimark;

import java.awt.Toolkit;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;

// import com.github.kwhat.jnativehook.GlobalScreen;
// import com.github.kwhat.jnativehook.NativeHookException;


public class App 
{
    static final boolean isOnMacOS = System.getProperty("os.name").toLowerCase().contains("mac");
    static Toolkit toolkit = Toolkit.getDefaultToolkit();
    static ClipboardManager clipboardManager = new ClipboardManager(toolkit.getSystemClipboard());
    static RobotManager robotManager = new RobotManager();

    public static void main(String[] args) {
        System.out.println(System.getProperty("os.name"));
        
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException err) {
            System.err.println("The program could not register the native keybind hooking (jnativehook library issue)!");
            System.err.println(err.getMessage());
            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new KeyboardListener());
        // SystemTrayManager systemTrayManager = new SystemTrayManager(toolkit);
    }

    public static void despacito() {
        robotManager.robot.keyRelease(KeyEvent.VK_CONTROL);
        robotManager.robot.keyRelease(KeyEvent.VK_M);

        Transferable previousClipboardContent = clipboardManager.getClipboardTransferable();

        robotManager.pressShortcut(KeyEvent.VK_A);
        robotManager.pressShortcut(KeyEvent.VK_X);

        String clipboardText = clipboardManager.getClipboardText();
        clipboardText = MarkdownParser.parseMarkdown(clipboardText);
        clipboardManager.setClipboardText(clipboardText);

        robotManager.pressShortcut(KeyEvent.VK_V);

        clipboardManager.setClipboardTransferable(previousClipboardContent);
    }
}