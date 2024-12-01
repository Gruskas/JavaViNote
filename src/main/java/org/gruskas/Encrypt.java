package org.gruskas;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {
    public static SecretKeySpec createAESKey(String password) throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = sha.digest(password.getBytes());
        return new SecretKeySpec(key, "AES");
    }

    public static void encryptFile(String password, String inputFile, String outputFile) {
        try {
            inputFile = inputFile.replaceAll("^\"|\"$|\\\\", "/");
            outputFile = outputFile.replaceAll("^\"|\"$|\\\\", "/");
            File input = new File(inputFile);
            if (!input.exists()) {
                System.out.println("The input file does not exist.");
                System.out.println(input.getAbsolutePath());
                return;
            }

            SecretKeySpec key = createAESKey(password);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            try (FileInputStream fileInputStream = new FileInputStream(inputFile);
                 FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
                 CipherOutputStream cipherOutputStream = new CipherOutputStream(fileOutputStream, cipher)) {

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    cipherOutputStream.write(buffer, 0, bytesRead);
                }

                System.out.println("File encrypted successfully!");
            }

        } catch (Exception e) {
            TerminalUI.Error("Error while encrypting file: " + e.getMessage());
        }
    }

    public static void decryptFile(String password, String inputFile, String outputFile) {
        try {
            inputFile = inputFile.replaceAll("^\"|\"$|\\\\", "/");
            outputFile = outputFile.replaceAll("^\"|\"$|\\\\", "/");
            File input = new File(inputFile);
            if (!input.exists()) {
                System.out.println("The input file does not exist.");
                System.out.println(input.getAbsolutePath());
                return;
            }

            SecretKeySpec key = createAESKey(password);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);

            try (FileInputStream fileInputStream = new FileInputStream(inputFile);
                 FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
                 CipherInputStream cipherInputStream = new CipherInputStream(fileInputStream, cipher)) {

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = cipherInputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }

                TerminalUI.success("File decrypted successfully");
            }

        } catch (Exception e) {
            TerminalUI.Error("Invalid Password");
            File output = new File(outputFile);
            if (output.exists()) {
                output.delete();
            }
        }
    }
}
