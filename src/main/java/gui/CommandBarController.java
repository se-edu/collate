package main.java.gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * This class handles the TextField that users input commands in.
 * 
 * @author Sebastian
 *
 */
public class CommandBarController extends TextField {

    private static final String COMMAND_BAR_LAYOUT_FXML = "/main/resources/layouts/CommandBar.fxml";
    private static final String COMMAND_BAR_DEFAULT_TEXT = 
            "collate from D:\\Documents\\Sebastian\\Dropbox\\collate\\src\\test\\testFiles";

    private MainApp mainApp;

    public CommandBarController(MainApp mainApp) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(COMMAND_BAR_LAYOUT_FXML));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.mainApp = mainApp;
        this.setText(COMMAND_BAR_DEFAULT_TEXT);
        this.selectAll();
    }

    @FXML
    public void onKeyPress(KeyEvent event) {
        mainApp.handleKeyPress(this, event.getCode(), this.getText());
    }
}
