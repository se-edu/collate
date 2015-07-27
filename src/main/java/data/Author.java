package main.java.data;

import java.util.ArrayList;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class contains information about an author.
 * It utilises Properties so that JavaFX can display the information easily.
 * 
 * https://docs.oracle.com/javase/8/javafx/properties-binding-tutorial/binding.htm
 * 
 * @author Sebastian Quek
 * 
 */
public class Author {
    
    private StringProperty name;
    private IntegerProperty linesOfCode;
    private DoubleProperty proportion;

    private ArrayList<CodeSnippet> codeSnippets;

    public Author(String name) {
        this.name = new SimpleStringProperty(name);
        this.linesOfCode = new SimpleIntegerProperty(0);
        this.proportion = new SimpleDoubleProperty(0);
        this.codeSnippets = new ArrayList<CodeSnippet>();
    }

    
    // ================================================================
    // Name property
    // ================================================================
    
    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getName() {
        return nameProperty().get();
    }

    
    // ================================================================
    // Lines of code property
    // ================================================================
    
    public IntegerProperty linesOfCodeProperty() {
        setLinesOfCode(calculateNumLines());
        return linesOfCode;
    }

    public void setLinesOfCode(int value) {
        this.linesOfCode.set(value);
    }

    public int getLinesOfCode() {
        return linesOfCodeProperty().get();
    }
    
    private int calculateNumLines() {
        int totalLines = 0;
        for (CodeSnippet codeSnippet : codeSnippets) {
            totalLines += codeSnippet.getNumLines();
        }
        return totalLines;
    }
    
    
    // ================================================================
    // Proportion property
    // ================================================================
    
    public DoubleProperty proportionProperty() {
        setProportion(calculateProportion());
        return proportion;
    }

    public void setProportion(Double value) {
        this.proportion.set(value);
    }

    public double getProportion() {
        return proportionProperty().get();
    }

    private Double calculateProportion() {
        double percentage = (double) (getLinesOfCode()) /
                            CodeSnippet.getTotalLines() * 100;
        return (double) Math.round(percentage * 100) / 100;
    }
    
    
    // ================================================================
    // Code snippets field
    // ================================================================
    
    public ArrayList<CodeSnippet> getCodeSnippets() {
        return codeSnippets;
    }

    public void addCodeSnippet(CodeSnippet snippet) {
        this.codeSnippets.add(snippet);
    }
}
