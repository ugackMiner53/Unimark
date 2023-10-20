package com.ugackminer.unimark;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class RobotManager {
    static final int modifierKey = App.isOnMacOS ? KeyEvent.VK_META : KeyEvent.VK_CONTROL;
    public Robot robot;

    public RobotManager() {
        try {
            this.robot = new Robot();
        } catch (AWTException err) {
            System.err.println("Could not initalize RobotManager!");
            System.err.println(err);
        }
    }

    public void pressShortcut(int shortcutKey) {
        robot.keyPress(modifierKey);
        robot.keyPress(shortcutKey);
        robot.keyRelease(shortcutKey);
        robot.keyRelease(modifierKey);
        
        robot.delay(100);
    }

}