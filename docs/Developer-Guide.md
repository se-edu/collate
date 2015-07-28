# Introduction
Collate is a simple tool for developers to consolidate the contributions of a project's authors into separate files. It is Java application that utilises JavaFX for its Graphical User Interface (GUI).

This guide describes the design and implementation of Collate. It will help you understand how Collate works and how you can further contribute to its development. I have organised this guide in a top-down manner so that you can understand the big picture before moving on to the more detailed sections.

# Architecture
![Architecture](images/architecture.png)

Collate is made up for five main components. Users can either use Collate through the GUI or Text UI (TUI) components.

1) The GUI component consists of JavaFX's FXML files which define the layout that users interact with and the Java files which control these FXML files.

2) The TUI component is an alternative of the GUI component. Users can enter commands through the command line interface (CLI).

2) The Backend component contains all the logic needed to parse users' commands, store collated data into individual files, etc.

3) The Data component represent data objects such as the authors of the project and code snippets that were written.

4) The Test Driver component utilises JUnit for unit testing.

# GUI Component
![Class diagram for GUI](images/gui-class-diagram.png)

The GUI component is made up of two packages, `gui` and `view`. The `gui` package contains the Java files that control what users see while the `view` package contains JavaFX's `fxml` files that describe how to layout JavaFX components.
A `stylesheet.css` is also found in the `view` package. This stylesheet customises the appearance and style of JavaFX components.

Users will enter commands through the `CommandBarController`, which then passes these commands to the `MainApp`. `MainApp` will then call `Logic` in the `backend` package to handle the actual execution of these commands.

`MainApp` is then responsible for displaying the correct view to update what the user sees. Collate has two views, a `Summary` view and `FileStats` view which are controlled by their corresponding controller classes. These classes will be elaborated upon in the following sections.

## MainApp Class
The `MainApp` class is the main driver for the GUI component. It controls what users see and handles user inputs by passing them to the `backend` package.

`MainApp` extends from JavaFX's `Application` class and overrides its `start` method. This method is the starting point of the whole application and very importantly, initialises all the components that are required for the GUI. This method facilitates the loading of `RootLayout.fxml` from the `view` package.

The `start` method then calls `initPrimaryStage(Stage)` as seen below. This method creates a new JavaFX `Scene` using this `RootLayout` and sets the main `Stage` to show this scene.

###### Code snippet from MainApp.java
``` java
private void initPrimaryStage(Stage primaryStage) {
    this.primaryStage = primaryStage;
    this.primaryStage.setTitle(WINDOW_TITLE);
    this.primaryStage.setScene(new Scene(rootLayout));
    this.primaryStage.show();
}
```
`RootLayout.fxml` has a type of JavaFX's `BorderPane` which is a type of `Pane`. It allows us to layout JavaFX components by specifying which position of the `BorderPane` they should appear in, be it top, left, right, bottom or center. The command bar where users enter commands in is positioned at the bottom and the statistics from the `collate` command is placed in the center.

> You can also customise the height and width of the window by modifying the `prefHeight` and `prefWidth` parameters in `RootLayout.fxml`.

When a user presses any key, `MainApp` receives this information and decides what to do with them. The current implementation listens for the enter key which implies that the user has entered a command and would like for it to be executed.

> This implementation allows Collate to be extended to listen for other keystrokes such as `tab`, `up`, `down`, etc.

#### Notable APIs
Return type | Method and Description
----------- | ----------------------
void | `handleKeyPress(CommandBarController commandBarController, KeyCode key, String userInput)`: Decides what to do when the `key` is entered.

## CommandBarController Class
The `CommandBarController` loads `CommandBar.fxml` which contains a JavaFX `TextField` for users to enter commands and a JavaFX `Label` which shows feedback when commands are entered.

`CommandBar.fxml`, similar to `RootLayout.fxml`, has a type of `BorderPane`. The `Label` is placed at the top of the `BorderPane` and the `TextField` is placed in the center.

> As no preferred heights or widths are specified, JavaFX takes the default heights of the components and uses its parent container to calculate their widths. In the case of Collate, the `Label` and `TextField` inherit the width of `RooyLayout.fxml`. You can read more about `BorderPane` [here](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/BorderPane.html).

This class has a reference to `MainApp` and calls the `handleKeyPress` method of `MainApp` whenever a key is pressed. This ensures that the logic is handled by `MainApp` to avoid unnecessary coupling between `commandBarController` and `Logic`.

#### Notable APIs
Return type | Method and Description
----------- | ----------------------
void | `clear()`: Clear the command bar
void | `setFeedback(String feedbackText)`: Set the text of the feedback label

## SummaryController Class
The `SummaryController` is the default view after entering the `collate` command. It shows a table with three columns: author's name, lines of code and proportion of code written by the author.

This class loads `Summary.fxml` which is a type of `StackPane` (also a type of `Pane`) and contains a `TableView`.

> The use of `StackPane` ensures the `TableView` has a maximum height and width i.e fills the space given by its parent container.

`SummaryController` uses JavaFX's APIs to iterate through `Author` objects (from the `data` package) and construct rows based on certain attributes of these objects.

> More details of these JavaFX APIs can be found [here](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableView.html).

## FileStatsController Class
### FileStatsItem Class

# TUI component

# Backend Component
![Class diagram for Backend](images/backend-class-diagram.png)
## Logic Class
## CommandParser Class
### Command Class
## Storage Class

# Data Component
![Class diagram for Data](images/data-class-diagram.png)
## Author Class
## CodeSnippet Class
## SourceFile Class

# Test Driver Component