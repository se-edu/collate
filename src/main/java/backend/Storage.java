package main.java.backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class saves collated lines as Markdown files.
 * 
 * @author Sebastian Quek
 *
 */
public class Storage {

    private Logger logger;
    
    private static final String LOG_TAG = "CollatedFilesStorage";
    public static final String DEFAULT_SAVE_DIRECTORY = "collated";
    private static final String COLLATED_FILE_PATH = DEFAULT_SAVE_DIRECTORY + "/%s.md";

    public Storage() {
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
        try (PrintWriter writer = new PrintWriter(String.format(COLLATED_FILE_PATH, fileName))) {
            for (String line : collatedLines) {
                writer.println(line);
            }
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
