package com.ugackminer.unimark;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.awt.Toolkit;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.awt.AWTException;

import javax.swing.SwingUtilities;

import com.ugackminer.unimark.robot.ClipboardManager;
import com.ugackminer.unimark.robot.RobotManager;
import com.ugackminer.unimark.tray.SystemTrayManager;
import com.ugackminer.unimark.unicode.MarkdownParser;


public class App 
{
    public static final boolean isOnMacOS = System.getProperty("os.name").toLowerCase().contains("mac");
    public static boolean isDisabled = false;

    public static int[] keyboardShortcut = {KeyEvent.VK_CONTROL, KeyEvent.VK_M};

    public static SystemTrayManager systemTrayManager;
    static Toolkit toolkit = Toolkit.getDefaultToolkit();
    static ClipboardManager clipboardManager = new ClipboardManager(toolkit.getSystemClipboard());
    static RobotManager robotManager = new RobotManager();
    
    static ScheduledExecutorService conversionService = Executors.newScheduledThreadPool(1);
    static Transferable previousClipboardContent;

    public static void main(String[] args) throws AWTException {
        System.out.println(System.getProperty("os.name"));
        SwingUtilities.invokeLater(() -> {
            systemTrayManager = new SystemTrayManager(toolkit);
        });
    }

    /**
     * Presses keyboard shortcuts in order to copy the current text to the clipboard,
     * convert the markdown, and paste it back into the textbox.
     */
    public static void startRobotConversion() {
        if (isDisabled) return;
        for (int keycode : keyboardShortcut) {
            robotManager.robot.keyRelease(keycode);
        }

        App.previousClipboardContent = clipboardManager.getClipboardTransferable();
        
        robotManager.pressShortcut(KeyEvent.VK_A);
        robotManager.pressShortcut(KeyEvent.VK_X);

        conversionService.schedule(() -> {
            App.startClipboardConversion();
            robotManager.pressShortcut(KeyEvent.VK_V);
            conversionService.schedule(() -> {
                clipboardManager.setClipboardTransferable(App.previousClipboardContent);
            }, 100, TimeUnit.MILLISECONDS);
        }, 100, TimeUnit.MILLISECONDS);
    }

    /**
     * Takes text on the clipboard and runs it through the formatter,
     * then places it back on the clipboard
     */
    public static void startClipboardConversion() {
        String clipboardText = clipboardManager.getClipboardText();
        String clipboardHtml = clipboardManager.getClipboardHtmlText();

        if (clipboardHtml != null && !clipboardHtml.equals(clipboardText))
            clipboardHtml = MarkdownParser.parseMarkdown(clipboardHtml);
        else
            clipboardHtml = clipboardText;
        clipboardText = MarkdownParser.parseMarkdown(clipboardText);
        clipboardManager.setClipboardText(clipboardHtml, clipboardText);
    }
}