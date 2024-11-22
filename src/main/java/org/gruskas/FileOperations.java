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

    public static void createDirectory() throws IOException {
        String folderPath = "files";
        Path path = Paths.get(folderPath);

        if (!Files.exists(path)) {
            Files.createDirectories(path);
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
                    .forEach(x -> {
                        txtFiles.add(x);
                    });
        }
        if (txtFiles.isEmpty()) {
            txtFiles.add(Path.of("There are no files"));
        }
        return txtFiles;
    }

    public static void CreateFile(String name) throws IOException {
        try {
            File file = new File(folderPath + File.separator + name + ".txt");
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("Error while creating file: " + e.getMessage());
        }
    }

    public static void ReadFile(String name) {
        try {
            File file = new File(folderPath + File.separator + name + ".txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                System.out.println(data);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void DeleteFile(String name) {
        File file = new File(folderPath + File.separator + name + ".txt");
//        System.out.println(file.getAbsolutePath());
        if (file.delete()) {
            System.out.println("The file was successfully deleted: " + file.getName());
        } else {
            System.out.println("This file does not exist");
        }
    }

    public static void WriteToFile(String name, String content, Boolean append) {
        try {
            LocalDateTime DateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDate = formatter.format(DateTime);
            FileWriter Writer = new FileWriter(folderPath + File.separator + name + ".txt", append);

            Writer.write(content + " | " + formattedDate + System.lineSeparator() );
            Writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}