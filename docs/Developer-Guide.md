# Introduction
Collate is a simple tool for developers to consolidate the contributions of a project's authors into separate files. It is Java application that utilises JavaFX for its Graphical User Interface (GUI).

This guide describes the design and implementation of Collate. It will help you understand how Collate works and how you can further contribute to its development. I have organised this guide in a top-down manner so that you can understand the big picture before moving on to the more detailed sections.

# Table of Contents
<!-- TOC depth:3 withLinks:1 updateOnSave:0 orderedList:0 -->

- [Architecture](#architecture)
- [GUI Component](#gui-component)
	- [MainApp Class](#mainapp-class)
	- [CommandBarController Class](#commandbarcontroller-class)
	- [SummaryController Class](#summarycontroller-class)
	- [FileStatsController Class](#filestatscontroller-class)
		- [FileStatsItem Class](#filestatsitem-class)
- [TUI component](#tui-component)
- [Backend Component](#backend-component)
	- [Logic Class](#logic-class)
	- [CommandParser Class](#commandparser-class)
		- [Command Class](#command-class)
	- [Storage Class](#storage-class)
- [Data Component](#data-component)
	- [Author Class](#author-class)
	- [CodeSnippet Class](#codesnippet-class)
	- [SourceFile Class](#sourcefile-class)
- [Testing](#testing)
- [Future Development](#future-development)

<!-- /TOC -->

# Architecture
![Architecture](images/architecture.png)

Collate is made up for five main components. Users can either use Collate through the GUI or Text UI (TUI) components.

1. The GUI component consists of JavaFX's FXML files which define the layout that users interact with and the Java files which control these FXML files.
2. The TUI component is an alternative of the GUI component. Users can enter commands through the command line interface (CLI).
3. The Backend component contains all the logic needed to parse users' commands, store collated data into individual files, etc.
4. The Data component represents objects involved in the collation of source files such as the authors of the project and code snippets that were written.
5. The Test Driver component tests the TUI, Backend and Data components. It utilises JUnit for unit testing.

# GUI Component
![Class diagram for GUI](images/gui-class-diagram.png)

The GUI component is made up of two packages, `gui` and `view`. The `gui` package contains the Java files that control what users see while the `view` package contains JavaFX's `fxml` files that describe how to layout JavaFX components. A `stylesheet.css` is also found in the `view` package. This stylesheet customises the appearance and style of JavaFX components.

Users will enter commands through the `CommandBarController`, which then passes these commands to the `MainApp`. `MainApp` will then call `Logic` in the `backend` package to handle the actual execution of these commands.

`MainApp` is then responsible for correctly displaying and updating the GUI. Collate has two views that users can see, a `Summary` view and `FileStats` view which are controlled by their corresponding controller classes. These classes will be elaborated upon in the following sections.

## MainApp Class
The `MainApp` class is the main driver for the GUI component. It controls what users see and handles user inputs by passing them to the `backend` package.

`MainApp` extends from JavaFX's `Application` class and overrides its `start` method. This method is the starting point of the whole application and very importantly, initialises all the components that are required for the GUI. This method also calls another method to load `RootLayout.fxml` from the `view` package. `RootLayout.fxml` contains the information about the layout of Collate's base components.

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
`RootLayout.fxml` is a type of JavaFX's `BorderPane` which in turn is a type of `Pane`. It allows us to layout JavaFX components by specifying which position of the `BorderPane` they should appear in, be it top, left, right, bottom or centre. The command bar where users enter commands in is positioned at the bottom and the statistics from the `collate` command is placed in the centre.

> You can also customise the height and width of the window by modifying the `prefHeight` and `prefWidth` parameters in `RootLayout.fxml`.

When a user presses any key, `MainApp` receives this information and decides what to do next. The current implementation listens for the enter key being pressed. The enter key is used by users to express their intent to execute the command they have typed out.

> This implementation allows Collate to be extended to listen for other keystrokes such as `tab`, `up`, `down`, etc.

#### Notable APIs
Return type | Method and Description
----------- | ----------------------
void | `handleKeyPress(CommandBarController commandBarController, KeyCode key, String userInput)`: Decides what to do when the `key` is entered.

## CommandBarController Class
The `CommandBarController` loads `CommandBar.fxml` which contains a JavaFX `TextField` for users to enter commands and a JavaFX `Label` which shows feedback when commands are entered.

`CommandBar.fxml`, similar to `RootLayout.fxml`, is a `BorderPane`. The `Label` is placed at the top of the `BorderPane` and the `TextField` is placed in the centre.

> As no preferred heights or widths are specified, JavaFX takes the default heights of the components and uses its parent container to calculate their widths. In the case of Collate, the `Label` and `TextField` inherit the width of `RooyLayout.fxml`. You can read more about `BorderPane` [here](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/BorderPane.html).

This class has a reference to `MainApp` and calls the `handleKeyPress` method of `MainApp` whenever a key is pressed. This ensures that the logic is handled by `MainApp` to avoid unnecessary coupling between `commandBarController` and `Logic`.

#### Notable APIs
Return type | Method and Description
----------- | ----------------------
void | `clear()`: Clear the command bar
void | `setFeedback(String feedbackText)`: Set the text of the feedback label

## SummaryController Class
The `SummaryController` controls the default view after entering the `collate` command. It shows a table with three columns: author's name, lines of code and proportion of code written by the author.

This class loads `Summary.fxml` which is a type of `StackPane` (also a type of `Pane`) and contains a `TableView`.

> The use of `StackPane` ensures the `TableView` has a maximum height and width i.e fills the space given by its parent container.

`SummaryController` uses JavaFX's APIs to iterate through `Author` objects (from the `data` package) and construct rows based on certain attributes of these objects.

> More details of these JavaFX APIs can be found [here](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableView.html).

## FileStatsController Class
The `FileStatsController` controls the view that is shown when a user enters the `view` command. It shows the files that the specified target author has contributed to and the proportion of code he/she wrote in those files.

This class loads `FileStats.fxml` which is a type of `BorderPane` and has a `Label` positioned on top and a `ListView` positioned in the center. The `Label` is used to indicate the target author's name and the `ListView` is used to show his/her contribution statistics.

This class' constructor requires a `HashMap` of `SourceFile` objects (from the `data` package) and the corresponding number of lines the target author has written for that `SourceFile`. It then uses this data to construct `FileStatsItem` objects to be shown in a `ListView`.

> The `SourceFile` object is a representation of source files that have been collated.

A `ListView` contains objects of a certain type and these objects are laid out vertically. In this case, the `ListView` contains our custom `FileStatsItem` objects. JavaFX will then render these objects as per the layouts of the corresponding `FileStatsItem` objects which will be elaborated upon in the following section.

> By creating custom objects and using `ListView` to show them, you can create complex list-based layouts.

### FileStatsItem Class
![FileStatsItem](images/filestatsitem.png)
Each `FileStatsItem` object consists of nine JavaFX components.

1. `BorderPane` - The `FileStatsItem.fxml` is of type `BorderPane`. It has a `StackPane` at its center.
2. `StackPane` - The outer container which facilitates the styling of the shadow of the object.
3. `HBox card` - Forms the inner container of this custom component and lays out its children horizontally.
4. `StackPane` - Forms the container for the `circle` and `percentage` components.
5. `Shape circle` - Coloured circle behind the percentage value.
6. `Text percentage` - The text that overlays the circle.
7. `VBox` - Lays out the `filename` and `linesOfCode` vertically
8. `Text filename`
9. `Text linesOfCode`

The `StackPane` allows the `percentage` to appear in front of the `circle`. The `card` `HBox` component has two children, first, this `StackPane` and second, the `VBox`.

> The `StackPane` component enables you to position elements along the z-axis while the `HBox` and `VBox` components enable you to position elements horizontally or vertically. Together, they allow you to create your own custom components easily.

This class' constructor uses its percentage parameter to generate a colour for the `circle`, with green indicating a higher percentage and red indicating a lower percentage.

This class also implements `Comparable` to enable sorting of `FileStatsItem` objects based on their percentage values.

# Text UI component
The `TUI` components consists of one class, `Collate`. You can export the `.jar` file and specify this class as the main class and run Collate from the command line i.e `java -jar Collate-TUI.jar`.

## Collate class
The `Collate` class receives commands from the command line and passes them to the `Backend` component to handle. `Collate` then prints the respective statistics in the command line. This class relies on the `Data` component to show the appropriate statistics.

# Backend Component
![Class diagram for Backend](images/backend-class-diagram.png)
The `Backend` component is made up of four classes. At the centre of this component is the `Logic` class which is in charge of handling the execution of user inputs from the `GUI` component. This component only relies on the `Data` component and works independently from the `GUI` and `TUI` components.

## Logic Class
![Sequence diagram for collate command](images/sequence-diagram-collate-command.png)

The `Logic` class contains the methods that form the functionality of Collate. It can be thought of as the "brain" of Collate. User inputs are passed to the `executeCommand(String)` method which parses the input to find out what type of command the input is. Finding the type of command is done in the `CommandParser` class which will be elaborated in the next section.

After knowing the type of command, `Logic` executes the command and updates the its relevant fields before calling the `Storage` class to store the collated data if necessary. The data is stored in Markdown files. More details are mentioned in the `Storage` section.

The `executeCommand(String)` method will then return the type of command to its caller method. The caller method can then decide how to update the user interface.

This class provides several APIs for the user interface components (`GUI` and `TUI`) to obtain information and render them for the user.

#### Notable APIs
Return type | Method and Description
----------- | ----------------------
Command.Type | `executeCommand(String userInput)`: Handle the execution of user inputs
Collection<Author> | `getAuthors()`: Get the authors of the project that has been collated.
String | `getTargetAuthorName()`: Get the name of the target author that was specified in the user's `view` command.
HashMap&lt;SourceFile, Integer&gt; | `getTargetAuthorStatistics()`: Get the statistics of the target author in the form of a `HashMap` with the keys as the `SourceFile` objects that the author contributed to and the values as the number of lines he/she wrote for that `SourceFile`.

## CommandParser Class
The `CommandParser` class figures out what type of command has been entered by the user. It creates `Command` objects which are then passed to `Logic` to be executed.

This class plays the important role of defining the fields in each `Command` object depending on the type of command. For example, if a user enters `view author1`, `CommandParser` creates a `Command` object that has its `authorName` initialised to "author1". These fields can then be accessed by `Logic` to execute the command properly.

#### Notable APIs
Return type | Method and Description
----------- | ----------------------
Command | `parse(String userInput)`: Analyses the given `userInput` to determine its type and returns a `Command` object with all the relevant fields initialised.

### Command Class
This class is solely created by `CommandParser` and is executed by `Logic`. It is a simple Java class with several fields and corresponding public getters and setters.

#### Notable getter APIs
Return type | Method and Description
----------- | ----------------------
Type | `getCommandType()`: Returns the `Type` of command.
String | `getDirectory()`: Returns the directory for `collate` commands.
boolean | `willScanCurrentDirOnly()`: Returns the boolean that determines whether only the current folder should be scanned for source files.
ArrayList&lt;String&gt; | `getFileTypes()`: Returns the list of types of files that are to be scanned.
String | `getAuthorName()`: Returns the target author name from `view` commands.

## Storage Class
`Storage` is a simple class that has one public method which takes in a list of lines and saves them to a local file in a default directory. This default directory is defined in this class.

The current implementation saves collated data in Markdown files. These files are human-readable and editable, and utilises syntax from [Github Flavored Markdown](https://help.github.com/articles/github-flavored-markdown/).

#### Notable APIs
Return type | Method and Description
----------- | ----------------------
void | `addCollatedFile(String fileName, ArrayList<String> collatedLines)`: Saves the `collatedLines` in the default save directory with `fileName` as its name and `.md` as its extension.

# Data Component
![Class diagram for Data](images/data-class-diagram.png)

The `Data` component contains the classes that represent the various elements that are required in contribution statistics.

`Logic` manipulates these classes and the UI components will use the data within these classes to render the view that users will see.

## Author Class
This class represents authors who have contributed to the project. Each `Author` can have multiple associated `CodeSnippet` objects.

`Author` objects have several fields of `Property` type. These class variables are special JavaFX constructs which behave in a similar manner as typical integer, double and String Java types. For example, `Author` has a `IntegerProperty` type for its `linesOfCode` variable. This variable contains an integer which can be accessed by calling `linesOfCode.get()`.

By utilising JavaFX `Property` types, the `GUI` can interact directly with the `Author` class to render details in a table easily.

> You can read more about JavaFX Properties [here](https://docs.oracle.com/javase/8/javafx/properties-binding-tutorial/binding.htm).

#### Notable APIs
Return type | Method and Description
----------- | ----------------------
ArrayList<CodeSnippet> | `getCodeSnippets()`: Get a list of the author's code snippets.
void | `addCodeSnippet(CodeSnippet snippet)`: Add a `CodeSnippet` to the list of code snippets.
int | `getLinesOfCode()`: Gets the total number of lines of code the author wrote.
String | `getName()`: Gets the name of the author.
double | `getProportion()`: Gets the overall percentage of code the author wrote as compared to the total number of lines of code in the project.

## CodeSnippet Class
Each `CodeSnippet` represents the lines of code between two consecutive author tags that were written by an `Author`. Since this code belongs to a file, a `SourceFile` object is associated with each `CodeSnippet` object.

The `CodeSnippet` class has a static variable `totalLines`. This variable stores the total number of lines of code of all code snippets and is used to calculate the overall proportion of code each author wrote.

#### Notable APIs
Return type | Method and Description
----------- | ----------------------
void | `addLine(String line)`: Add a line of code to the code snippet.
Author | `getAuthor()`: Get the `Author` of the code snippet.
SourceFile | `getFile()`: Get the `SourceFile` of the code snippet.
int | `getNumLines()`: Get the number of lines of code in this code snippet.
static int | `getTotalLines()`: Get the total number of lines of code of all code snippets.
static void | `resetTotalLines()`: Resets the total number of lines of code of all code snippets.

## SourceFile Class
This class represents source files that contain at least one author tag. Each `SourceFile` object is constructed with the file's path relative to the user's specified base folder and the file's language. The base folder is specified through the `collate from <FOLDER>` command.

#### Notable APIs
Return type | Method and Description
----------- | ----------------------
int | `getNumLines()`: Get the number of lines of code in this source file.
void | `addNumLines(int value)`: Add to the number of lines of code in this source file.
String | `getLanguage()`: Get the programming language of the source file.
String | `getRelativeFilePath()`: Get the relative path of the source file.

# Testing
Collate uses JUnit to perform unit tests on the `Backend`, `TUI` and `Data` components. Every method is unit tested to ensure everything works as intended.

Tests are placed in the `src/test` folder and if you require that actual files be used for tests, you can place them in the `src/test/testFiles` folder. Be mindful to update existing tests that rely on the current source files within the `src/text/testFiles` folder.

# Future Development
There are several additions that can be made to Collate to further increase its usefulness and usability.

#### GUI testing
The current implementation does not perform any tests on the `GUI` component and relies solely on visual feedback to ensure the user interface is showing information correctly.

#### Saving and loading of statistics
By enabling users to save and load the statistics from a `collate` command, users will be able to view the progression of contributions of authors by collating at different stages of the project.

Furthermore, Collate can show a graph of contribution statistics based on these saved statistics.

#### Better command feedback
Having more specific feedback to users' commands will allow users to find that what exactly happened after the command was entered. Invalid commands will show more intelligent feedback based on what the user entered.

#### Auto-completion of commands
Allowing users to press a button to auto-complete a command makes it much easier to enter commands and reduces the margin of error.

#### History of commands
Users can traverse this history to re-execute previously typed commands. This becomes especially useful if a user wants to collate the source files in the same folder periodically.
