# Sebastian Quek
###### CommandBarController.java
```java
public class CommandBarController extends TextField {

    private static final String COMMAND_BAR_LAYOUT_FXML = "/main/resources/layouts/CommandBar.fxml";

    public CommandBarController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(COMMAND_BAR_LAYOUT_FXML));
        loader.setController(this);
        loader.setRoot(this);
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

    @FXML
    public void onKeyPress(KeyEvent event) {
        Logic.handleKeyPress(this, event.getCode(), this.getText());
    }
}
```
###### MainApp.java
```java
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
```
###### OverviewLayoutController.java
```java
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
    
    public static void updateOverviewDisplay(ArrayList<String> stats, boolean clearAll) {
        if (clearAll) {
            obsList.clear();
        }
        obsList.addAll(stats);
    }
    
    public static void updateOverviewDisplay(String stat, boolean clearAll) {
        if (clearAll) {
            obsList.clear();
        }
        obsList.add(stat);
    }
}
```
