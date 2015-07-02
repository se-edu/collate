package main.java.backend;

public class SourceFile {
    private String fileLocation;
    private int numLines;    

    public SourceFile(String fileLocation) {
        this.fileLocation = fileLocation;
        numLines = 0;
    }
    
    public String getFileLocation() {
        return fileLocation;
    }
    
    public void updateNumLines(int value) {
        numLines += value;
    }
    
    public int getNumLines() {
        return numLines;
    }
}
