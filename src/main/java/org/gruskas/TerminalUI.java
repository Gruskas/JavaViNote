package org.gruskas;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;


public class TerminalUI {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BOLD = "\u001B[1m";
    public static final String RED_BRIGHT = "\033[0;91m";
    public static final String CYAN_BOLD = "\033[1;36m";
    public static final String GREEN_BRIGHT = "\033[0;92m";

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
        ArrayList<Path> files = FileOperations.findTxtFiles();
        String encryption;

        if (files.isEmpty()) {
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

            ArrayList<String> lastModifiedDates = FileOperations.getLastModifiedDates(files);
            longest = Math.max(longest, 10) + 7;

            System.out.println(ANSI_RED + "+" + "-".repeat(longest + 33) + "+");
            System.out.println(ANSI_RED + "| " + ANSI_RESET + ANSI_BOLD + "Nr" + ANSI_RESET + ANSI_RED + " | " + ANSI_RESET + ANSI_BOLD + "File Name" + ANSI_RESET + " ".repeat(longest - 16) + ANSI_RED + " | " + ANSI_RESET + ANSI_BOLD + "Last Modification" + ANSI_RESET + ANSI_RED + " | " + ANSI_RESET + ANSI_BOLD + "Encryption" + ANSI_RESET + ANSI_RED + " |" );
            System.out.println(ANSI_RED + "+" + "-".repeat(longest + 33) + "+");

            for (int i = 0; i < files.size(); i++) {
                String fileName = files.get(i).getFileName().toString();
                if (fileName.endsWith(".txt")) {
                    fileName = fileName.substring(0, fileName.length() - 4);
                    encryption = "    NO    ";
                } else {
                    fileName = fileName.substring(0, fileName.length() - 8);
                    encryption = "    YES   ";
                }
                String lastModified = lastModifiedDates.get(i);
                System.out.println(ANSI_RED + "| " + ANSI_RESET + index++ + ANSI_RED + "  | " + ANSI_RESET + fileName + " ".repeat(Math.max(0, longest - fileName.length() - 7)) + ANSI_RED + " | " + ANSI_RESET + lastModified + ANSI_RED + " | " + ANSI_RESET + encryption + ANSI_RED + " |");
            }
            System.out.println(ANSI_RED + "+" + "-".repeat(longest + 33) + "+" + ANSI_RESET);
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

    public static void success(String message) {
        System.out.println(GREEN_BRIGHT + "[+] SUCCESS: " + message + ANSI_RESET);
    }

    public static void success(String message, String details) {
        System.out.print(GREEN_BRIGHT + "[+] SUCCESS: " + message + ANSI_RESET);
        System.out.println(details);
    }

    public static void success(String message, int details, String message2, String details2) {
        System.out.println(GREEN_BRIGHT + "[+] SUCCESS: " + message + ANSI_RESET + details + GREEN_BRIGHT + message2 + ANSI_RESET + details2);
    }

    public static void positionPrompt(Boolean start) {
        try (Terminal terminal = TerminalBuilder.terminal()) {

            int linesToMoveDown = terminal.getHeight() - getLinesCount() - 1;

            if (start) {
                Main.start = false;
                for (int i = 0; i < linesToMoveDown - 12; i++) {
                    terminal.writer().println();
                }
            } else {
                for (int i = 0; i < linesToMoveDown - 2; i++) {
                    terminal.writer().println();
                }
            }

            terminal.flush();
        } catch (IOException e) {
            TerminalUI.Error("Error: " + e.getMessage());
        }
    }

    private static int getLinesCount() throws IOException {
        ArrayList<Path> files = FileOperations.txtFiles;
        return files.size() + 3;
    }

}