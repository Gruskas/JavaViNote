package org.gruskas;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class InputHandler {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    public static String selectAction() {
        System.out.print(TerminalUI.ANSI_BOLD + TerminalUI.ANSI_GREEN + "-> " + TerminalUI.ANSI_RESET);
        return scanner.nextLine();
    }

    public static void Action(String input) {
        input = input.replaceAll("\\s", "");
        switch (input) {
            case ":q":
            case "quit":
                Main.running = false;
                break;
            case ":n":
            case "newfile":
                try {
                    FileOperations.CreateFile(getFileNameC());
                } catch (IOException e) {
                    TerminalUI.Error("Failed to create file: ", e.getMessage());
                    e.printStackTrace();
                }
                break;
            case ":d":
            case "del":
                FileOperations.DeleteFile(getFileName());
                break;
            case ":dl":
            case "del-line":
                try {
                    System.out.print("Enter a line to delete: ");
                    int line = scanner.nextInt();
                    scanner.nextLine();
                    FileOperations.DeleteLine(getFileName(), line);
                } catch (Exception e) {
                    TerminalUI.Error("Error while deleting line", e.getMessage());
                    e.printStackTrace();
                }
                break;
            case ":o":
            case "open":
                FileOperations.ReadFile(getFileName());
                break;
            case ":ow":
            case "overwrite":
                try {
                    boolean append = false;
                    ArrayList<String> details = getFileDetails(append);
                    String fileName = details.get(0);
                    String content = details.get(1);
                    FileOperations.WriteToFile(fileName, content, append);
                } catch (Exception e) {
                    TerminalUI.Error("Unexpected error: " + e.getMessage());
                    e.printStackTrace();
                }
                break;
            case ":a":
            case "append":
                try {
                    boolean append = true;
                    ArrayList<String> details = getFileDetails(append);
                    String fileName = details.get(0);
                    String content = details.get(1);
                    FileOperations.WriteToFile(fileName, content, append);
                } catch (Exception e) {
                    TerminalUI.Error("Unexpected error: " + e.getMessage());
                    e.printStackTrace();
                }
                break;
            case ":ul":
            case "update-line":
                try {
                    boolean append = true;
                    ArrayList<String> details = getFileDetails(append);
                    System.out.print("Enter the lines to replace: ");
                    int line = scanner.nextInt();
                    scanner.nextLine();
                    String fileName = details.get(0);
                    String newContent = details.get(1);
                    FileOperations.UpdateLine(fileName, line, newContent);
                } catch (Exception e) {
                    TerminalUI.Error("Unexpected error: " + e.getMessage());
                    e.printStackTrace();
                }
                break;
            case ":r":
            case "rename":
                FileOperations.RenameFile(getFileName(), getFileNameC());
                break;
            case ":h":
            case "help":
                printHelp();
                break;
            default:
                TerminalUI.warn("Invalid input. For help, try 'help'");
        }
    }

    private static void printHelp() {
        System.out.println("Available Commands:");
        System.out.println("    :h, help            - Show this help message.");
        System.out.println("    :q, quit            - Quit the program.");
        System.out.println("    :n, newfile         - Create a new file.");
        System.out.println("    :d, del             - Delete a specified file.");
        System.out.println("    :dl, del-line       - Delete a specific line from the file.");
        System.out.println("    :o, open            - Open and read the content of the file.");
        System.out.println("    :ow, overwrite      - Overwrite the file with new content.");
        System.out.println("    :a, append          - Append content to the file.");
        System.out.println("    :ul, update-line    - Update (replace) a specific line in the file.");
        System.out.println("    :r, rename          - Rename the specified file.");
    }

    private static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static String getFileNameC() {
        System.out.print("Enter File name: ");
        return scanner.nextLine();
    }

    private static String getFileName() {
        try {
            ArrayList<Path> files = FileOperations.txtFiles;
            boolean randomBoolean = random.nextBoolean();

            if (randomBoolean) {
                System.out.print("Enter File name or number: ");
            } else {
                System.out.print("Enter File String or int: ");
            }

            String input = scanner.nextLine();

            if (isInteger(input)) {
                int fileNumber = Integer.parseInt(input);

                if (fileNumber < 1 || fileNumber > files.size()) {
                    TerminalUI.Error("Invalid number selected.");
                    return "";
                }

                String fileName = files.get(fileNumber - 1).getFileName().toString();
                return fileName.substring(0, fileName.length() - 4);
            } else {
                return input;
            }
        } catch (NumberFormatException e) {
            TerminalUI.Error("Invalid input.");
            return "";
        } catch (Exception e) {
            TerminalUI.Error("Unexpected error: " + e.getMessage());
            return "";
        }
    }

    private static ArrayList getFileDetails(boolean append) {
        ArrayList<String> list = new ArrayList<>();
        list.add(getFileName());
        if (append) {
            System.out.print("Enter a sentence: ");
        } else {
            TerminalUI.warn("Overwrites the contents of the file!");
            System.out.print("Enter a sentence: ");
        }
        list.add(scanner.nextLine());
        return list;
    }
}
