package main.java.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.input.KeyCode;
import main.java.gui.CommandBarController;
import main.java.gui.OverviewLayoutController;
import main.java.logic.data.Author;
import main.java.logic.data.CodeSnippet;
import main.java.logic.parser.CommandParser;

public class Logic {

    private static Logger logger;
    private static CommandParser commandParser;
    private static HashMap<String, Author> authors;

    private static final String LOG_TAG = "Logic";
    private static final int INITIAL_NUM_CONTRIBUTORS = 5;
    private static final String JAVA_AUTHOR_TAG = "@author";

    static {
        logger = Logger.getLogger(LOG_TAG);
        commandParser = new CommandParser();
        authors = new HashMap<String, Author>(INITIAL_NUM_CONTRIBUTORS);
    }

    public static void handleKeyPress(CommandBarController commandBar,
                                      KeyCode key,
                                      String userInput) {
        if (key == KeyCode.ENTER) {
            handleEnterPress(userInput);
            commandBar.clear();
        }
    }


    // ================================================================
    // Private methods
    // ================================================================

    private static void handleEnterPress(String userInput) {
        Command command = new Command(userInput);
        executeCommand(command);
        for (Author author : authors.values()) {
            for (CodeSnippet codeSnippet : author.getCodeSnippets()) {
                OverviewLayoutController.updateOverviewDisplay(codeSnippet.toString());
            }
        }
    }

    private static void executeCommand(Command command) {
        switch (command.getCommandType()) {
            case COLLATE :
                handleCollate(command.getArguments());
                break;
            case INVALID :
            default :
                break;
        }
    }


    // ================================================================
    // Collate methods
    // ================================================================

    private static void handleCollate(String arguments) {
        String directory = commandParser.getDirectory(arguments);
        if (directory != null) {
            traverseDirectory(directory);
        }
    }

    private static void traverseDirectory(String directory) {
        File folder = new File(directory);
        traverseDirectory(folder);
    }

    private static void traverseDirectory(File folder) {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                traverseDirectory(file);
            } else if (getFileExtension(file).equals("java")) {
                logger.log(Level.INFO, "Found file: " + file);
                collateFile(file);
            }
        }
    }

    private static void collateFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            Author currentAuthor = null;
            CodeSnippet currentSnippet = null;

            while (line != null) {

                if (line.contains(JAVA_AUTHOR_TAG)) {
                    String authorName = findAuthorName(line);
                    logger.log(Level.INFO, "Found author: " + authorName);

                    if (!authors.containsKey(authorName)) {
                        currentAuthor = new Author(authorName);
                        authors.put(authorName, currentAuthor);
                    } else {
                        currentAuthor = authors.get(authorName);
                    }

                    currentSnippet = new CodeSnippet(currentAuthor,
                                                     file.getPath(),
                                                     "java");
                } else if (currentSnippet != null) {
                    currentSnippet.addLine(line);
                }

                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String findAuthorName(String line) {
        String[] split = line.split(JAVA_AUTHOR_TAG);
        return split[1].trim();
    }

    private static String getFileExtension(File file) {
        int idxLastPeriod = file.getName().lastIndexOf('.');
        if (idxLastPeriod != -1) {
            return file.getName().substring(idxLastPeriod + 1);
        }
        return null;
    }
}
