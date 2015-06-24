package main.java.gui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;

public class OverviewLayoutController extends StackPane {

    private static final String OVERVIEW_LAYOUT_FXML = "/main/resources/layouts/Overview.fxml";

    public OverviewLayoutController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(OVERVIEW_LAYOUT_FXML));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
