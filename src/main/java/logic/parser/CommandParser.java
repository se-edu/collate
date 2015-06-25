package main.java.logic.parser;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandParser {
    
    private static final String DIRECTORY_KEYWORD = "from";
    private static final String STRING_ONE_SPACE = " ";
    
    public CommandParser() {
    }

    public String getDirectory(String arguments) {
        if (arguments != null) {
            ArrayList<String> argArray = splitArguments(arguments);
            if (argArray.contains(DIRECTORY_KEYWORD)) {
                int directoryIndex = argArray.indexOf(DIRECTORY_KEYWORD) + 1;
                return argArray.get(directoryIndex);
            }
        }
        return null;
    }
    
    private ArrayList<String> splitArguments(String arguments) {
        String[] strArray = arguments.trim().split(STRING_ONE_SPACE);
        return new ArrayList<String>(Arrays.asList(strArray));
    }

}
