package main.java.logic;

import javafx.scene.input.KeyCode;
import main.java.gui.CommandBarController;
import main.java.gui.OverviewLayoutController;

public class Logic {
    public Logic() {

    }

    public static void handleKeyPress(CommandBarController commandBar,
                                      KeyCode key,
                                      String userInput) {
        if (key == KeyCode.ENTER) {
            OverviewLayoutController.updateOverviewDisplay(userInput);
            commandBar.clear();
        }
    }
}
