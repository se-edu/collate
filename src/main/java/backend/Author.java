package main.java.backend;

import java.util.ArrayList;

public class Author {
    private String name;
    private ArrayList<CodeSnippet> codeSnippets;
    
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
        int totalLines = 0;
        for (CodeSnippet codeSnippet : codeSnippets) {
            totalLines += codeSnippet.getNumLines();
        }
        return totalLines;
    }
    
    public void addCodeSnippet(CodeSnippet snippet) {
        this.codeSnippets.add(snippet);
    }

}
