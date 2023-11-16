package com.ugackminer.unimark;

import java.awt.Toolkit;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;


public class App 
{
    static final boolean isOnMacOS = System.getProperty("os.name").toLowerCase().contains("mac");
    static Toolkit toolkit = Toolkit.getDefaultToolkit();
    static ClipboardManager clipboardManager = new ClipboardManager(toolkit.getSystemClipboard());
    static RobotManager robotManager = new RobotManager();
    static ShortcodeConverter shortcodeConverter = new ShortcodeConverter();

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
        // SystemTrayManager systemTrayManager = new SystemTrayManager();
    }

    /**
     * Presses keyboard shortcuts in order to copy the current text to the clipboard,
     * convert the markdown, and paste it back into the textbox.
     */
    public static void startRobotConversion() {
        robotManager.robot.keyRelease(KeyEvent.VK_CONTROL);
        robotManager.robot.keyRelease(KeyEvent.VK_M);

        Transferable previousClipboardContent = clipboardManager.getClipboardTransferable();

        robotManager.pressShortcut(KeyEvent.VK_A);
        robotManager.pressShortcut(KeyEvent.VK_X);

        App.startClipboardConversion();

        robotManager.pressShortcut(KeyEvent.VK_V);

        clipboardManager.setClipboardTransferable(previousClipboardContent);
    }

    /**
     * Takes text on the clipboard and runs it through the formatter,
     * then places it back on the clipboard
     */
    public static void startClipboardConversion() {
        String clipboardText = clipboardManager.getClipboardText();
        String clipboardHtml = clipboardManager.getClipboardHtmlText();
        System.out.println(clipboardHtml);
        System.out.println(clipboardText);
        System.out.println("");
        if (clipboardHtml != null && !clipboardHtml.equals(clipboardText))
            clipboardHtml = MarkdownParser.parseMarkdown(clipboardHtml);
        else
            clipboardHtml = clipboardText;
        clipboardText = MarkdownParser.parseMarkdown(clipboardText);
        System.out.println(clipboardHtml);
        System.out.println(clipboardText);
        System.out.println("");
        clipboardManager.setClipboardText(clipboardHtml, clipboardText);
    }
}