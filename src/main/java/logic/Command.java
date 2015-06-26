package main.java.logic;

import java.util.ArrayList;

public class Command {
    public static enum Type {
        COLLATE, INVALID
    }

    private Type type;
    private String directory;
    private boolean scanCurrentDirOnly;
    private ArrayList<String> fileTypes;

    public Command() {
        setScanCurrentDirOnly(false);
        fileTypes = new ArrayList<String>();
    }

    
    // ================================================================
    // Public getters
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
}
