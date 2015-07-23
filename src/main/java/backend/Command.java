package main.java.backend;

import java.util.ArrayList;

/**
 * Command is a class that contains all the required information for Logic to
 * execute it. It is created by CommandParser's parse method.
 * 
 * @author Sebastian Quek
 *
 */
public class Command {
    
    public enum Type {
        COLLATE, VIEW, SUMMARY, INVALID
    }

    private Type type;
    private String directory;
    private boolean scanCurrentDirOnly;
    private ArrayList<String> fileTypes;
    
    private String authorName;

    public Command(Type type) {
        this.scanCurrentDirOnly = false;
        this.fileTypes = new ArrayList<String>();
        this.type = type;
    }

    public Type getCommandType() {
        return type;
    }


    // ================================================================
    // "Collate" command methods
    // ================================================================

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
    

    // ================================================================
    // "View" command methods
    // ================================================================

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
