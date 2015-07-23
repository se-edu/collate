package main.java.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import main.java.backend.SourceFile;

public class FileStatsController extends VBox {

    @FXML
    private ListView<FileStatsItem> fileStats;
    
    @FXML
    private Label title;

    private ArrayList<FileStatsItem> items;

    private static final String FILE_STATS_FXML = "/main/resources/layouts/FileStats.fxml";

    public FileStatsController(String authorName, HashMap<SourceFile, Integer> statistics) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FILE_STATS_FXML));
        loader.setController(this);
        loader.setRoot(this);

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        title.setText(authorName);
        initStats(statistics);
    }

    private void initStats(HashMap<SourceFile, Integer> statistics) {
        items = new ArrayList<FileStatsItem>();
        
        for (SourceFile sourceFile : statistics.keySet()) {
            addFileStatsItem(sourceFile, statistics.get(sourceFile));  
        }
        
        Collections.sort(items);
        fileStats.setItems(FXCollections.observableList(items));
    }

    private void addFileStatsItem(SourceFile currentFile, int currentNumLines) {
        items.add(new FileStatsItem(currentFile.getRelativeFilePath(),
                                    currentNumLines,
                                    (double) currentNumLines /
                                            currentFile.getNumLines() * 100));
    }

}
