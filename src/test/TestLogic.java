package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import main.java.backend.Command;
import main.java.backend.Logic;
import main.java.backend.SourceFile;

import org.junit.Before;
import org.junit.Test;

public class TestLogic {

    private static final String ROOT_DIR = System.getProperty("user.dir");
    private static final String TEST_RESOURCES_DIR_WITH_ENDING_BACKSLASH = "\\src\\test\\testFiles\\";
    private static final String TEST_RESOURCES_DIR_NO_ENDING_BACKSLASH = "\\src\\test\\testFiles";
    private static final String TEST_FILE1 = "testFile1.txt";
    private static final String TEST_FILE2 = "testFile2.txt";
    private static final String TEST_FILE_NO_EXTENSION = "testFileNoExtension";
    private static final String TEST_FILE_IN_SUBFOLDER = "testFile1.txt";
    private static final String RELATIVE_PATH_TEST_FILE_IN_SUBFOLDER = "subfolder\\" + TEST_FILE_IN_SUBFOLDER;
    private static final String FULL_PATH_TEST_FILE1 = ROOT_DIR + TEST_RESOURCES_DIR_WITH_ENDING_BACKSLASH + TEST_FILE1;
    
    private Logic logic;

    @Before
    public void init() {
        logic = new Logic();
    }

    @Test
    public void testHandleEnterPress() {
        assertEquals(logic.handleEnterPress("collate from " + ROOT_DIR +
                                            TEST_RESOURCES_DIR_WITH_ENDING_BACKSLASH),
                     Command.Type.COLLATE);
        
        assertEquals(logic.handleEnterPress("collate from " + ROOT_DIR +
                                            TEST_RESOURCES_DIR_NO_ENDING_BACKSLASH),
                     Command.Type.COLLATE);

        assertEquals(logic.handleEnterPress("collate from " + ROOT_DIR +
                                            TEST_RESOURCES_DIR_WITH_ENDING_BACKSLASH + " only"),
                     Command.Type.COLLATE);

        assertEquals(logic.handleEnterPress("collate from " + ROOT_DIR +
                                            TEST_RESOURCES_DIR_WITH_ENDING_BACKSLASH +
                                            " include java"),
                     Command.Type.COLLATE);

        assertEquals(logic.handleEnterPress("collate from " + ROOT_DIR +
                                            TEST_RESOURCES_DIR_WITH_ENDING_BACKSLASH + " include txt"),
                     Command.Type.COLLATE);

        assertEquals(logic.handleEnterPress("collate from " + FULL_PATH_TEST_FILE1),
                     Command.Type.COLLATE);

        assertEquals(logic.handleEnterPress("view A1234567Z"),
                     Command.Type.VIEW);

        assertEquals(logic.handleEnterPress("view author1"), Command.Type.VIEW);

        assertEquals(logic.handleEnterPress("summary"), Command.Type.SUMMARY);

        assertEquals(logic.handleEnterPress("from"), Command.Type.INVALID);
    }

    @Test
    public void testGetOverviewData() {
        logic.handleEnterPress("collate from " + FULL_PATH_TEST_FILE1);
        assertEquals(logic.getOverviewData().size(), 1);
        assertEquals(logic.getOverviewData().get(0).getName(), "author1");
    }

    @Test
    public void testGetTargetAuthorName() {
        assertEquals(logic.getTargetAuthorName(), null);
        logic.handleEnterPress("collate from " + FULL_PATH_TEST_FILE1);
        logic.handleEnterPress("view author1");
        assertEquals(logic.getTargetAuthorName(), "author1");
    }
    
    @Test
    public void testGetTargetAuthorStatistics() {
        logic.handleEnterPress("collate from " + ROOT_DIR + TEST_RESOURCES_DIR_WITH_ENDING_BACKSLASH);
        assertTrue(logic.getTargetAuthorStatistics().isEmpty());
        
        logic.handleEnterPress("view author1");
        HashMap<SourceFile, Integer> statistics = logic.getTargetAuthorStatistics();
        for (SourceFile sourceFile : statistics.keySet()) {
            String currentFileLocation = sourceFile.getFileLocation();
            if (currentFileLocation.equals(TEST_FILE1)) {
                assertEquals((int) statistics.get(sourceFile), 5);
            } else if (currentFileLocation.equals(TEST_FILE2)) {
                assertEquals((int) statistics.get(sourceFile), 2);
            } else if (currentFileLocation.equals(TEST_FILE_NO_EXTENSION)) {
                assertEquals((int) statistics.get(sourceFile), 1);
            }
        }
        
        logic.handleEnterPress("view author2");
        statistics = logic.getTargetAuthorStatistics();
        for (SourceFile sourceFile : statistics.keySet()) {
            String currentFileLocation = sourceFile.getFileLocation();
            if (currentFileLocation.equals(RELATIVE_PATH_TEST_FILE_IN_SUBFOLDER)) {
                assertEquals((int) statistics.get(sourceFile), 4);
            }
        }
    }

}
