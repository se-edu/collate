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
    
    public int getNumLines() {
        return numLines;
    }
    
    public void addNumLines(int value) {
        numLines += value;
    }
}
