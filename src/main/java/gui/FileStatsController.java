package main.java.gui;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import main.java.backend.Author;
import main.java.backend.CodeSnippet;
import main.java.backend.SourceFile;

public class FileStatsController extends ListView<FileStatsItem> {

    @FXML
    private ListView<FileStatsItem> fileStats;

    private ArrayList<FileStatsItem> items;

    private static final String FILE_STATS_FXML = "/main/resources/layouts/FileStats.fxml";

    public FileStatsController(Author author) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FILE_STATS_FXML));
        loader.setController(this);
        loader.setRoot(this);

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (author != null) {
            initStats(author);
        }
    }

    private void initStats(Author author) {
        items = new ArrayList<FileStatsItem>();
        SourceFile currentFile = null;
        int currentNumLines = 0;

        for (CodeSnippet snippet : author.getCodeSnippets()) {

            if (currentFile == null) {
                currentFile = snippet.getFile();
                currentNumLines += snippet.getNumLines();
            } else if (currentFile.equals(snippet.getFile())) {
                currentNumLines += snippet.getNumLines();
            } else if (!currentFile.equals(snippet.getFile())) {
                addFileStatsItem(currentFile, currentNumLines);
                currentFile = snippet.getFile();
                currentNumLines = snippet.getNumLines();
            }
        }

        addFileStatsItem(currentFile, currentNumLines);

        fileStats.setItems(FXCollections.observableList(items));
    }

    private void addFileStatsItem(SourceFile currentFile, int currentNumLines) {
        items.add(new FileStatsItem(currentFile.getFileLocation(),
                                    currentNumLines,
                                    (double) currentNumLines /
                                            currentFile.getNumLines() * 100));
    }

}
