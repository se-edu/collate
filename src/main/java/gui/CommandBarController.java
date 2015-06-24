package main.java.gui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;

public class CommandBarController extends TextField {

    private static final String COMMAND_BAR_LAYOUT_FXML = "/main/resources/layouts/CommandBar.fxml";

    public CommandBarController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(COMMAND_BAR_LAYOUT_FXML));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CommandBarController(String text) {
        this();
        this.setText(text);
        this.selectAll();
    }
}
