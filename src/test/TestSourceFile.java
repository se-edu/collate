package test;

import static org.junit.Assert.assertEquals;
import main.java.backend.SourceFile;

import org.junit.Before;
import org.junit.Test;

public class TestSourceFile {
    private static final String FILE_LOCATION = "test/test.txt";
    private SourceFile sourceFile;

    @Before
    public void init() {
        sourceFile = new SourceFile(FILE_LOCATION);
    }

    @Test
    public void testGetFileLocation() {
        assertEquals(FILE_LOCATION, sourceFile.getRelativeFilePath());
    }

    @Test
    public void testNumLines() {
        assertEquals(0, sourceFile.getNumLines());
        sourceFile.addNumLines(5);
        assertEquals(5, sourceFile.getNumLines());
    }
}
