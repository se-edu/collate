package main.java.logic;

public class Command {
    public static enum Type {
        COLLATE, INVALID
    }

    private Type type;
    private String directory;
    private boolean scanCurrentDirOnly;

    public Command() {
        setScanCurrentDirOnly(false);
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

}
