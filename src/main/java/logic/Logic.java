package main.java.logic;

import java.io.File;

import javafx.scene.input.KeyCode;
import main.java.gui.CommandBarController;
import main.java.gui.OverviewLayoutController;
import main.java.logic.parser.CommandParser;

public class Logic {
    private static CommandParser commandParser;
    private static final String JAVA_AUTHOR_TAG = "@contributor";

    public Logic() {
        commandParser = new CommandParser();
    }

    public static void handleKeyPress(CommandBarController commandBar,
                                      KeyCode key,
                                      String userInput) {
        if (key == KeyCode.ENTER) {
            handleEnterPress(userInput);
            commandBar.clear();
        }
    }

    private static void handleEnterPress(String userInput) {
        Command command = new Command(userInput);
        executeCommand(command);
        OverviewLayoutController.updateOverviewDisplay(command.getCommandType()
                                                              .toString());
    }

    private static void executeCommand(Command command) {
        switch (command.getCommandType()) {
            case COLLATE :
                handleCollate(command.getArguments());
                break;
            case INVALID :
                break;
        }
    }

    private static void handleCollate(String arguments) {
        String directory = commandParser.getDirectory(arguments);
        traverseDirectory(directory);
    }

    private static void traverseDirectory(String directory) {
        File folder = new File(directory);
        traverseDirectory(folder);
    }

    private static void traverseDirectory(File folder) {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                traverseDirectory(file);
            } else if (getFileExtension(file) == "java") {
                collateFile(file);
            }
        }
    }

    private static void collateFile(File file) {
        // TODO Auto-generated method stub
        
    }

    private static String getFileExtension(File file) {
        int idxLastPeriod = file.getName().lastIndexOf('.');
        if (idxLastPeriod != -1) {
            return file.getName().substring(idxLastPeriod + 1);
        }
        return "";
    }
}
