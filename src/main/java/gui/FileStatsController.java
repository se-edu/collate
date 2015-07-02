package main.java.gui;

import java.io.IOException;

import main.java.backend.Author;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;

public class FileStatsController extends ListView<FileStatsItem> {

    @FXML
    private ListView<FileStatsItem> fileStats;
    
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
        // TODO Auto-generated method stub
        
    }

}
