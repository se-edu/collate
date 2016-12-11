package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import main.java.backend.Command;
import main.java.backend.Logic;
import main.java.data.Author;
import main.java.data.SourceFile;

import org.junit.Before;
import org.junit.Test;

public class TestLogic {

    private static final String ROOT_DIR = System.getProperty("user.dir");
    private static final String TEST_RESOURCES_DIR = "/src/test/testFiles/";
    private static final String TEST_FILE1 = "testFile1.txt";
    private static final String TEST_FILE2 = "testFile2.txt";
    private static final String TEST_FILE3 = "testFile3.txt";
    private static final String TEST_FILE4 = "testFile4.txt";
    private static final String TEST_FILE5 = "testFile5.txt";
    private static final String TEST_FILE_HTML = "testFileHtml.html";
    private static final String TEST_FILE_NO_EXTENSION = "testFileNoExtension";
    private static final String TEST_FILE_IN_SUBFOLDER = "testFile1.txt";
    private static final String RELATIVE_PATH_TEST_FILE_IN_SUBFOLDER =
        "subfolder/" + TEST_FILE_IN_SUBFOLDER;
    
    private static final String FULL_PATH_TEST_FILE1 = getFullPath(TEST_FILE1);
    private static final String FULL_PATH_TEST_FILE3 = getFullPath(TEST_FILE3);
    private static final String FULL_PATH_TEST_FILE4 = getFullPath(TEST_FILE4);
    private static final String FULL_PATH_TEST_FILE5 = getFullPath(TEST_FILE5);
    private static final String FULL_PATH_TEST_FILE_HTML = getFullPath(TEST_FILE_HTML);
    
    private static final String AUTHOR1 = "author1";
    private static final String AUTHOR2 = "author2";
    private static final String AUTHOR3_REUSED = "author3-reused";
    private static final String AUTHOR4 = "author4";

    private Logic logic;

    @Before
    public void init() {
        logic = new Logic();
    }

    @Test
    public void testHandleEnterPress() {
        assertEquals(Command.Type.COLLATE,
                     logic.executeCommand("collate from \"" + ROOT_DIR +
                                          TEST_RESOURCES_DIR + "\""));

        assertEquals(Command.Type.COLLATE,
                     logic.executeCommand("collate from \"" + ROOT_DIR +
                                          TEST_RESOURCES_DIR + "\" only"));

        assertEquals(Command.Type.COLLATE,
                     logic.executeCommand("collate from \"" + ROOT_DIR +
                                          TEST_RESOURCES_DIR +
                                          "\" include java"));

        assertEquals(Command.Type.COLLATE,
                     logic.executeCommand("collate from \"" + ROOT_DIR +
                                          TEST_RESOURCES_DIR + "\" include txt"));

        assertEquals(Command.Type.COLLATE,
                     logic.executeCommand("collate from " +
                                          FULL_PATH_TEST_FILE1));

        assertEquals(Command.Type.VIEW, logic.executeCommand("view A1234567Z"));

        assertEquals(Command.Type.VIEW, logic.executeCommand("view author1"));

        assertEquals(Command.Type.SUMMARY, logic.executeCommand("summary"));

        assertEquals(Command.Type.INVALID, logic.executeCommand("from"));
    }
    
    @Test
    public void testGetAuthors() {
        assertCollatedOneAuthor(FULL_PATH_TEST_FILE1, AUTHOR1);
    }
    
    @Test
    public void testGetAuthorsWithDashesInName() {
        assertCollatedOneAuthor(FULL_PATH_TEST_FILE3, AUTHOR3_REUSED);
    }
    
    @Test
    public void testGetAuthorsWithDashAtStartOrEndOfName() {
        assertCollatedOneAuthorWithLinesOfCode(FULL_PATH_TEST_FILE4, AUTHOR2, 5);
    }
    
    @Test
    public void testGetAuthorsWithNonAlphaNumericCharacters() {
        assertCollatedOneAuthorWithLinesOfCode(FULL_PATH_TEST_FILE5, AUTHOR4, 6);
    }
    
    @Test
    public void testGetAuthorsForHtml() {
        assertCollatedOneAuthor(FULL_PATH_TEST_FILE_HTML, AUTHOR3_REUSED);
    }
    
    @Test
    public void testGetTargetAuthorName() {
        assertEquals(null, logic.getTargetAuthorName());
        logic.executeCommand("collate from " + FULL_PATH_TEST_FILE1);
        logic.executeCommand("view " + AUTHOR1);
        assertEquals(AUTHOR1, logic.getTargetAuthorName());
    }

    @Test
    public void testGetTargetAuthorStatistics() {
        logic.executeCommand("collate from " + ROOT_DIR +
                             TEST_RESOURCES_DIR);
        assertTrue(logic.getTargetAuthorStatistics().isEmpty());

        logic.executeCommand("view " + AUTHOR1);
        HashMap<SourceFile, Integer> statistics =
            logic.getTargetAuthorStatistics();
        for (SourceFile sourceFile : statistics.keySet()) {
            String currentFileLocation = sourceFile.getRelativeFilePath();
            if (currentFileLocation.equals(TEST_FILE1)) {
                assertEquals(5, (int) statistics.get(sourceFile));
            } else if (currentFileLocation.equals(TEST_FILE2)) {
                assertEquals(2, (int) statistics.get(sourceFile));
            } else if (currentFileLocation.equals(TEST_FILE_NO_EXTENSION)) {
                assertEquals(1, (int) statistics.get(sourceFile));
            }
        }

        logic.executeCommand("view " + AUTHOR2);
        statistics = logic.getTargetAuthorStatistics();
        for (SourceFile sourceFile : statistics.keySet()) {
            String currentFileLocation = sourceFile.getRelativeFilePath();
            if (currentFileLocation.equals(RELATIVE_PATH_TEST_FILE_IN_SUBFOLDER)) {
                assertEquals(4, (int) statistics.get(sourceFile));
            }
        }
    }
    
    /**
     * Expands into the full path for the file.
     */
    private static String getFullPath(String fileName) {
        return "\"" + ROOT_DIR + TEST_RESOURCES_DIR + fileName + "\"";
    }
    
    /**
     * Asserts that collation is only done for the specified author for the file given.
     */
    private void assertCollatedOneAuthor(String filePath, String expectedAuthor) {
        logic.executeCommand("collate from " + filePath);
        assertEquals(1, logic.getAuthors().size());
        for (Author author : logic.getAuthors()) {
            assertEquals(expectedAuthor, author.getName());
        }
    }
    
    /**
     * Asserts that collation is only done for the specified author for the file given,
     * with the correct amount of lines of code.
     */
    private void assertCollatedOneAuthorWithLinesOfCode(String filePath, String expectedAuthor, 
                                                        int expectedLinesOfCode) {
        
        logic.executeCommand("collate from " + filePath);
        assertEquals(1, logic.getAuthors().size());
        for (Author author : logic.getAuthors()) {
            assertEquals(expectedAuthor, author.getName());
            assertEquals(expectedLinesOfCode, author.getLinesOfCode());
        }
    }
}
