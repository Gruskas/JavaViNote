package org.gruskas;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.gruskas.TerminalUI.*;

public class EditMode {
    private static final Scanner scanner = new Scanner(System.in);
    public static boolean running = false;
    public static ArrayList<String> content = new ArrayList<>();
    public static ArrayList<String> oldContent = new ArrayList<>();
    private static File file = null;

    public static void editMode(String inputFile) {
        EditMode.file = new File(inputFile);
        try {
            readFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        oldContent = new ArrayList<>(content);
        while (running) {
            ClearTerminal.clear();
            showFile();
            System.out.println(ANSI_YELLOW + "Actions: [q] Quit [e] Edit Line [a] Add Line [d] Delete Line [w] Save [wq] Write And Quit [c] Clear [sd] Show Differences");
//            System.out.println(oldContent);
//            System.out.println(content);
            String input = InputHandler.selectAction();
            action(input);
        }
    }

    public static void readFile() throws FileNotFoundException {
        try (Scanner scanner = new Scanner(file)) {
            content.clear();
            while (scanner.hasNextLine()) {
                content.add(scanner.nextLine());
            }
        }
    }


    public static void showFile() {
        int index = 1;
        int longest = 0;

        if (content.isEmpty()) {
            System.out.println(ANSI_RED + "+" + "-".repeat( 16) + "+");
            System.out.println("| "+ ANSI_RESET + ANSI_BOLD + "File is empty." + ANSI_RESET + ANSI_RED + " |");
            System.out.println(ANSI_RED + "+" + "-".repeat( 16) + "+");
        } else {
            for (String line : content) {
                Pattern pattern = Pattern.compile("^(.*)\\s+\\|\\s+(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2})$");
                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) {
                    String text = matcher.group(1);
                    if (text.length() > longest) {
                        longest = text.length();
                    }
                }
            }
            if (longest < 6) {
                longest = 10;
            }
            System.out.println(ANSI_RED + "+" + "-".repeat(longest + 28) + "+");
            System.out.println(ANSI_RED + "| " + ANSI_RESET + ANSI_BOLD + "Edit Mode" + " ".repeat(longest - 5) + ANSI_RESET + ANSI_RED + " |  " + ANSI_RESET + ANSI_BOLD + "Last Modification" + ANSI_RESET + ANSI_RED + "  |");
            System.out.println(ANSI_RED + "+" + "-".repeat(longest + 28) + "+");

            for (String line : content) {
                Pattern pattern = Pattern.compile("^(.*)\\s+\\|\\s+(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2})$");
                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) {
                    String text = matcher.group(1);
                    String date = matcher.group(2);

                    System.out.println(ANSI_RED + "| " + ANSI_RESET + index++ + ANSI_RED + " | " + ANSI_RESET + text + " ".repeat(longest - text.length()) + ANSI_RED + " | " + ANSI_RESET + date + ANSI_RED + " |");
                }
            }
            System.out.println(ANSI_RED + "+" + "-".repeat(longest + 28) + "+");
        }
    }

    public static void action(String input) {
        switch (input) {
            case "q":
            case "quit":
                running = false;
                break;
            case "d":
                try {
                    System.out.print("Enter a line to delete: ");
                    int line = scanner.nextInt();
                    deleteLine(line);
                } catch (InputMismatchException e) {
                    TerminalUI.Error("Error deleting line.");
                    scanner.nextLine();
                    System.out.println("Press Enter to continue...");
                    scanner.nextLine();
                }
                break;
            case "a":
                System.out.print("Enter a sentence: ");
                String newContent = scanner.nextLine();
                addLine(newContent);
                break;
            case "c":
                clearArray();
                break;
            case "w":
                WriteToFile(false);
                break;
            case "wq":
                WriteToFile(true);
                break;
            case "sd":
                showDifferences();
                break;
            case "e":
                try {
                    System.out.print("Enter line number: ");
                    int lineToReplace = scanner.nextInt();
                    System.out.print("Enter a sentence: ");
                    String newContent2 = scanner.nextLine();
                    replaceLine(lineToReplace, newContent2);
                } catch (NumberFormatException e) {
                    TerminalUI.Error("Wrong line number");
                }
                break;
        }
    }

    private static void clearArray() {
        content.clear();
    }

    public static void addLine(String line) {
        String formattedDate = FileOperations.DateTimeNow();
        content.add(line + " | " + formattedDate);
    }

    private static void deleteLine(int line) {
        content.remove(line - 1);
    }

    private static void replaceLine(int line, String newContent) {
        String formattedDate = FileOperations.DateTimeNow();
        content.set(line - 1, newContent + " | " + formattedDate);
    }

    public static void WriteToFile(boolean wq) {
        try (FileWriter Writer = new FileWriter(file)) {
            for (String line : content) {
                Writer.write(line + System.lineSeparator());
            }
            if (wq) {
                running = false;
            }
        } catch (IOException e) {
            TerminalUI.Error("An error occurred while writing to the file: ", e.getMessage());
        }
    }

    public static void showDifferences() {
        System.out.println(ANSI_YELLOW + "Changes:" + ANSI_RESET);

        for (int i = 0; i < oldContent.size(); i++) {
            if (i >= content.size() || !content.get(i).equals(oldContent.get(i))) {
                System.out.println(ANSI_RED + "- Removed: " + oldContent.get(i) + ANSI_RESET);
            }
        }

        for (int i = 0; i < content.size(); i++) {
            if (i >= oldContent.size() || !content.get(i).equals(oldContent.get(i))) {
                System.out.println(ANSI_GREEN + "+ Added: " + content.get(i) + ANSI_RESET);
            }
        }
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
}
