package main.java.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

//@author Sebastian Quek
public class MainApp extends Application {

    private static final String ROOT_LAYOUT_FXML = "/main/resources/layouts/RootLayout.fxml";

    private static final String WINDOW_TITLE = "Collate";
    private static final String COMMAND_BAR_DEFAULT_TEXT = "Enter command here";

    private Stage primaryStage;
    private BorderPane rootLayout;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        initRootLayout();
        initPrimaryStage(primaryStage);

        // Add components to RootLayout
        addCommandBar();
        addOverview();        
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

    private void addOverview() {
        rootLayout.setCenter(new OverviewLayoutController());
    }

    private void addCommandBar() {
        rootLayout.setBottom(new CommandBarController(COMMAND_BAR_DEFAULT_TEXT));
    }
}