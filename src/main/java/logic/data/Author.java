package main.java.logic.data;

import java.util.ArrayList;

public class Author {
    private String name;
    private ArrayList<CodeSnippet> codeSnippets;
    private int totalLines;
    
    public Author(String name) {
        this.name = name;
        this.codeSnippets = new ArrayList<CodeSnippet>();
    }
    
    public String getName() {
        return name;
    }

    public ArrayList<CodeSnippet> getCodeSnippets() {
        return codeSnippets;
    }
    
    public int getTotalLines() {
        return totalLines;
    }
    
    public void addCodeSnippet(CodeSnippet snippet) {
        this.codeSnippets.add(snippet);
    }

}
