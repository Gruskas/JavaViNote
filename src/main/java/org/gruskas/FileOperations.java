package org.gruskas;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class FileOperations {
    public static String folderPath = "." + File.separator + "files";
    public static ArrayList<Path> txtFiles = new ArrayList<>();

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
                    .filter(x -> x.toString().endsWith(".txt") || x.toString().endsWith(".txt.enc"))
                    .forEach(txtFiles::add);
        }
        FileOperations.txtFiles = txtFiles;
        return txtFiles;
    }

    public static ArrayList<String> getLastModifiedDates(ArrayList<Path> txtFiles) {
        ArrayList<String> lastModifiedDates = new ArrayList<>();
        SimpleDateFormat lastModifiedDate = new SimpleDateFormat("yyyy-MM-dd  HH:mm");

        for (Path file : txtFiles) {
            try {
                long lastModified = Files.getLastModifiedTime(file).toMillis();
                lastModifiedDates.add(lastModifiedDate.format(new Date(lastModified)));
            } catch (Exception e) {
                lastModifiedDates.add("Error");
            }
        }
        return lastModifiedDates;
    }

    public static void CreateFile(String name) throws IOException {
        try {
            File file = new File(folderPath + File.separator + name + ".txt");
            if (file.createNewFile()) {
                TerminalUI.success("File created: ", file.getName());
            } else {
                TerminalUI.warn("File already exists.");
            }
        } catch (IOException e) {
            TerminalUI.Error("Error while creating file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void ReadFile(String name) {
        if (isEncrypted(name)) {
            TerminalUI.Error("Cannot open an encrypted file. Please decrypt it first.");
            return;
        }
        if (!name.isEmpty()) {
            File file = new File(folderPath + File.separator + name + ".txt");
            try (Scanner scanner = new Scanner(file)) {
                int i = 1;
                while (scanner.hasNextLine()) {
                    String content = scanner.nextLine();
                    System.out.println(i + ". " + content);
                    i++;
                }
            } catch (FileNotFoundException e) {
                TerminalUI.Error("An error occurred while reading the file: ", e.getMessage());
            }
        }
    }

    public static void DeleteFile(String name) {
        File file = new File(folderPath + File.separator + name + ".txt");
        File encFile = new File(folderPath + File.separator + name + ".txt.enc");
        if (encFile.exists()) {
            file = encFile;
        }

        if (file.delete()) {
            TerminalUI.success("The file was successfully deleted: ", file.getName());
        } else {
            TerminalUI.Error("This file does not exist");
        }
    }

    public static void DeleteLine(String fileName, int lineNumber) {
        if (isEncrypted(fileName)) {
            TerminalUI.Error("Cannot modify an encrypted file. Please decrypt it first.");
            return;
        }
        Path filePath = Paths.get(folderPath + File.separator + fileName + ".txt");
        try {
            ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(filePath);

            if (lineNumber > 0 && lineNumber <= lines.size()) {
                lines.remove(lineNumber - 1);
                Files.write(filePath, lines);
                TerminalUI.success("Line ", lineNumber, " has been deleted from the file: ", fileName);
            } else {
                TerminalUI.Error("Invalid line number.");
            }
        } catch (IOException e) {
            TerminalUI.Error("An error occurred while deleting the line: " + e.getMessage());
        }
    }

    public static void UpdateLine(String fileName, int lineNumber, String newContent) {
        if (isEncrypted(fileName)) {
            TerminalUI.Error("Cannot modify an encrypted file. Please decrypt it first.");
            return;
        }
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
        if (isEncrypted(name)) {
            TerminalUI.Error("Cannot modify an encrypted file. Please decrypt it first.");
            return;
        }
        try (FileWriter Writer = new FileWriter(folderPath + File.separator + name + ".txt", append)) {
            String formattedDate = DateTimeNow();
            Writer.write(content + " | " + formattedDate + System.lineSeparator());
        } catch (IOException e) {
            TerminalUI.Error("An error occurred while writing to the file: ", e.getMessage());
        }
    }

    public static void RenameFile(String oldName, String newName) {
        if (isEncrypted(oldName)) {
            TerminalUI.Error("Cannot modify an encrypted file. Please decrypt it first.");
            return;
        }
        Path oldPath = Paths.get(folderPath + File.separator + oldName + ".txt");
        Path newPath = Paths.get(folderPath + File.separator + newName + ".txt");

        try {
            Files.move(oldPath, newPath);
            TerminalUI.success("File successfully renamed.");
        } catch (IOException e) {
            TerminalUI.Error("Failed to rename file: ", e.getMessage());
        }
    }

    public static void encryptFile(String fileName, String password) {
        Path oldPath = Paths.get(folderPath + File.separator + fileName + ".txt");
        Path newPath = Paths.get(folderPath + File.separator + fileName + ".txt.enc");

        String oldPathString = oldPath.toString();
        String newPathString = newPath.toString();

        try {
            if (Encrypt.encryptFile(password, oldPathString, newPathString)) {
                Files.delete(oldPath);
            }

        } catch (IOException e) {
            TerminalUI.Error("Error: ", e.getMessage());
        }
    }

    public static void decryptFile(String fileName, String password) {
        System.out.println(fileName);
        Path oldPath = Paths.get(folderPath + File.separator + fileName + ".txt.enc");
        Path newPath = Paths.get(folderPath + File.separator + fileName + ".txt");

        String oldPathString = oldPath.toString();
        String newPathString = newPath.toString();

        try {
            if (Encrypt.decryptFile(password, oldPathString, newPathString)) {
                Files.delete(oldPath);
            } else {
                Files.delete(newPath);
            }
        } catch (IOException e) {
            TerminalUI.Error("Error: ", e.getMessage());
        }
    }

    public static String DateTimeNow() {
        LocalDateTime DateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.format(DateTime);
    }

    private static boolean isEncrypted(String fileName) {
        Path file = Paths.get(folderPath + File.separator + fileName + ".txt.enc");
        return Files.exists(file) && file.toString().endsWith(".txt.enc");
    }
}