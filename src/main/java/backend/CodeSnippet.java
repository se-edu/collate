package main.java.backend;

import java.util.ArrayList;
import java.util.StringJoiner;

/**
 * Each code snippet contains lines that belong to an author (as annotated by
 * the @@author tag). This class also has a SourceFile field which specifies
 * which
 * file this snippet belongs to.
 * 
 * @author Sebastian Quek
 *
 */
public class CodeSnippet {
    
    private static int totalLines = 0; // total number of lines in all code
                                       // snippets

    private static final String STRING_NEW_LINE = "\n";

    private Author author;
    private SourceFile file;
    private ArrayList<String> lines;
    private String language;

    public CodeSnippet(Author author, SourceFile file, String language) {
        this.author = author;
        this.file = file;
        this.language = language;
        this.lines = new ArrayList<String>();
        author.addCodeSnippet(this);
    }

    // ================================================================
    // Static methods
    // ================================================================
    
    public static int getTotalLines() {
        return totalLines;
    }

    public static void resetTotalLines() {
        totalLines = 0;
    }

    // ================================================================
    // Public methods
    // ================================================================
    
    public void addLine(String line) {
        lines.add(line);
        totalLines++;
    }

    public Author getAuthor() {
        return author;
    }

    public int getNumLines() {
        return lines.size();
    }
    
    public SourceFile getFile() {
        return file;
    }

    public String getLanguage() {
        return language;
    }

    /*
     * Utilises StringJoiner to reconstruct the lines as seen in its original
     * source file
     * 
     * http://docs.oracle.com/javase/8/docs/api/java/util/StringJoiner.html
     */
    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(STRING_NEW_LINE);
        for (String line : lines) {
            joiner.add(line);
        }
        return joiner.toString();
    }
}
