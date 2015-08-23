package main.java.backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * This class saves collated lines as Markdown files.
 * 
 * @author Sebastian Quek
 *
 */
public class Storage {
    
    public static final String DEFAULT_SAVE_DIRECTORY = "collated";
    private static final String COLLATED_FILE_PATH_FORMAT = DEFAULT_SAVE_DIRECTORY + "/%s.md";
    private static final String ERROR_FILE_NOT_FOUND = "%s was not found";

    public Storage() {
        createSaveDirectory();
    }

    private void createSaveDirectory() {
        File dir = new File(DEFAULT_SAVE_DIRECTORY);

        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public void addCollatedFile(String fileName, ArrayList<String> collatedLines) {
        try (PrintWriter writer = new PrintWriter(String.format(COLLATED_FILE_PATH_FORMAT, fileName))) {
            for (String line : collatedLines) {
                writer.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println(String.format(ERROR_FILE_NOT_FOUND, fileName));
        }
    }
}
