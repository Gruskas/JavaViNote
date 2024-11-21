package org.gruskas;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileOperations {
    public enum Mode {
        NORMAL,
        INSERT,
        COMMAND
    }

    public static void createDirectory() throws IOException {
        String folderPath = "./files";
        Path path = Paths.get(folderPath);

        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    public static ArrayList<Path> findTxtFiles() throws IOException {
        String folderPath = "./files";
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
}
