package org.gruskas;

import java.io.IOException;

public class Main {
    public static boolean running = true;
    public static boolean start = true;
    public static boolean windows = ClearTerminal.getOperatingSystem();

    public static void main(String[] args) throws IOException {
        if (args.length > 0) {
            Arguments.checkArguments(args);
            System.exit(0);
        }
        ClearTerminal.clear();
        FileOperations.createDirectory();
        TerminalUI.printBanner();

        try {
            while (running) {
                TerminalUI.showFiles();
                if (!windows) {
                    TerminalUI.positionPrompt(start);
                }
                String action = InputHandler.selectAction();
                InputHandler.Action(action);
            }
        } finally {
            System.out.println(TerminalUI.CYAN_BOLD + "Exiting the program." + TerminalUI.ANSI_RESET);
            System.exit(0);
        }
    }
}