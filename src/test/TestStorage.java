package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import main.java.backend.Storage;

import org.junit.Before;
import org.junit.Test;

public class TestStorage {

    private static final String FILENAME1 = "storageTest1";
    private static final String FILENAME2 = "storageTest2";
    private static final String FILENAME1_WITH_EXTENSION = FILENAME1 + ".md";
    private static final String PATHNAME = Storage.DEFAULT_SAVE_DIRECTORY +
                                           "/" + FILENAME1_WITH_EXTENSION;
    private Storage storage;

    @Before
    public void init() {
        storage = new Storage();
    }

    @Test
    public void testStorage() {
        File dir = new File(Storage.DEFAULT_SAVE_DIRECTORY);
        assertTrue(dir.exists());
    }

    @Test
    public void testAddCollatedFile() {
        ArrayList<String> collatedLines = generateCollatedLines();
        storage.addCollatedFile(FILENAME1, collatedLines);

        File file = new File(PATHNAME);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            ArrayList<String> lines = new ArrayList<String>();
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            assertEquals(collatedLines.size(), lines.size());

        } catch (FileNotFoundException e) {
            fail("File not found");
        } catch (IOException e) {
            fail("IOException " + e.getMessage());
        }

        file.delete();
    }

    @Test
    public void testMissingSaveDir() {
        File dir = new File(Storage.DEFAULT_SAVE_DIRECTORY);
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                file.delete();
            }
        }
        dir.delete();
        
        ArrayList<String> collatedLines = generateCollatedLines();
        storage.addCollatedFile(FILENAME2, collatedLines);
        
        File file = new File(PATHNAME);
        assertFalse(file.exists());
    }

    private ArrayList<String> generateCollatedLines() {
        ArrayList<String> collatedLines = new ArrayList<String>();
        collatedLines.add("line1");
        collatedLines.add("line2");
        collatedLines.add("line3");
        return collatedLines;
    }
}
