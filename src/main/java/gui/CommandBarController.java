package main.java.gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

// @@author Sebastian Quek
public class CommandBarController extends TextField {

    private static final String COMMAND_BAR_LAYOUT_FXML = "/main/resources/layouts/CommandBar.fxml";

    private MainApp mainApp;

    // @@author
    public CommandBarController(MainApp mainApp) {
        this.mainApp = mainApp;

        FXMLLoader loader = new FXMLLoader(getClass().getResource(COMMAND_BAR_LAYOUT_FXML));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CommandBarController(String text, MainApp mainApp) {
        this(mainApp);
        this.setText(text);
        this.selectAll();
    }

    @FXML
    public void onKeyPress(KeyEvent event) {
        mainApp.handleKeyPress(this, event.getCode(), this.getText());
    }
}
