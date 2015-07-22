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
        assertEquals(command.getCommandType(), Command.Type.COLLATE);
        assertEquals(command.getDirectory(), "C:\\");
        assertFalse(command.willScanCurrentDirOnly());
        assertEquals(command.getFileTypes().size(), 0);

        command = commandParser.parse("collate from C:/Windows");
        assertEquals(command.getCommandType(), Command.Type.COLLATE);
        assertEquals(command.getDirectory(), "C:\\Windows");
        assertFalse(command.willScanCurrentDirOnly());
        assertEquals(command.getFileTypes().size(), 0);

        command = commandParser.parse("collate from C:/Users\\Default");
        assertEquals(command.getCommandType(), Command.Type.COLLATE);
        assertEquals(command.getDirectory(), "C:\\Users\\Default");
        assertFalse(command.willScanCurrentDirOnly());
        assertEquals(command.getFileTypes().size(), 0);
        
        command = commandParser.parse("collate from \"C:/Program Files");
        assertEquals(command.getCommandType(), Command.Type.COLLATE);
        assertEquals(command.getDirectory(), "C:\\Program Files");
        assertFalse(command.willScanCurrentDirOnly());
        assertEquals(command.getFileTypes().size(), 0);

        command = commandParser.parse("collate from C:/ only");
        assertEquals(command.getCommandType(), Command.Type.COLLATE);
        assertEquals(command.getDirectory(), "C:\\");
        assertTrue(command.willScanCurrentDirOnly());
        assertEquals(command.getFileTypes().size(), 0);

        command = commandParser.parse("collate from C:/ include java, css");
        assertEquals(command.getCommandType(), Command.Type.COLLATE);
        assertEquals(command.getDirectory(), "C:\\");
        assertFalse(command.willScanCurrentDirOnly());
        assertEquals(command.getFileTypes().size(), 2);

        command = commandParser.parse("collate include css from C:/");
        assertEquals(command.getCommandType(), Command.Type.COLLATE);
        assertEquals(command.getDirectory(), "C:\\");
        assertFalse(command.willScanCurrentDirOnly());
        assertEquals(command.getFileTypes().size(), 1);

        command = commandParser.parse("collate include css from \"C:/Program Files\" only");
        assertEquals(command.getCommandType(), Command.Type.COLLATE);
        assertEquals(command.getDirectory(), "C:\\Program Files");
        assertTrue(command.willScanCurrentDirOnly());
        assertEquals(command.getFileTypes().size(), 1);
    }

    @Test
    public void testViewCommand() {
        Command command;

        String authorName = "A1234567Z";
        command = commandParser.parse("view " + authorName);
        assertEquals(command.getCommandType(), Command.Type.VIEW);
        assertEquals(command.getAuthorName(), authorName);
    }

    @Test
    public void testSummaryCommand() {
        Command command;

        command = commandParser.parse("summary");
        assertEquals(command.getCommandType(), Command.Type.SUMMARY);

        command = commandParser.parse("summary     C:/");
        assertEquals(command.getCommandType(), Command.Type.SUMMARY);
    }

    @Test
    public void testInvalidCommand() {
        Command command;

        command = commandParser.parse("collate");
        assertEquals(command.getCommandType(), Command.Type.INVALID);

        command = commandParser.parse("collate from");
        assertEquals(command.getCommandType(), Command.Type.INVALID);

        command = commandParser.parse("collate include");
        assertEquals(command.getCommandType(), Command.Type.INVALID);

        command = commandParser.parse("collate include from");
        assertEquals(command.getCommandType(), Command.Type.INVALID);
        
        command = commandParser.parse("collate from include only");
        assertEquals(command.getCommandType(), Command.Type.INVALID);
        
        command = commandParser.parse("collate from C:/ include only");
        assertEquals(command.getCommandType(), Command.Type.INVALID);
        
        command = commandParser.parse("colate C:/");
        assertEquals(command.getCommandType(), Command.Type.INVALID);

        command = commandParser.parse("view");
        assertEquals(command.getCommandType(), Command.Type.INVALID);

        command = commandParser.parse("viewA1234567Z");
        assertEquals(command.getCommandType(), Command.Type.INVALID);
    }

}
