package org.gruskas;

import java.io.IOException;
import java.util.Scanner;

public class InputHandler {

    public static String selectAction() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("-> ");
        return scanner.nextLine();
    }

    public static void Action(String input) {
        switch (input) {
            case ":q":
                Main.running = false;
                System.exit(0);
                break;
            case ":n":
                try {
                    FileOperations.CreateFile(FileName());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case ":d":
                FileOperations.DeleteFile(FileName());
                break;
            case ":o":
                FileOperations.ReadFile(FileName());
                break;
            case ":ow":
                try {
                    boolean append = false;
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("Enter File name: ");
                    String FileName = scanner.nextLine();
                    System.out.print("Enter a sentence(overwrites the contents of the file): ");
                    String content = scanner.nextLine();
                    FileOperations.WriteToFile(FileName, content, append);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                break;
            case ":a":
                try {
                    boolean append = true;
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("Enter File name: ");
                    String FileName = scanner.nextLine();
                    System.out.print("Enter a sentence: ");
                    String content = scanner.nextLine();
                    FileOperations.WriteToFile(FileName, content, append);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                System.out.println("Invalid input");
        }
    }

    private static String FileName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter File name: ");
        return scanner.nextLine();
    }
}
