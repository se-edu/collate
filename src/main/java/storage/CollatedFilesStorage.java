package main.java.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CollatedFilesStorage {

    private static Logger logger;
    
    private static final String LOG_TAG = "CollatedFilesStorage";
    private static final String DEFAULT_SAVE_DIRECTORY = "collated";

    public CollatedFilesStorage() {
        createSaveDirectory();
        logger = Logger.getLogger(LOG_TAG);
    }

    private void createSaveDirectory() {
        File dir = new File(DEFAULT_SAVE_DIRECTORY);

        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public void addCollatedFile(String fileName, ArrayList<String> collatedLines) {
        try (PrintWriter writer = new PrintWriter(DEFAULT_SAVE_DIRECTORY + "/" +
                                                  fileName + ".md")) {
            for (String line : collatedLines) {
                writer.println(line);
            }
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
