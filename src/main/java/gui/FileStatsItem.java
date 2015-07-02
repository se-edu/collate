package main.java.gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public class FileStatsItem extends BorderPane{
    @FXML
    private Text filename;
    
    @FXML
    private Text linesOfCode;
    
    @FXML
    private Text percentage;
    
    @FXML
    private Shape circle;
    
    private static final String FILE_STATS_ITEM_FXML = "/main/resources/layouts/FileStatsItem.fxml";

    public FileStatsItem(String filename, int linesOfCode, double percentage) {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FILE_STATS_ITEM_FXML));
        loader.setController(this);
        loader.setRoot(this);

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        this.filename.setText(truncatedName(filename));
        this.linesOfCode.setText(linesOfCode + " lines");
        this.percentage.setText(String.format("%.0f%%", percentage));
    }

    private String truncatedName(String filename) {
        if (filename.length() >= 40) {
            return "..." + filename.substring(filename.length() - 37);
        }
        return filename;
    }
}
