package test;

import static org.junit.Assert.assertEquals;
import main.java.backend.Logic;
import main.java.tui.Collate;

import org.junit.Before;
import org.junit.Test;

public class TestCollate {
    private static final String ROOT_DIR = System.getProperty("user.dir");
    private static final String TEST_RESOURCES_DIR = "\\src\\test\\testFiles\\";

    private Logic logic;

    @Before
    public void init() {
        logic = new Logic();
    }

    @Test
    public void testCollateCommand() {
        String userInput = "collate from " + ROOT_DIR + TEST_RESOURCES_DIR;
        String output = Collate.handleUserInput(logic, userInput);

        assertEquals("Author                LOC      Proportion (%)\n"
                     + "---------------------------------------------\n"
                     + "author2                 4               30.77\n"
                     + "author1                 8               61.54\n"
                     + "Long autho...           1                7.69\n",
                     output);
    }

    @Test
    public void testSummaryCommand() {
        testCollateCommand();

        String userInput = "summary";
        String output = Collate.handleUserInput(logic, userInput);

        assertEquals("Author                LOC      Proportion (%)\n"
                     + "---------------------------------------------\n"
                     + "author2                 4               30.77\n"
                     + "author1                 8               61.54\n"
                     + "Long autho...           1                7.69\n",
                     output);
    }

    @Test
    public void testViewCommand() {
        testCollateCommand();

        String userInput = "view author2";
        String output = Collate.handleUserInput(logic, userInput);

        assertEquals("File path                                           LOC      Proportion (%)\n"
                     + "---------------------------------------------------------------------------\n"
                     + "subfolder\\testFile1.txt                               4              100.00\n",
                     output);

        userInput = "view Long author name";
        output = Collate.handleUserInput(logic, userInput);

        assertEquals("File path                                           LOC      Proportion (%)\n"
                     + "---------------------------------------------------------------------------\n"
                     + "...ceedingly_excessively_long_file_name.txt           1              100.00\n",
                     output);
    }

    @Test
    public void testInvalidCommand() {
        String userInput = "view";
        String output = Collate.handleUserInput(logic, userInput);

        assertEquals("Invalid command.", output);

        userInput = "view author1";
        output = Collate.handleUserInput(logic, userInput);

        assertEquals("Invalid command.", output);
    }

    @Test
    public void testExitCommand() {
        String userInput = "exit";
        String output = Collate.handleUserInput(logic, userInput);

        assertEquals("Goodbye!", output);
    }
}
