package main.java.backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Logic {

    private Logger logger;
    private CommandParser commandParser;
    private Storage storage;
    private HashMap<String, Author> authors;
    private String rootDirectory;
    
    private ObservableList<Author> obsList = FXCollections.observableArrayList();
    private ObservableList<Author> obsAuthor = FXCollections.observableArrayList();

    private static final String LOG_TAG = "Logic";
    private static final int INITIAL_NUM_CONTRIBUTORS = 5;
    private static final String AUTHOR_TAG = "@@author";

    public Logic() {
        logger = Logger.getLogger(LOG_TAG);
        commandParser = new CommandParser();
        storage = new Storage();
        authors = new HashMap<String, Author>(INITIAL_NUM_CONTRIBUTORS);
    }

    public Command.Type handleEnterPress(String userInput) {
        Command command = commandParser.parse(userInput);
        return executeCommand(command);
    }

    
    // ================================================================
    // Private methods
    // ================================================================
    private Command.Type executeCommand(Command command) {
        switch (command.getCommandType()) {
            case COLLATE :
                logger.log(Level.INFO, "Collate command detected");
                handleCollate(command);
                return Command.Type.COLLATE;
            case VIEW :
                logger.log(Level.INFO, "View command detected");
                handleView(command);
                return Command.Type.VIEW;
            case SUMMARY:
                logger.log(Level.INFO, "Summary command detected");
                return Command.Type.SUMMARY;
            case INVALID :
            default :
                return Command.Type.INVALID;
        }
    }


    // ================================================================
    // Collate command methods
    // ================================================================
    private void handleCollate(Command command) {
        rootDirectory = command.getDirectory();
        boolean willScanCurrentDirOnly = command.willScanCurrentDirOnly();
        ArrayList<String> fileTypes = command.getFileTypes();

        if (rootDirectory != null) {
            reInitVar();
            
            traverseDirectory(new File(rootDirectory),
                              willScanCurrentDirOnly,
                              fileTypes);
            saveCollatedFiles();
        }
    }

    private void reInitVar() {
        obsList = FXCollections.observableArrayList();
        authors = new HashMap<String, Author>(INITIAL_NUM_CONTRIBUTORS);
        CodeSnippet.resetTotalLines();
    }

    private void traverseDirectory(File folder,
                                   boolean willScanCurrentDirOnly,
                                   ArrayList<String> fileTypes) {
        if (folder.exists()) {
            if (folder.isFile()) {
                scanFile(folder, getFileExtension(folder));
            } else if (folder.isDirectory()) {
                for (File file : folder.listFiles()) {
                    if (!willScanCurrentDirOnly && file.isDirectory()) {
                        traverseDirectory(file,
                                          willScanCurrentDirOnly,
                                          fileTypes);
                    } else if (file.isFile() &&
                               hasIncludedFileType(fileTypes,
                                                   getFileExtension(file))) {
                        logger.log(Level.INFO, "Found file: " + file);
                        scanFile(file, getFileExtension(file));
                    }
                }
            }
        }
    }

    private boolean hasIncludedFileType(ArrayList<String> fileTypes,
                                        String fileExtension) {
        if (fileTypes.isEmpty() || fileTypes.contains(fileExtension)) {
            return true;
        } else {
            return false;
        }
    }

    private void saveCollatedFiles() {
        for (Author author : authors.values()) {
            ArrayList<String> collatedLines = new ArrayList<String>();
            collatedLines.add("# " + author.getName());
            for (CodeSnippet codeSnippet : author.getCodeSnippets()) {
                collatedLines.add(codeSnippet.toString());
            }
            storage.addCollatedFile(author.getName(), collatedLines);
        }
    }


    private void scanFile(File file, String extension) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            Author currentAuthor = null;
            CodeSnippet currentSnippet = null;
            boolean ignoreLine = false;
            SourceFile currentFile = new SourceFile(getRelativePath(file.getPath()));
            
            while (line != null) {
                currentFile.updateNumLines(1);
                
                if (line.contains(AUTHOR_TAG)) {
                    String authorName = findAuthorName(line, AUTHOR_TAG);
                    logger.log(Level.INFO, "Found author tag: " + authorName);
                    
                    if (authorName.isEmpty()) {
                        ignoreLine = true;
                        line = reader.readLine();
                        continue;
                    }

                    if (!authors.containsKey(authorName)) {
                        currentAuthor = new Author(authorName);
                        authors.put(authorName, currentAuthor);
                        obsList.add(currentAuthor);
                        logger.log(Level.INFO, "New author created");
                    } else {
                        currentAuthor = authors.get(authorName);
                    }

                    ignoreLine = false;
                    
                    currentSnippet = new CodeSnippet(currentAuthor,
                                                     currentFile,
                                                     extension);
                } else if (!ignoreLine && currentSnippet != null) {
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
        try {
            String[] split = line.split(authorTag);
            return split[1].replaceAll("[^ a-zA-Z0-9]+", "").trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            return "";
        }
    }

    private String getFileExtension(File file) {
        int idxLastPeriod = file.getName().lastIndexOf('.');
        if (idxLastPeriod != -1) {
            return file.getName().substring(idxLastPeriod + 1);
        }
        return "";
    }
    
    
    // ================================================================
    // View command methods
    // ================================================================
    private void handleView(Command command) {
        String inputName = command.getAuthorName();
        for (Author author : authors.values()) {
            if (author.getName().toLowerCase().equals(inputName.toLowerCase())) {
                logger.log(Level.INFO, "Found target author: " + author.getName());
                obsAuthor.clear();
                obsAuthor.add(author);
                break;
            }
        }
    }

    public ObservableList<Author> getOverviewData() {
        return obsList;
    }
    
    public Author getTargetAuthor() {
        try {
            return obsAuthor.get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}
