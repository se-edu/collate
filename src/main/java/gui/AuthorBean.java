package main.java.gui;

import java.util.ArrayList;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.java.data.Author;
import main.java.data.CodeSnippet;

/**
 * This class is a wrapper class for Author.
 * It utilises Properties so that JavaFX can display the information easily.
 * 
 * https://docs.oracle.com/javase/8/javafx/properties-binding-tutorial/binding.
 * htm
 * 
 * @author Sebastian Quek
 * 
 */
public class AuthorBean {
    private Author author;
    private StringProperty name;
    private IntegerProperty linesOfCode;
    private DoubleProperty proportion;

    public AuthorBean(Author author) {
        this.author = author;
        this.name = new SimpleStringProperty(author.getName());
        this.linesOfCode = new SimpleIntegerProperty(author.getLinesOfCode());
        this.proportion = new SimpleDoubleProperty(author.getProportion());
    }

    public StringProperty nameProperty() {
        return name;
    }

    public IntegerProperty linesOfCodeProperty() {
        return linesOfCode;
    }

    public DoubleProperty proportionProperty() {
        return proportion;
    }

    public ArrayList<CodeSnippet> getCodeSnippets() {
        return author.getCodeSnippets();
    }
}
