package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import main.java.backend.Command;
import main.java.backend.CommandParser;

import org.junit.Before;
import org.junit.Test;

public class TestCommandParserAndCommand {

    private CommandParser commandParser;

    @Before
    public void init() {
        commandParser = new CommandParser();
    }

    @Test
    public void testCollateCommand() {
        Command command;

        command = commandParser.parse("collate from C:/");
        assertEquals(Command.Type.COLLATE, command.getCommandType());
        assertEquals("C:\\", command.getDirectory());
        assertFalse(command.willScanCurrentDirOnly());
        assertEquals(0, command.getFileTypes().size());

        command = commandParser.parse("collate from        C:/Windows");
        assertEquals(Command.Type.COLLATE, command.getCommandType());
        assertEquals("C:\\Windows", command.getDirectory());
        assertFalse(command.willScanCurrentDirOnly());
        assertEquals(0, command.getFileTypes().size());

        command = commandParser.parse("collate from C:/Users\\Default");
        assertEquals(Command.Type.COLLATE, command.getCommandType());
        assertEquals("C:\\Users\\Default", command.getDirectory());
        assertFalse(command.willScanCurrentDirOnly());
        assertEquals(0, command.getFileTypes().size());
        
        command = commandParser.parse("collate from \"C:/Program Files");
        assertEquals(Command.Type.COLLATE, command.getCommandType());
        assertEquals("C:\\Program Files", command.getDirectory());
        assertFalse(command.willScanCurrentDirOnly());
        assertEquals(0, command.getFileTypes().size());

        command = commandParser.parse("collate from C:/ only");
        assertEquals(Command.Type.COLLATE, command.getCommandType());
        assertEquals("C:\\", command.getDirectory());
        assertTrue(command.willScanCurrentDirOnly());
        assertEquals(0, command.getFileTypes().size());

        command = commandParser.parse("collate from C:/ include java, css");
        assertEquals(Command.Type.COLLATE, command.getCommandType());
        assertEquals("C:\\", command.getDirectory());
        assertFalse(command.willScanCurrentDirOnly());
        assertEquals(2, command.getFileTypes().size());
        assertEquals("java", command.getFileTypes().get(0));
        assertEquals("css", command.getFileTypes().get(1));

        command = commandParser.parse("collate include css, FXML from C:/");
        assertEquals(Command.Type.COLLATE, command.getCommandType());
        assertEquals("C:\\", command.getDirectory());
        assertFalse(command.willScanCurrentDirOnly());
        assertEquals(2, command.getFileTypes().size());
        assertEquals("css", command.getFileTypes().get(0));
        assertEquals("fxml", command.getFileTypes().get(1));

        command = commandParser.parse("collate include css from \"C:/Program Files\" only");
        assertEquals(Command.Type.COLLATE, command.getCommandType());
        assertEquals("C:\\Program Files", command.getDirectory());
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
