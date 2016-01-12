# Sebastian
###### main\java\gui\CommandBarController.java
```java
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
```
###### main\java\gui\MainApp.java
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
        
        Logic logic = initLogic();

        // Add components to RootLayout
        addCommandBar(logic);
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
    
    private Logic initLogic() {
        return new Logic();       
    }

    private void addOverview() {
        rootLayout.setCenter(new OverviewLayoutController());
    }

    private void addCommandBar(Logic logic) {
        rootLayout.setBottom(new CommandBarController(COMMAND_BAR_DEFAULT_TEXT, logic));
    }
}
```
###### main\java\gui\OverviewLayoutController.java
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
###### main\resources\layouts\CommandBar.fxml
```fxml
<fx:root onKeyPressed="#onKeyPress" stylesheets="@../styles/stylesheet.css"
	type="TextField" xmlns="http://javafx.com/javafx/8.0.40" xmlns:fx="http://javafx.com/fxml/1" />
```
###### main\resources\layouts\Overview.fxml
```fxml
<fx:root type="StackPane" xmlns="http://javafx.com/javafx/8.0.40" xmlns:fx="http://javafx.com/fxml/1">
	<children>
		<ListView fx:id="overviewList">
			<!-- TODO Add Nodes -->
		</ListView>
	</children>
</fx:root>
```
###### main\resources\layouts\RootLayout.fxml
```fxml
<BorderPane id="root" prefHeight="500.0" prefWidth="500.0"
	stylesheets="@../styles/stylesheet.css" xmlns="http://javafx.com/javafx/8.0.40"
	xmlns:fx="http://javafx.com/fxml/1" />
```
###### main\resources\styles\stylesheet.css
```css

#root {
	-fx-padding: 10px;
}
```
