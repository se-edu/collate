package main.java.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.java.backend.Author;
import main.java.backend.Logic;

// @@author Sebastian Quek
public class MainApp extends Application {

    private static final String ROOT_LAYOUT_FXML = "/main/resources/layouts/RootLayout.fxml";

    private static final String WINDOW_TITLE = "Collate";
    private static final String COMMAND_BAR_DEFAULT_TEXT = "collate from D:\\Documents\\Sebastian\\Dropbox\\collate\\src";

    private Stage primaryStage;
    private BorderPane rootLayout;

    private Logic logic;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        initRootLayout();
        initPrimaryStage(primaryStage);

        initLogic();

        // Add components to RootLayout
        addCommandBar(this);
        addOverview(this);
    }

    /**
     * Initialises the RootLayout that will contain all other JavaFX components.
     */
    private void initRootLayout() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ROOT_LAYOUT_FXML));
        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialises the main JavaFX Stage with RootLayout being the main Scene.
     * 
     * @param primaryStage
     */
    private void initPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(WINDOW_TITLE);
        this.primaryStage.setScene(new Scene(rootLayout));
        this.primaryStage.show();
    }

    private void initLogic() {
        logic = new Logic();
    }

    private void addCommandBar(MainApp mainApp) {
        rootLayout.setBottom(new CommandBarController(COMMAND_BAR_DEFAULT_TEXT,
                                                      mainApp));
    }

    private void addOverview(MainApp mainApp) {
        rootLayout.setCenter(new OverviewLayoutController(mainApp));
    }
    
    
    // ================================================================
    // Methods which refer to Logic directly
    // ================================================================
    public ObservableList<Author> getOverviewData() {
        return logic.getOverviewData();
    }

    public void handleKeyPress(CommandBarController commandBarController,
                               KeyCode key,
                               String userInput) {
        logic.handleKeyPress(commandBarController, key, userInput);
    }
}