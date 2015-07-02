package main.java.backend;

import java.util.ArrayList;

public class Command {
    public static enum Type {
        COLLATE, VIEW, SUMMARY, INVALID
    }

    private Type type;
    private String directory;
    private boolean scanCurrentDirOnly;
    private ArrayList<String> fileTypes;
    
    private String authorName;

    public Command(Type type) {
        setScanCurrentDirOnly(false);
        fileTypes = new ArrayList<String>();
        this.type = type;
    }

    
    // ================================================================
    // Public getters and setters
    // ================================================================

    public Type getCommandType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public boolean willScanCurrentDirOnly() {
        return scanCurrentDirOnly;
    }

    public void setScanCurrentDirOnly(boolean scanCurrentDirOnly) {
        this.scanCurrentDirOnly = scanCurrentDirOnly;
    }

    public ArrayList<String> getFileTypes() {
        return fileTypes;
    }

    public void setFileTypes(ArrayList<String> fileTypes) {
        this.fileTypes = fileTypes;
    }
    
    

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
