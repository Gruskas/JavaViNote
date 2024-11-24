package org.gruskas;

import java.io.IOException;

public class Main {
    public static boolean running = true;

    public static void main(String[] args) throws IOException {
        ClearTerminal.clear();
        FileOperations.createDirectory();
        TerminalUI.printBanner();

        try {
            while (running) {
                TerminalUI.showFiles();
                String action = InputHandler.selectAction();
                InputHandler.Action(action);
            }
        } finally {
            System.out.println("Exiting the program.");
            System.exit(0);
        }
    }
}