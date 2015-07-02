package main.java.backend;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandParser {

    private static final int POSITION_PARAM_COMMAND = 0;
    private static final int POSITION_FIRST_PARAM_ARGUMENT = 1;
    private static final String STRING_ONE_SPACE = " ";

    private static final String USER_COMMAND_COLLATE = "collate";
    private static final String USER_COMMAND_VIEW = "view";

    private static final String[] KEYWORDS = {"from", "only", "include"};
    private static final String KEYWORD_DIRECTORY = KEYWORDS[0];
    private static final String KEYWORD_SCAN_CURRENT_DIR_ONLY = KEYWORDS[1];
    private static final String KEYWORD_INCLUDE = KEYWORDS[2];

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
            case USER_COMMAND_VIEW :
                command = initViewCommand(arguments);
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
        Command command = new Command(Command.Type.COLLATE);
        command.setDirectory(findDirectory(arguments));
        command.setScanCurrentDirOnly(hasScanCurrentDirOnlyKeyword(arguments));
        command.setFileTypes(findIncludedFileTypes(arguments));
        return command;
    }

    private String findDirectory(ArrayList<String> arguments) {
        if (arguments.contains(KEYWORD_DIRECTORY)) {
            int directoryIndex = arguments.indexOf(KEYWORD_DIRECTORY) + 1;
            try {
                return arguments.get(directoryIndex);
            } catch (IndexOutOfBoundsException e) {
                return "";
            }
        }
        return "";
    }

    private boolean hasScanCurrentDirOnlyKeyword(ArrayList<String> arguments) {
        return arguments.contains(KEYWORD_SCAN_CURRENT_DIR_ONLY);
    }

    private ArrayList<String> findIncludedFileTypes(ArrayList<String> arguments) {
        ArrayList<String> fileTypes = new ArrayList<String>();
        if (arguments.contains(KEYWORD_INCLUDE)) {
            int fileTypesIndex = arguments.indexOf(KEYWORD_INCLUDE) + 1;
            for (int i = fileTypesIndex; i < arguments.size(); i++) {
                addToFileTypesIfValid(fileTypes, arguments.get(i));
            }
        }
        return fileTypes;
    }

    private void addToFileTypesIfValid(ArrayList<String> fileTypes,
                                       String inputFileType) {
        String sanitisedFileType = inputFileType.replaceAll("[^a-zA-Z0-9]+", "")
                                               .trim();
        for (String keyword : KEYWORDS) {
            if (keyword.equals(sanitisedFileType)) {
                return;
            } else {
                fileTypes.add(sanitisedFileType);
            }
        }
    }
    
    
    // ================================================================
    // Create view command methods
    // ================================================================
    
    private Command initViewCommand(ArrayList<String> arguments) {
        Command command = new Command(Command.Type.VIEW);
        command.setAuthorName(findAuthorName(arguments));
        return command;
    }

    private String findAuthorName(ArrayList<String> arguments) {
        StringBuilder builder = new StringBuilder();
        for (String word : arguments) {
            builder.append(word);
            builder.append(STRING_ONE_SPACE);
        }
        return builder.toString().trim();
    }

    // ================================================================
    // Create invalid command method
    // ================================================================

    private Command initInvalidCommand() {
        Command command = new Command(Command.Type.INVALID);
        return command;
    }
}
