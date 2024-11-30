package org.gruskas;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class ArgumentsEditor {
    public static void ReadFile(String path) {
        File file = new File(path);
        try (Scanner scanner = new Scanner(file)) {
            int i = 1;
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                System.out.println(i + ". " + data);
                i++;
            }
        } catch (FileNotFoundException e) {
            TerminalUI.Error("An error occurred while reading the file: ", e.getMessage());
        }
    }

    public static void DeleteFile(String path) {
        File file = new File(path);
//        System.out.println(file.getAbsolutePath());
        if (file.delete()) {
            TerminalUI.success("The file was successfully deleted: ", file.getName());
        } else {
            TerminalUI.Error("This file does not exist");
        }
    }


    public static void UpdateLine(String path, int lineNumber, String newContent) {
        Path filePath = Paths.get(path);
        try {
            ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(filePath);

            if (lineNumber > 0 && lineNumber <= lines.size()) {
                String formattedDate = FileOperations.DateTimeNow();
                lines.set(lineNumber - 1, newContent + " | " + formattedDate);
                Files.write(filePath, lines);
                System.out.println("Line " + lineNumber + " has been updated in the file: " + path);
            } else {
                TerminalUI.Error("Invalid line number.");
            }
        } catch (IOException e) {
            TerminalUI.Error("An error occurred while updating the line: ", e.getMessage());
        }
    }

    public static void WriteToFile(String path, String content, Boolean append) {
        try (FileWriter Writer = new FileWriter(path, append)) {
            String formattedDate = FileOperations.DateTimeNow();
            Writer.write(content + " | " + formattedDate + System.lineSeparator());
        } catch (IOException e) {
            TerminalUI.Error("An error occurred while writing to the file: ", e.getMessage());
        }
    }
}
