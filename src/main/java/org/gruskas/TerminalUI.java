package org.gruskas;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.gruskas.FileOperations.findTxtFiles;

public class TerminalUI {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BOLD = "\u001B[1m";
    public static final String RED_BRIGHT = "\033[0;91m";    // RED
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
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
                
                %s""", RED_BRIGHT, ANSI_RESET));
    }

    public static void showFiles() throws IOException {
        ArrayList<Path> files = findTxtFiles();
        if (files.isEmpty()) {
//            System.out.println(ANSI_RED + "There are no files." + ANSI_RESET);
            Error("There are no files.");
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

    public static void Error(String message) {
        System.out.println(ANSI_RED + "[-] ERROR: " + message + ANSI_RESET);
    }

    public static void Error(String message, String details) {
        System.out.print(ANSI_RED + message + ANSI_RESET);
        System.out.println(details);
    }

    public static void warn(String message) {
        System.out.println(ANSI_YELLOW + "[!] WARNING: " + message + ANSI_RESET);
    }
}