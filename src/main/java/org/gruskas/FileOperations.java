package org.gruskas;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileOperations {
    public static String folderPath = "." + File.separator + "files";
    public ArrayList<String> txtFiles = new ArrayList<>();

    public static void createDirectory() {
        Path path = Paths.get(folderPath);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                TerminalUI.Error("Failed to create directory: ", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<Path> findTxtFiles() throws IOException {
        Path path = Paths.get(folderPath);
        ArrayList<Path> txtFiles = new ArrayList<>();

        if (Files.exists(path)) {
            Files.walk(path)
                    .filter(x -> {
                        return x.toString().endsWith(".txt");
                    })
                    .forEach(txtFiles::add);
        }
        return txtFiles;
    }

    public static void CreateFile(String name) throws IOException {
        try {
            File file = new File(folderPath + File.separator + name + ".txt");
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                TerminalUI.warn("File already exists.");
            }
        } catch (IOException e) {
            TerminalUI.Error("Error while creating file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void ReadFile(String name) {
        if (!name.isEmpty()) {
            try {
                int i = 1;
                File file = new File(folderPath + File.separator + name + ".txt");
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String data = scanner.nextLine();
                    System.out.println(i + ". " + data);
                    i++;
                }
            } catch (FileNotFoundException e) {
                TerminalUI.Error("An error occurred while reading the file: ", e.getMessage());
            }
        }
    }

    public static void DeleteFile(String name) {
        File file = new File(folderPath + File.separator + name + ".txt");
//        System.out.println(file.getAbsolutePath());
        if (file.delete()) {
            System.out.println("The file was successfully deleted: " + file.getName());
        } else {
            TerminalUI.Error("This file does not exist");
        }
    }

    public static void DeleteLine(String fileName, int lineNumber) {
        Path filePath = Paths.get(folderPath + File.separator + fileName + ".txt");
        try {
            ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(filePath);

            if (lineNumber > 0 && lineNumber <= lines.size()) {
                lines.remove(lineNumber - 1);
                Files.write(filePath, lines);
                System.out.println("Line " + lineNumber + " has been deleted from the file: " + fileName);
            } else {
                TerminalUI.Error("Invalid line number.");
            }
        } catch (IOException e) {
            TerminalUI.Error("An error occurred while deleting the line: " + e.getMessage());
        }
    }

    public static void UpdateLine(String fileName, int lineNumber, String newContent) {
        Path filePath = Paths.get(folderPath + File.separator + fileName + ".txt");
        try {
            ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(filePath);

            if (lineNumber > 0 && lineNumber <= lines.size()) {
                String formattedDate = DateTimeNow();
                lines.set(lineNumber - 1, newContent + " | " + formattedDate);
                Files.write(filePath, lines);
                System.out.println("Line " + lineNumber + " has been updated in the file: " + fileName);
            } else {
                TerminalUI.Error("Invalid line number.");
            }
        } catch (IOException e) {
            TerminalUI.Error("An error occurred while updating the line: ", e.getMessage());
        }
    }

    public static void WriteToFile(String name, String content, Boolean append) {
        try {
            String formattedDate = DateTimeNow();
            FileWriter Writer = new FileWriter(folderPath + File.separator + name + ".txt", append);

            Writer.write(content + " | " + formattedDate + System.lineSeparator());
            Writer.close();
        } catch (IOException e) {
            TerminalUI.Error("An error occurred while writing to the file: ", e.getMessage());
        }
    }

    public static String DateTimeNow() {
        LocalDateTime DateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.format(DateTime);
    }
}