package main.java.gui;

import java.io.IOException;
import java.util.Collection;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import main.java.data.Author;

/**
 * This class controls the Summary view by loading the corresponding fxml file
 * and initialising the table's columns with the authors' properties.
 * 
 * @author Sebastian Quek
 *
 */
public class SummaryController extends StackPane {
    
    @FXML
    private TableView<Author> summaryTable;
    @FXML
    private TableColumn<Author, String> authorNameColumn;
    @FXML
    private TableColumn<Author, Integer> linesOfCodeColumn;
    @FXML
    private TableColumn<Author, Double> proportionColumn;

    private static final String OVERVIEW_LAYOUT_FXML = "/main/resources/layouts/Summary.fxml";

    public SummaryController(Collection<Author> summaryData) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(OVERVIEW_LAYOUT_FXML));
        loader.setController(this);
        loader.setRoot(this);

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        summaryTable.setItems(FXCollections.observableArrayList(summaryData));
        authorNameColumn.setCellValueFactory(new PropertyValueFactory<Author, String>("name"));
        linesOfCodeColumn.setCellValueFactory(new PropertyValueFactory<Author, Integer>("linesOfCode"));
        proportionColumn.setCellValueFactory(new PropertyValueFactory<Author, Double>("proportion"));
    }
}
