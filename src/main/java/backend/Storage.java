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
    private static final String SAVE_DIRECTORY_FORMAT = "%s" + File.separator + "collated";
    private static final String COLLATED_FILE_PATH_FORMAT = "%s" + File.separator + "%s.md";
    private static final String ERROR_FILE_NOT_FOUND = "File was not found: %s";
    
    private String saveFolder;
    
    public Storage(File directory) {
        if (directory.isDirectory()) {
            saveFolder = String.format(SAVE_DIRECTORY_FORMAT, directory);
        } else if (directory.isFile()) {
            saveFolder = String.format(SAVE_DIRECTORY_FORMAT, directory.getParent());
        }
        createSaveDirectory(saveFolder);
    }
    
    public Storage() {
        saveFolder = DEFAULT_SAVE_DIRECTORY;
        createSaveDirectory(DEFAULT_SAVE_DIRECTORY);
    }

    private void createSaveDirectory(String directory) {
        File dir = new File(directory);

        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public void addCollatedFile(String fileName, ArrayList<String> collatedLines) {
        createSaveDirectory(saveFolder);
        try (PrintWriter writer = new PrintWriter(String.format(COLLATED_FILE_PATH_FORMAT, saveFolder, fileName))) {
            for (String line : collatedLines) {
                writer.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println(String.format(ERROR_FILE_NOT_FOUND, fileName));
        }
    }
}
