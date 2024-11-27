package org.gruskas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.gruskas.TerminalUI.*;
//import static org.gruskas.TerminalDimensions.*;

public class InputHandler {
    static Scanner scanner = new Scanner(System.in);

    public static String selectAction() {
        System.out.print(ANSI_GREEN + "-> " + ANSI_RESET);
        return scanner.nextLine();
    }

    public static void Action(String input) {
        switch (input) {
            case ":q":
                Main.running = false;
                break;
            case ":n":
                try {
                    FileOperations.CreateFile(getFileName());
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

    private static String getFileName() {
        System.out.print("Enter File name: ");
        return scanner.nextLine();
    }

    private static ArrayList<String> getFileDetails(boolean append) {
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
