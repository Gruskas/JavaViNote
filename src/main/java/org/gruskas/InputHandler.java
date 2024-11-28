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
                Main.running = false;
                break;
            case ":n":
                try {
                    FileOperations.CreateFile(getFileNameC());
                } catch (IOException e) {
                    TerminalUI.Error("Failed to create file: ", e.getMessage());
                    e.printStackTrace();
                }
                break;
            case ":d":
                FileOperations.DeleteFile(getFileName());
                break;
            case ":dl":
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
                FileOperations.ReadFile(getFileName());
                break;
            case ":ow":
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
            case ":uf":
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
            default:
                TerminalUI.warn("Invalid input");
        }
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
            ArrayList<Path> files = FileOperations.findTxtFiles();
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
