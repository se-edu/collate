package main.java.backend;

import java.util.ArrayList;

public class CodeSnippet {
    private Author author;
    private String fileLocation;
    private ArrayList<String> lines;
    
    // TODO extract languages to a new class
    private String language;
    
    public CodeSnippet(Author author, String fileLocation, String language) {
        this.author = author;
        this.fileLocation = fileLocation;
        this.language = language;
        this.lines = new ArrayList<String>();
        author.addCodeSnippet(this);
    }
    
    public void addLine(String line) {
        lines.add(line);
    }
    
    public Author getAuthor() {
        return author;
    }
    
    public int getNumLines() {
        return lines.size();
    }
    
    @Override
    public String toString() {
        // TODO refactor all the strings
        StringBuilder builder = new StringBuilder();
        builder.append("###### " + fileLocation + "\n");
        builder.append("```" + language + "\n");
        for (String line : lines) {
            builder.append(line + "\n");
        }
        builder.append("```");
        return builder.toString();
    }

}
