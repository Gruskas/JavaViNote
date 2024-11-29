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
                default:
                    TerminalUI.Error("Unknown argument: " + arg);
                    break;
            }
        }
    }

    private static void printHelp() {
        System.out.println("Usage: java -jar JavaViNote-1.0-SNAPSHOT.jar [options]");
        System.out.println("Options:");
        System.out.println("  -h, --help                 Show this help message.");
        System.out.println("  -f, --file <filename>      Open the specified file.");
        System.out.println("  -o, --open <filename>      Open the specified file (alias for -f).");
        System.out.println("  -d, --delete <filename>    Delete the specified file.");
        System.out.println("  -ul, --update-line <file> <line> <newContent>");
        System.out.println("                             Update a specific line in the file with new content.");
        System.out.println("  -a, --append <file> <content>");
        System.out.println("                             Append content to the specified file.");
    }
}
