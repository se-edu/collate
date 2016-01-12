package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import main.java.backend.Command;
import main.java.backend.CommandParser;

import org.junit.Before;
import org.junit.Test;

public class TestCommandParserAndCommand {

    private static final String ROOT_DIR = System.getProperty("user.dir");
    private static final String TEST_RESOURCES_DIR = "/src/test/testFiles";
    private static final String FULL_TEST_RES_DIR = ROOT_DIR + TEST_RESOURCES_DIR;

    private CommandParser commandParser;

    @Before
    public void init() {
        commandParser = new CommandParser();
    }

    @Test
    public void testCollateCommand() {
        Command command;

        // Normal collate
        command = commandParser.parse("collate from /");
        assertEquals(Command.Type.COLLATE, command.getCommandType());
        assertEquals("/", command.getDirectory());
        assertFalse(command.willScanCurrentDirOnly());
        assertEquals(0, command.getFileTypes().size());

        // Collate with extra spaces
        command = commandParser.parse("collate from        /");
        assertEquals(Command.Type.COLLATE, command.getCommandType());
        assertEquals("/", command.getDirectory());
        assertFalse(command.willScanCurrentDirOnly());
        assertEquals(0, command.getFileTypes().size());

        // Collate folder with spaces in name
        command = commandParser.parse("collate from \"" + FULL_TEST_RES_DIR + "/subfolder with spaces\"");
        assertEquals(Command.Type.COLLATE, command.getCommandType());
        assertEquals(FULL_TEST_RES_DIR + "/subfolder with spaces", command.getDirectory());
        assertFalse(command.willScanCurrentDirOnly());
        assertEquals(0, command.getFileTypes().size());

        // Collate folder without sub-folders
        command = commandParser.parse("collate from \"" + FULL_TEST_RES_DIR + "\" only");
        assertEquals(Command.Type.COLLATE, command.getCommandType());
        assertEquals(FULL_TEST_RES_DIR, command.getDirectory());
        assertTrue(command.willScanCurrentDirOnly());
        assertEquals(0, command.getFileTypes().size());

        // Collate only certain types of files ('include' keyword after 'from' keyword)
        command = commandParser.parse("collate from \"" + FULL_TEST_RES_DIR + "\" include java, css");
        assertEquals(Command.Type.COLLATE, command.getCommandType());
        assertEquals(FULL_TEST_RES_DIR, command.getDirectory());
        assertFalse(command.willScanCurrentDirOnly());
        assertEquals(2, command.getFileTypes().size());
        assertEquals("java", command.getFileTypes().get(0));
        assertEquals("css", command.getFileTypes().get(1));

        // Collate only certain types of files ('include' keyword before 'from' keyword)
        command = commandParser.parse("collate include css, FXML from \"" + FULL_TEST_RES_DIR + "\"");
        assertEquals(Command.Type.COLLATE, command.getCommandType());
        assertEquals(FULL_TEST_RES_DIR, command.getDirectory());
        assertFalse(command.willScanCurrentDirOnly());
        assertEquals(2, command.getFileTypes().size());
        assertEquals("css", command.getFileTypes().get(0));
        assertEquals("fxml", command.getFileTypes().get(1));

        // Collate only certain file types from a folder with spaces in its name (no sub-folders)
        command = commandParser.parse("collate include css from \"" + FULL_TEST_RES_DIR + "/subfolder with spaces\" only");
        assertEquals(Command.Type.COLLATE, command.getCommandType());
        assertEquals(FULL_TEST_RES_DIR + "/subfolder with spaces", command.getDirectory());
        assertTrue(command.willScanCurrentDirOnly());
        assertEquals(1, command.getFileTypes().size());
    }

    @Test
    public void testViewCommand() {
        Command command;

        String authorName = "A1234567Z";
        command = commandParser.parse("view " + authorName);
        assertEquals(Command.Type.VIEW, command.getCommandType());
        assertEquals(authorName, command.getAuthorName());
    }

    @Test
    public void testSummaryCommand() {
        Command command;

        command = commandParser.parse("summary");
        assertEquals(Command.Type.SUMMARY, command.getCommandType());

        command = commandParser.parse("summary     C:/");
        assertEquals(Command.Type.SUMMARY, command.getCommandType());
    }

    @Test
    public void testInvalidCommand() {
        Command command;

        command = commandParser.parse("collate");
        assertEquals(Command.Type.INVALID, command.getCommandType());

        command = commandParser.parse("collate from");
        assertEquals(Command.Type.INVALID, command.getCommandType());

        command = commandParser.parse("collate include");
        assertEquals(Command.Type.INVALID, command.getCommandType());

        command = commandParser.parse("collate include from");
        assertEquals(Command.Type.INVALID, command.getCommandType());
        
        command = commandParser.parse("collate from include only");
        assertEquals(Command.Type.INVALID, command.getCommandType());
        
        command = commandParser.parse("collate from C:/ include only");
        assertEquals(Command.Type.INVALID, command.getCommandType());
        
        command = commandParser.parse("colate C:/");
        assertEquals(Command.Type.INVALID, command.getCommandType());

        command = commandParser.parse("view");
        assertEquals(Command.Type.INVALID, command.getCommandType());

        command = commandParser.parse("viewA1234567Z");
        assertEquals(Command.Type.INVALID, command.getCommandType());
    }

}
