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

    public SourceFile(String relativeFilePath) {
        this.numLines = 0;
        this.relativeFilePath = relativeFilePath;
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
}
