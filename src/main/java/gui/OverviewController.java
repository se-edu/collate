package main.java.gui;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import main.java.backend.Author;

// @@author Sebastian Quek
public class OverviewController extends StackPane {
    @FXML
    private TableView<Author> overviewTable;
    @FXML
    private TableColumn<Author, String> authorNameColumn;
    @FXML
    private TableColumn<Author, Integer> linesOfCodeColumn;
    @FXML
    private TableColumn<Author, Double> proportionColumn;

    private static final String OVERVIEW_LAYOUT_FXML = "/main/resources/layouts/Overview.fxml";

    public OverviewController(MainApp mainApp, ObservableList<Author> overviewData) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(OVERVIEW_LAYOUT_FXML));
        loader.setController(this);
        loader.setRoot(this);

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        overviewTable.setItems(overviewData);
        authorNameColumn.setCellValueFactory(new PropertyValueFactory<Author, String>("name"));
        linesOfCodeColumn.setCellValueFactory(new PropertyValueFactory<Author, Integer>("linesOfCode"));
        proportionColumn.setCellValueFactory(new PropertyValueFactory<Author, Double>("proportion"));
    }
}
