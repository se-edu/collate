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
        assertEquals(sourceFile.getFileLocation(), FILE_LOCATION);
    }

    @Test
    public void testNumLines() {
        assertEquals(sourceFile.getNumLines(), 0);
        sourceFile.addNumLines(5);
        assertEquals(sourceFile.getNumLines(), 5);
    }
}
