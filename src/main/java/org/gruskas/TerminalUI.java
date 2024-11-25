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
    public static final String ANSI_BOLD = "\u001B[1m";
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
        if (files.isEmpty()) {
            System.out.println(ANSI_RED + "There are no files." + ANSI_RESET);
        } else {
            int index = 1;
            int longest = 0;

            for (Path file : files) {
                String fileName = file.getFileName().toString();
                fileName = fileName.substring(0, fileName.length() - 4);
                if (fileName.length() > longest) {
                    longest = fileName.length();
                }
            }

            longest = Math.max(longest, 10) + 6;
            String HeightLine = " ".repeat(longest);
            System.out.println(ANSI_RED + "+" + "-".repeat(longest) + "+" + ANSI_RESET);
            System.out.println(ANSI_RED + "| " + ANSI_RESET + ANSI_BOLD + "Nr" + ANSI_RESET + ANSI_RED + " | " + ANSI_RESET + ANSI_BOLD + "File Name" + ANSI_RESET + " ".repeat(longest - 16) + ANSI_RED + " |" + ANSI_RESET);
            System.out.println(ANSI_RED + "+" + "-".repeat(longest) + "+" + ANSI_RESET);

            for (Path file : files) {
                String fileName = file.getFileName().toString();
                fileName = fileName.substring(0, fileName.length() - 4);
                System.out.println(ANSI_RED + "| " + ANSI_RESET + index++ + ANSI_RED + "  | " + ANSI_RESET + fileName + " ".repeat(longest - fileName.length() - 7) + ANSI_RED + " |" + ANSI_RESET);
            }
            System.out.println(ANSI_RED + "+" + "-".repeat(longest) + "+" + ANSI_RESET);
        }
    }
}