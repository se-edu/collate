package main.java.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.input.KeyCode;
import main.java.gui.CommandBarController;
import main.java.gui.OverviewLayoutController;
import main.java.logic.data.Author;
import main.java.logic.data.CodeSnippet;
import main.java.logic.parser.CommandParser;
import main.java.storage.CollatedFilesStorage;

public class Logic {

    private Logger logger;
    private CommandParser commandParser;
    private CollatedFilesStorage collatedFilesStorage;
    private HashMap<String, Author> authors;
    private String rootDirectory;

    private static final String LOG_TAG = "Logic";
    private static final int INITIAL_NUM_CONTRIBUTORS = 5;
    private static final String JAVA_AUTHOR_TAG = "@author";

    public Logic() {
        logger = Logger.getLogger(LOG_TAG);
        commandParser = new CommandParser();
        collatedFilesStorage = new CollatedFilesStorage();
        authors = new HashMap<String, Author>(INITIAL_NUM_CONTRIBUTORS);
    }

    public void handleKeyPress(CommandBarController commandBar,
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

    private void handleEnterPress(String userInput) {
        Command command = commandParser.parse(userInput);
        executeCommand(command);

        updateOverviewDisplay();
    }

    private void executeCommand(Command command) {
        switch (command.getCommandType()) {
            case COLLATE :
                logger.log(Level.INFO, "Collate command detected");
                handleCollate(command);
                break;
            case INVALID :
            default :
                break;
        }
    }
    
    private void updateOverviewDisplay() {
        ArrayList<String> data = new ArrayList<String>();
        int totalLines = 0;
        for (Author author : authors.values()) {
            totalLines += author.getTotalLines();
        }
        for (Author author : authors.values()) {
            int numLines = author.getTotalLines();
            double percentage = (double) numLines / totalLines * 100;
            data.add(author.getName() + ", total lines: " + numLines +
                     ", percentage: " + String.format("%.2f", percentage) + "%");
        }
        OverviewLayoutController.updateOverviewDisplay(data, true);
    }


    // ================================================================
    // Collate methods
    // ================================================================

    private void handleCollate(Command command) {
        rootDirectory = command.getDirectory();
        boolean willScanCurrentDirOnly = command.willScanCurrentDirOnly();

        if (rootDirectory != null) {
            authors = new HashMap<String, Author>(INITIAL_NUM_CONTRIBUTORS);
            traverseDirectory(new File(rootDirectory), willScanCurrentDirOnly);
            saveCollatedFiles();
        }
    }

    private void traverseDirectory(File folder, boolean willScanCurrentDirOnly) {
        if (folder.exists()) {
            if (folder.isFile()) {
                collateFile(folder, getFileExtension(folder));
            } else if (folder.isDirectory()) {
                for (File file : folder.listFiles()) {
                    if (!willScanCurrentDirOnly && file.isDirectory()) {
                        traverseDirectory(file, willScanCurrentDirOnly);
                    } else if (file.isFile()) {
                        logger.log(Level.INFO, "Found file: " + file);
                        collateFile(file, getFileExtension(file));
                    }
                }
            }
        }
    }

    private void saveCollatedFiles() {
        for (Author author : authors.values()) {
            ArrayList<String> collatedLines = new ArrayList<String>();
            collatedLines.add("# " + author.getName());
            for (CodeSnippet codeSnippet : author.getCodeSnippets()) {
                collatedLines.add(codeSnippet.toString());
            }
            collatedFilesStorage.addCollatedFile(author.getName(),
                                                 collatedLines);
        }
    }


    private void collateFile(File file, String extension) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            Author currentAuthor = null;
            CodeSnippet currentSnippet = null;

            while (line != null) {

                if (line.contains(JAVA_AUTHOR_TAG)) {
                    String authorName = findAuthorName(line, JAVA_AUTHOR_TAG);
                    logger.log(Level.INFO, "Found author: " + authorName);

                    if (!authors.containsKey(authorName)) {
                        currentAuthor = new Author(authorName);
                        authors.put(authorName, currentAuthor);
                        logger.log(Level.INFO, "New author created");
                    } else {
                        currentAuthor = authors.get(authorName);
                    }

                    currentSnippet = new CodeSnippet(currentAuthor,
                                                     getRelativePath(file.getPath()),
                                                     extension);
                } else if (currentSnippet != null) {
                    currentSnippet.addLine(line);
                }

                line = reader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getRelativePath(String path) {
        if (path.equals(rootDirectory)) {
            return path.substring(path.lastIndexOf("\\") + 1);
        }
        return path.replace(rootDirectory, "").substring(1);
    }

    private String findAuthorName(String line, String authorTag) {
        String[] split = line.split(authorTag);
        return split[1].trim();
    }

    private String getFileExtension(File file) {
        int idxLastPeriod = file.getName().lastIndexOf('.');
        if (idxLastPeriod != -1) {
            return file.getName().substring(idxLastPeriod + 1);
        }
        return "";
    }
}
