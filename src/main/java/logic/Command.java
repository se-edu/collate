package main.java.logic;

public class Command {
    
    public static enum Type {
        COLLATE, INVALID
    }
    
    private static final String STRING_ONE_SPACE = " ";
    private static final int POSITION_PARAM_COMMAND = 0;
    private static final int POSITION_FIRST_PARAM_ARGUMENT = 1;
    
    private static final String USER_COMMAND_COLLATE = "collate";
    
    private Type commandType;
    private String arguments;
    
    public Command(String userInput) {
        String[] parameters = splitUserInput(userInput);
        
        String userCommand = getUserCommand(parameters);
        commandType = determineCommandType(userCommand);
        
        arguments = getUserArguments(parameters);
    }
    
    // ================================================================
    // Public getters
    // ================================================================
    
    public Type getCommandType() {
        return commandType;
    }

    public String getArguments() {
        return arguments;
    }
    
    
    // ================================================================
    // Private methods
    // ================================================================
    
    private Type determineCommandType(String userCommand) {
        switch (userCommand.toLowerCase()) {
            case USER_COMMAND_COLLATE :
                return Type.COLLATE;
            default :
                return Type.INVALID;
        }
    }

    private String getUserArguments(String[] parameters) {
        StringBuilder builder = new StringBuilder();
        for (int i = POSITION_FIRST_PARAM_ARGUMENT; i < parameters.length; i++) {
            builder.append(parameters[i]);
            builder.append(STRING_ONE_SPACE);
        }
        return builder.toString().trim();
    }

    private String getUserCommand(String[] parameters) {
        return parameters[POSITION_PARAM_COMMAND];
    }

    private String[] splitUserInput(String input) {
        return input.trim().split(STRING_ONE_SPACE);
    }

}
