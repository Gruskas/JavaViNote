package org.gruskas;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ClearTerminal.clear();
        FileOperations.createDirectory();
        TerminalUI.printBanner();
        TerminalUI.showFiles();
    }
}
