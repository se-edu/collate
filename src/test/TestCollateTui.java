package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import main.java.backend.Logic;
import main.java.tui.CollateTui;

import org.junit.Before;
import org.junit.Test;

public class TestCollateTui {
    private static final String ROOT_DIR = System.getProperty("user.dir");
    private static final String TEST_RESOURCES_DIR = "/src/test/testFiles/";

    private Logic logic;
    private String slash;

    @Before
    public void init() {
        logic = new Logic();
        slash = File.separator;
    }

    @Test
    public void testCollateCommand() {
        String userInput =
            "collate from \"" + ROOT_DIR + TEST_RESOURCES_DIR + "\"";
        String output = CollateTui.handleUserInput(logic, userInput);
        assertEquals("Author                LOC      Proportion (%)\n"
                     + "---------------------------------------------\n"
                     + "author2                 5               27.78\n"
                     + "Long-autho...           1                5.56\n"
                     + "author1                 8               44.44\n"
                     + "author3-re...           4               22.22\n",
                     output);
    }

    @Test
    public void testSummaryCommand() {
        testCollateCommand();

        String userInput = "summary";
        String output = CollateTui.handleUserInput(logic, userInput);

        assertEquals("Author                LOC      Proportion (%)\n"
                     + "---------------------------------------------\n"
                     + "author2                 5               27.78\n"
                     + "Long-autho...           1                5.56\n"
                     + "author1                 8               44.44\n"
                     + "author3-re...           4               22.22\n",
                     output);
    }

    @Test
    public void testViewCommand() {
        testCollateCommand();

        String userInput = "view author2";
        String output = CollateTui.handleUserInput(logic, userInput);

        assertTrue(output.contains(slash +
                                   "test file with spaces.txt                            1              100.00\n"));
        assertTrue(output.contains(slash + "subfolder" + slash +
                                   "testFile1.txt                              4              100.00\n"));

        userInput = "view Long-author-name";
        output = CollateTui.handleUserInput(logic, userInput);

        assertTrue(output.contains("...ceedingly_excessively_long_file_name.txt           1              100.00\n"));
    }

    @Test
    public void testInvalidCommand() {
        String userInput = "view";
        String output = CollateTui.handleUserInput(logic, userInput);

        assertEquals("Invalid command.", output);

        userInput = "view author1";
        output = CollateTui.handleUserInput(logic, userInput);

        assertEquals("Invalid command.", output);
    }

    @Test
    public void testExitCommand() {
        String userInput = "exit";
        String output = CollateTui.handleUserInput(logic, userInput);

        assertEquals("Goodbye!", output);
    }
}
