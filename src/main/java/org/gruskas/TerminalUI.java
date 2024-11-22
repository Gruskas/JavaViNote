package org.gruskas;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.gruskas.FileOperations.findTxtFiles;

public class TerminalUI {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    private static FileOperations KeyHandler;

    public static void printBanner() {
        System.out.println(String.format("""
                %s
                    _____                              __     __  __  __    __             __              
                   |     \\                            |  \\   |  \\|  \\|  \\  |  \\           |  \\             
                    \\$$$$$  ______  __     __  ______ | $$   | $$ \\$$| $$\\ | $$  ______  _| $$_     ______ 
                      | $$ |      \\|  \\   /  \\|      \\| $$   | $$|  \\| $$$\\| $$ /      \\|   $$ \\   /      \\
                 __   | $$  \\$$$$$$\\\\$$\\ /  $$ \\$$$$$$\\\\$$\\ /  $$| $$| $$$$\\ $$|  $$$$$$\\\\$$$$$$  |  $$$$$$\\
                |  \\  | $$ /      $$ \\$$\\  $$ /      $$ \\$$\\  $$ | $$| $$\\$$ $$| $$  | $$ | $$ __ | $$    $$
                | $$__| $$|  $$$$$$$  \\$$ $$ |  $$$$$$$  \\$$ $$  | $$| $$ \\$$$$| $$__/ $$ | $$|  \\| $$$$$$$$
                 \\$$    $$ \\$$    $$   \\$$$   \\$$    $$   \\$$$   | $$| $$  \\$$$ \\$$    $$  \\$$  $$ \\$$     \\
                  \\$$$$$$   \\$$$$$$$    \\$     \\$$$$$$$    \\$     \\$$ \\$$   \\$$  \\$$$$$$    \\$$$$   \\$$$$$$$
                
                %s""", ANSI_RED, ANSI_RESET));
    }

    public static void showFiles() throws IOException {
        ArrayList<Path> files = findTxtFiles();

        for (Path file : files) {
            String fileName = file.getFileName().toString();
            fileName = fileName.substring(0, fileName.length() - 4);
            System.out.println(fileName);
        }
    }
}