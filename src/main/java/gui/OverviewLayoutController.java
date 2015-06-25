package main.java.gui;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;

public class OverviewLayoutController extends StackPane {
    @FXML
    private ListView<String> overviewList;

    private static final String OVERVIEW_LAYOUT_FXML = "/main/resources/layouts/Overview.fxml";
    
    private static ObservableList<String> obsList = FXCollections.observableArrayList();

    public OverviewLayoutController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(OVERVIEW_LAYOUT_FXML));
        loader.setController(this);
        loader.setRoot(this);

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        overviewList.setItems(obsList);
    }
    
    public static void updateOverviewDisplay(ArrayList<String> stats) {
        obsList.addAll(stats);
    }
}
