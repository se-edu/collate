package main.java.gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public class FileStatsItem extends BorderPane implements Comparable<FileStatsItem>{
    @FXML
    private HBox card;
    
    @FXML
    private Text filename;
    
    @FXML
    private Text linesOfCode;
    
    @FXML
    private Text percentage;
    
    @FXML
    private Shape circle;
    
    private static final String FILE_STATS_ITEM_FXML = "/main/resources/layouts/FileStatsItem.fxml";
    private static final String BASE_COLOUR_0 = "#ff4081";
    private static final String BASE_COLOUR_20 = "#ff5722";
    private static final String BASE_COLOUR_40 = "#ffa726";
    private static final String BASE_COLOUR_60 = "#00c853";
    private static final String BASE_COLOUR_80 = "#2196f3";
    
    private double percentageValue;

    public FileStatsItem(String filename, int linesOfCode, double percentage) {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FILE_STATS_ITEM_FXML));
        loader.setController(this);
        loader.setRoot(this);

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        this.percentageValue = percentage;
        
        this.filename.setText(truncatedName(filename));
        this.linesOfCode.setText(linesOfCode + " lines");
        this.percentage.setText(String.format("%.0f%%", percentage));
        this.card.setStyle("-fx-background-color: " + generateColour(percentage));
    }

    private String generateColour(double percentage) {
        if (percentage >= 80) {
            return BASE_COLOUR_80;
        } else if (percentage >= 60) {
            return BASE_COLOUR_60;
        } else if (percentage >= 40) {
            return BASE_COLOUR_40;
        } else if (percentage >= 20) {
            return BASE_COLOUR_20;
        } else {
            return BASE_COLOUR_0;
        }
    }

    private String truncatedName(String filename) {
        if (filename.length() >= 40) {
            return "..." + filename.substring(filename.length() - 37);
        }
        return filename;
    }
    
    public double getPercentageValue() {
        return percentageValue;
    }

    @Override
    public int compareTo(FileStatsItem o) {
        return (int) Math.round(o.getPercentageValue() - percentageValue);
    }
}
