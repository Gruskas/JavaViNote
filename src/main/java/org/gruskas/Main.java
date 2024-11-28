package org.gruskas;

import java.io.IOException;

public class Main {
    public static boolean running = true;
    public static boolean start = true;

    public static void main(String[] args) throws IOException {
        ClearTerminal.clear();
        FileOperations.createDirectory();
        TerminalUI.printBanner();

        try {
            while (running) {
                TerminalUI.showFiles();
                TerminalUI.positionPrompt(start);
                String action = InputHandler.selectAction();
                InputHandler.Action(action);
            }
        } finally {
            System.out.println(TerminalUI.CYAN_BOLD + "Exiting the program.");
            System.exit(0);
        }
    }
}