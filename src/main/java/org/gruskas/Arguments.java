package org.gruskas;

public class Arguments {

    public static void checkArguments(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            switch (arg) {
                case "-h":
                case "--help":
                    helpArgument();
                    return;
                case "-f":
                case "-o":
                case "--file":
                case "--open":
                    fileArgument(i, args);
                    i += 1;
                    break;
                case "-d":
                case "--delate":
                    deleteArgument(i, args);
                    i += 1;
                    break;
                case "-ul":
                case "--update-line":
                    updateLineArgument(i, args);
                    i += 3;
                    break;
                case "-a":
                case "--append":
                    appendArgument(i, args);
                    i += 2;
                    break;
                case "-b":
                case "--banner":
                    TerminalUI.printBanner();
                    break;
                case "-e":
                case "--encrypt":
                    encryptArgument(i, args);
                    i += 3;
                    break;
                case "-de":
                case "--decrypt":
                    decryptArgument(i, args);
                    i += 3;
                    break;
                case "-em":
                case "--editmode":
                    editModeArgumnet(i, args);
                    i += 1;
                    break;
                default:
                    TerminalUI.Error("Unknown argument: " + arg + "\nFor help, use --help");
                    return;
            }
        }
    }

    private static void editModeArgumnet(int i, String[] args) {
        if (i + 1 < args.length) {
            EditMode.running = true;
            EditMode.editMode(args[++i]);
        } else {
            TerminalUI.Error("Missing arguments for editmode. Usage: --editmode <file>");
        }
    }

    private static void fileArgument(int i, String[] args) {
        if (i + 1 < args.length) {
            ArgumentsEditor.ReadFile(args[++i]);
        } else {
            TerminalUI.Error("Missing file name after argument.");
        }
    }

    private static void deleteArgument(int i, String[] args) {
        if (i + 1 < args.length) {
            ArgumentsEditor.DeleteFile(args[++i]);
        } else {
            TerminalUI.Error("Missing file name after argument.");
        }
    }

    private static void updateLineArgument(int i, String[] args) {
        if (i + 3 < args.length) {
            String path = args[++i];
            int line = Integer.parseInt(args[++i]);
            String newContent = args[++i];
            ArgumentsEditor.UpdateLine(path, line, newContent);
        } else {
            TerminalUI.Error("Missing arguments for update-line. Usage: --update-line <file> <line> <newContent>");
        }
    }

    private static void appendArgument(int i, String[] args) {
        if (i + 2 < args.length) {
            String path = args[++i];
            String newContent = args[++i];
            ArgumentsEditor.WriteToFile(path, newContent, true);
        } else {
            TerminalUI.Error("Missing arguments for append. Usage: --append <file> <content>");
        }
    }

    private static void encryptArgument(int i, String[] args) {
        if (i + 3 < args.length) {
            String password = args[++i];
            String inputFile = args[++i];
            String outputFile = args[++i];
            Encrypt.encryptFile(password, inputFile, outputFile);
        } else {
            TerminalUI.Error("Missing arguments for encrypt. Usage: --encrypt <password> <input-file> <output-file>");
        }
    }

    private static void decryptArgument(int i, String[] args) {
        if (i + 3 < args.length) {
            String password = args[++i];
            String inputFile = args[++i];
            String outputFile = args[++i];
            Encrypt.decryptFile(password, inputFile, outputFile);
        } else {
            TerminalUI.Error("Missing arguments for decrypt. Usage: --decrypt <password> <input-file> <output-file>");
        }
    }

    private static void helpArgument() {
        System.out.println("""
                Usage: java -jar JavaViNote-1.0-SNAPSHOT.jar [options]
                Options:
                    -h, --help                 Show this help message.
                    -f, --file <filename>      Open the specified file.
                    -o, --open <filename>      Open the specified file (alias for -f).
                    -d, --delete <filename>    Delete the specified file.
                    -ul, --update-line <file> <line> <newContent>
                                             Update a specific line in the file with new content.
                    -a, --append <file> <content>
                                             Append content to the specified file.
                    -b, --banner               Show the program banner.
                    -e, --encrypt <password> <input-file> <output-file>
                                             Encrypt the specified file.
                    -de, --decrypt <password> <input-file> <output-file>
                                             Decrypt the specified file.
                """);
    }
}