package main.java.gui;

import javafx.fxml.FXML;
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

    public FileStatsItem() {
        // TODO Auto-generated constructor stub
    }

}
