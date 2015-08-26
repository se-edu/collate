package main.java.data;

import java.util.ArrayList;

/**
 * This class contains information about each author of the project.
 * 
 * @author Sebastian Quek
 * 
 */
public class Author {
    
    private String name;
    private int linesOfCode;
    private double proportion;

    private ArrayList<CodeSnippet> codeSnippets;

    public Author(String name) {
        this.name = name;
        this.linesOfCode = 0;
        this.proportion = 0;
        this.codeSnippets = new ArrayList<CodeSnippet>();
    }

    
    // ================================================================
    // Name
    // ================================================================

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    
    // ================================================================
    // Lines of code
    // ================================================================
    
    public void setLinesOfCode(int value) {
        this.linesOfCode = value;
    }

    public int getLinesOfCode() {
        setLinesOfCode(calculateNumLines());
        return linesOfCode;
    }
    
    private int calculateNumLines() {
        int totalLines = 0;
        for (CodeSnippet codeSnippet : getCodeSnippets()) {
            totalLines += codeSnippet.getNumLines();
        }
        return totalLines;
    }
    
    
    // ================================================================
    // Proportion
    // ================================================================

    public void setProportion(Double value) {
        this.proportion = value;
    }

    public double getProportion() {
        setProportion(calculateProportion());
        return proportion;
    }

    private Double calculateProportion() {
        double percentage = (double) (getLinesOfCode()) /
                            CodeSnippet.getTotalLines() * 100;
        return (double) Math.round(percentage * 100) / 100;
    }
    
    
    // ================================================================
    // Code snippets
    // ================================================================
    
    public ArrayList<CodeSnippet> getCodeSnippets() {
        return codeSnippets;
    }

    public void addCodeSnippet(CodeSnippet snippet) {
        getCodeSnippets().add(snippet);
    }
}
