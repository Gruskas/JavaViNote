package org.gruskas;

public class Arguments {

    public static void checkArguments(String[] args) {
        for (String arg : args) {
            switch (arg) {
                case "-h":
                case "--help":
                    printHelp();
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
        System.out.println("  -h, --help       Show this help message.");
    }
}
