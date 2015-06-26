package main.java.logic.parser;

import java.util.ArrayList;
import java.util.Arrays;

import main.java.logic.Command;

public class CommandParser {
    private static final int POSITION_PARAM_COMMAND = 0;
    private static final int POSITION_FIRST_PARAM_ARGUMENT = 1;
    private static final String STRING_ONE_SPACE = " ";

    private static final String USER_COMMAND_COLLATE = "collate";
    
    private static final String SCAN_CURRENT_DIR_ONLY_KEYWORD = "only";
    private static final String DIRECTORY_KEYWORD = "from";


    public CommandParser() {
    }

    public Command parse(String userInput) {
        Command command;
        ArrayList<String> parameters = splitString(userInput, STRING_ONE_SPACE);
        String userCommand = getUserCommand(parameters);
        ArrayList<String> arguments = getUserArguments(parameters);

        switch (userCommand.toLowerCase()) {
            case USER_COMMAND_COLLATE :
                command = initCollateCommand(arguments);
                break;
            default :
                command = initInvalidCommand();
        }

        return command;
    }


    // ================================================================
    // Private methods
    // ================================================================

    private ArrayList<String> splitString(String arguments, String delimiter) {
        String[] strArray = arguments.trim().split(delimiter);
        return new ArrayList<String>(Arrays.asList(strArray));
    }

    private String getUserCommand(ArrayList<String> parameters) {
        return parameters.get(POSITION_PARAM_COMMAND);
    }

    private ArrayList<String> getUserArguments(ArrayList<String> parameters) {
        return new ArrayList<String>(parameters.subList(POSITION_FIRST_PARAM_ARGUMENT,
                                                        parameters.size()));
    }


    // ================================================================
    // Create collate command methods
    // ================================================================

    private Command initCollateCommand(ArrayList<String> arguments) {
        Command command = new Command();
        command.setType(Command.Type.COLLATE);
        command.setDirectory(findDirectory(arguments));
        command.setScanCurrentDirOnly(hasScanCurrentDirOnlyKeyword(arguments));
        return command;
    }


    private boolean hasScanCurrentDirOnlyKeyword(ArrayList<String> arguments) {
        return arguments.contains(SCAN_CURRENT_DIR_ONLY_KEYWORD);
    }

    private String findDirectory(ArrayList<String> arguments) {
        if (arguments.contains(DIRECTORY_KEYWORD)) {
            int directoryIndex = arguments.indexOf(DIRECTORY_KEYWORD) + 1;
            try {
                return arguments.get(directoryIndex);
            } catch (IndexOutOfBoundsException e) {
                return "";
            }
        }
        return "";
    }


    // ================================================================
    // Create invalid command method
    // ================================================================

    private Command initInvalidCommand() {
        Command command = new Command();
        command.setType(Command.Type.INVALID);
        return command;
    }
}
