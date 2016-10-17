package main.java.backend;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;

/**
 * CommandParser parses user's input to create Command objects that have the
 * appropriate fields initialised. For example, the "view" command requires the
 * authorName field to be initialised.
 * 
 * @author Sebastian Quek
 *
 */
public class CommandParser {
    
    private static final int POSITION_PARAM_COMMAND = 0;
    private static final int POSITION_FIRST_PARAM_ARGUMENT = 1;

    private static final String REGEX_WHITESPACES = "[\\s,]+";
    
    private static final String STRING_DOUBLE_INVERTED_COMMA = "\"";
    private static final String STRING_EMPTY = "";
    private static final String STRING_ONE_SPACE = " ";

    private static final String USER_COMMAND_COLLATE = "collate";
    private static final String USER_COMMAND_VIEW = "view";
    private static final String USER_COMMAND_SUMMARY = "summary";
    private static final String USER_COMMAND_EXIT = "exit";

    private static final String[] KEYWORDS = {"from", "to", "only", "include"};
    private static final String KEYWORD_FROM_DIRECTORY = KEYWORDS[0];
    private static final String KEYWORD_TO_DIRECTORY = KEYWORDS[1];
    private static final String KEYWORD_SCAN_CURRENT_DIR_ONLY = KEYWORDS[2];
    private static final String KEYWORD_INCLUDE = KEYWORDS[3];

    public CommandParser() {
    }

    public Command parse(String userInput) {
        Command command;
        ArrayList<String> parameters = splitString(userInput);
        String userCommand = getUserCommand(parameters);
        ArrayList<String> arguments = getUserArguments(parameters);

        switch (userCommand.toLowerCase()) {

            case USER_COMMAND_COLLATE :
                command = initCollateCommand(arguments);
                break;

            case USER_COMMAND_VIEW :
                command = initViewCommand(arguments);
                break;

            case USER_COMMAND_SUMMARY :
                command = initSummaryCommand();
                break;
                
            case USER_COMMAND_EXIT :
                command = initExitCommand();
                break;

            default :
                command = initInvalidCommand();
        }

        return command;
    }

    private ArrayList<String> splitString(String arguments) {
        String[] strArray = arguments.trim().split(REGEX_WHITESPACES);
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
        File fromDirectory = new File(findDirectory(arguments, KEYWORD_FROM_DIRECTORY));
        File toDirectory = new File(findDirectory(arguments, KEYWORD_TO_DIRECTORY));
        ArrayList<String> fileTypes = findIncludedFileTypes(arguments);

        if (isValidCollateCommand(arguments, fromDirectory, fileTypes)) {
            Command command = new Command(Command.Type.COLLATE);
            command.setReadDirectory(fromDirectory.getAbsolutePath());
            command.setSaveDirectory(toDirectory.getAbsolutePath());
            command.setScanCurrentDirOnly(hasScanCurrentDirOnlyKeyword(arguments));
            command.setFileTypes(fileTypes);
            return command;
        } else {
            return initInvalidCommand();
        }
    }

    private String findDirectory(ArrayList<String> arguments, String directoryIdentifier) {
        if (arguments.contains(directoryIdentifier)) {
            int directoryIndex = arguments.indexOf(directoryIdentifier) + 1;

            try {
                return getFullDirectory(arguments, directoryIndex);
            } catch (IndexOutOfBoundsException e) {
                // No directory was specified
                return STRING_EMPTY;
            }
        } else {
            return STRING_EMPTY;
        }
    }
    
    /**
     * A valid collate command requires the following:
     * 
     * 1) The input directory exists
     * 2) If the "include" keyword is specified, it must be followed by words
     * that indicate file types
     * 
     * @param arguments
     * @param directory
     * @param fileTypes
     * 
     */
    private boolean isValidCollateCommand(ArrayList<String> arguments,
                                          File directory,
                                          ArrayList<String> fileTypes) {
        return directory.exists() &&
               !(arguments.contains(KEYWORD_INCLUDE) && fileTypes.isEmpty());
    }

    /**
     * Finds the full directory as given in the user's input.
     * If the user inputs a directory with whitespaces, the user must surround
     * the directory with double inverted commas as this method will utilise
     * the presence of double inverted commas to get the full directory.
     * 
     * @param arguments
     * @param directoryIndex
     */
    private String getFullDirectory(ArrayList<String> arguments,
                                    int directoryIndex) throws IndexOutOfBoundsException {
        String directory = arguments.get(directoryIndex);
        if (directory.startsWith(STRING_DOUBLE_INVERTED_COMMA)) {
            if (!directory.endsWith(STRING_DOUBLE_INVERTED_COMMA)) {
                for (int i = directoryIndex + 1; i < arguments.size(); i++) {
                    String currentArgument = arguments.get(i);
                    directory += STRING_ONE_SPACE + currentArgument;
    
                    if (currentArgument.endsWith(STRING_DOUBLE_INVERTED_COMMA)) {
                        break;
                    }
                }
            }
            // Remove double inverted commas
            directory = directory.replace(STRING_DOUBLE_INVERTED_COMMA,
                                          STRING_EMPTY);
        }
        return directory;
    }

    private boolean hasScanCurrentDirOnlyKeyword(ArrayList<String> arguments) {
        return arguments.contains(KEYWORD_SCAN_CURRENT_DIR_ONLY);
    }

    private ArrayList<String> findIncludedFileTypes(ArrayList<String> arguments) {
        ArrayList<String> fileTypes = new ArrayList<String>();
        if (arguments.contains(KEYWORD_INCLUDE)) {
            int fileTypesIndex = arguments.indexOf(KEYWORD_INCLUDE) + 1;
            for (int i = fileTypesIndex; i < arguments.size(); i++) {
                String inputFileType = arguments.get(i).toLowerCase();
                if (isValidFileType(inputFileType)) {
                    fileTypes.add(inputFileType);
                } else {
                    break;
                }
            }
        }
        return fileTypes;
    }

    /**
     * A valid file type is one that is not a keyword.
     * 
     * @param fileType
     */
    private boolean isValidFileType(String fileType) {
        for (String keyword : KEYWORDS) {
            if (keyword.equals(fileType)) {
                return false;
            }
        }
        return true;
    }
    
    
    // ================================================================
    // Create view command methods
    // ================================================================
    
    private Command initViewCommand(ArrayList<String> arguments) {
        String authorName = findAuthorName(arguments);
        if (!authorName.isEmpty()) {
            Command command = new Command(Command.Type.VIEW);
            command.setAuthorName(authorName);
            return command;
        } else {
            return initInvalidCommand();
        }
    }

    private String findAuthorName(ArrayList<String> arguments) {
        StringJoiner joiner = new StringJoiner(STRING_ONE_SPACE);
        for (String word : arguments) {
            joiner.add(word);
        }
        return joiner.toString();
    }

    
    // ================================================================
    // Create summary command method
    // ================================================================
   
    private Command initSummaryCommand() {
        return new Command(Command.Type.SUMMARY);
    }
    
    
    // ================================================================
    // Create invalid command method
    // ================================================================

    private Command initInvalidCommand() {
        return new Command(Command.Type.INVALID);
    }
    
    
    // ================================================================
    // Create exit command method
    // ================================================================

    private Command initExitCommand() {
        return new Command(Command.Type.EXIT);
    }
}
