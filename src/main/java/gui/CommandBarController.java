package main.java.gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import main.java.logic.Logic;

//@author Sebastian Quek
public class CommandBarController extends TextField {

    private static final String COMMAND_BAR_LAYOUT_FXML = "/main/resources/layouts/CommandBar.fxml";
    private Logic logic;

    public CommandBarController(Logic logic) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(COMMAND_BAR_LAYOUT_FXML));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.logic = logic;
    }

    public CommandBarController(String text, Logic logic) {
        this(logic);
        this.setText(text);
        this.selectAll();
    }

    @FXML
    public void onKeyPress(KeyEvent event) {
        logic.handleKeyPress(this, event.getCode(), this.getText());
    }
}
