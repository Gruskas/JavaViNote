package org.gruskas;

public class Arguments {

    public static void checkArguments(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            switch (arg) {
                case "-h":
                case "--help":
                    printHelp();
                    return;
                case "-f":
                case "-o":
                case "--file":
                case "--open":
                    if (i + 1 < args.length) {
//                        System.out.println(args[i + 1]);
                        i++;
                        ArgumentsEditor.ReadFile(args[i]);

                    } else {
                        TerminalUI.Error("Missing file name after " + arg);
                    }
                    break;
                case "-d":
                case "--delate":
                    if (i + 1 < args.length) {
//                        System.out.println(args[i + 1]);
                        i++;
                        ArgumentsEditor.DeleteFile(args[i]);

                    } else {
                        TerminalUI.Error("Missing file name after " + arg);
                    }
                    break;
                case "-ul":
                case "--update-line":
                    if (i + 1 < args.length) {
//                        System.out.println(args[i + 1]);
                        i++;
                        String path = args[i];
                        i++;
                        int line = Integer.parseInt(args[i]);
                        System.out.println(line);
                        i++;
                        String newContent = args[i];
                        i++;
                        ArgumentsEditor.UpdateLine(path, line, newContent);
                    } else {
                        TerminalUI.Error("Missing file name after " + arg);
                    }
                    break;
                case "-a":
                case "--append":
                    if (i + 1 < args.length) {
                        i++;
                        String path = args[i];
                        i++;
                        String newContent = args[i];
                        i++;
                        ArgumentsEditor.WriteToFile(path, newContent, true);
                    } else {
                        TerminalUI.Error("Missing file name after " + arg);
                    }
                    break;
                case "-b":
                case "--banner":
                    TerminalUI.printBanner();
                    return;
                case "-e":
                case "--encrypt":
                    if (i + 3 < args.length) {
                        i++;
                        String password = args[i];
                        i++;
                        String input = args[i];
                        i++;
                        String output = args[i];
                        i++;
                        Encrypt.encryptFile(password, input, output);
                    } else {
                        TerminalUI.Error(arg);
                    }
                    break;
                case "-de":
                case "--decrypt":
                    if (i + 3 < args.length) {
                        i++;
                        String password2 = args[i];
                        i++;
                        String input2 = args[i];
                        i++;
                        String output2 = args[i];
                        i++;
                        Encrypt.decryptFile(password2, input2, output2);
                    } else {
                        TerminalUI.Error(arg);
                    }
                    break;
                default:
                    TerminalUI.Error("Unknown argument: " + arg + "\nFor help, use --help");
                    break;
            }
        }
    }

    private static void printHelp() {
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