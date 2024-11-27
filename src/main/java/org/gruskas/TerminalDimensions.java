package org.gruskas;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class TerminalDimensions {
    public static int getTerminalWidth() {
        try {
            Terminal terminal = TerminalBuilder.terminal();
            int width = terminal.getWidth();
            terminal.close();
            return width;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // Method to return terminal height
    public static int getTerminalHeight() {
        try {
            Terminal terminal = TerminalBuilder.terminal();
            int height = terminal.getHeight();
            terminal.close();
            return height;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}