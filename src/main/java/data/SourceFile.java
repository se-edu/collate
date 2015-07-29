package main.java.data;

/**
 * Each SourceFile contains its file location (relative to the root directory)
 * and total number of lines.
 * 
 * @author Sebastian Quek
 * 
 */
public class SourceFile {

    private int numLines;
    private String relativeFilePath;
    private String language;

    public SourceFile(String relativeFilePath, String language) {
        this.numLines = 0;
        this.relativeFilePath = relativeFilePath;
        this.language = language;
    }
    
    public int getNumLines() {
        return numLines;
    }
    
    public void addNumLines(int value) {
        numLines += value;
    }

    public String getRelativeFilePath() {
        return relativeFilePath;
    }
    
    public String getLanguage() {
        return language;
    }
}
