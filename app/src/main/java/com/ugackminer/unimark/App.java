package com.ugackminer.unimark;

import java.util.Timer;
import java.util.TimerTask;

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
    static Toolkit toolkit = Toolkit.getDefaultToolkit();
    static ClipboardManager clipboardManager = new ClipboardManager(toolkit.getSystemClipboard());
    static RobotManager robotManager = new RobotManager();
    
    static Timer conversionTimer = new Timer("Conversion Timer");
    static Transferable previousClipboardContent;

    public static void main(String[] args) throws AWTException {
        System.out.println(System.getProperty("os.name"));
        SwingUtilities.invokeLater(() -> {
            SystemTrayManager systemTrayManager = new SystemTrayManager(toolkit);
        });
    }

    /**
     * Presses keyboard shortcuts in order to copy the current text to the clipboard,
     * convert the markdown, and paste it back into the textbox.
     */
    public static void startRobotConversion() {
        robotManager.robot.keyRelease(KeyEvent.VK_CONTROL);
        robotManager.robot.keyRelease(KeyEvent.VK_M);

        App.previousClipboardContent = clipboardManager.getClipboardTransferable();
        
        robotManager.pressShortcut(KeyEvent.VK_A);
        robotManager.pressShortcut(KeyEvent.VK_X);

        conversionTimer.schedule(convertTextTask, 100);
    }

    static TimerTask convertTextTask = new TimerTask() {
        @Override
        public void run() {
            App.startClipboardConversion();
            robotManager.pressShortcut(KeyEvent.VK_V);
            conversionTimer.schedule(resetTimerTask, 100);
        }
    };

    static TimerTask resetTimerTask = new TimerTask() {
        @Override
        public void run() {
            clipboardManager.setClipboardTransferable(App.previousClipboardContent);
        }
    };

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