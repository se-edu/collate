package test;

import static org.junit.Assert.assertEquals;
import main.java.backend.Author;
import main.java.backend.CodeSnippet;
import main.java.backend.SourceFile;

import org.junit.Before;
import org.junit.Test;

public class TestAuthor {
    private static final String AUTHOR_NAME_1 = "A1234567Z";
    private static final String AUTHOR_NAME_2 = "Z7654321A";
    private Author author;

    @Before
    public void init() {
        author = new Author(AUTHOR_NAME_1);
        CodeSnippet.resetTotalLines();
        CodeSnippet codeSnippet = new CodeSnippet(author,
                                                  new SourceFile("test.txt"),
                                                  "txt");
        codeSnippet.addLine("line1");
        codeSnippet.addLine("line2");
        codeSnippet.addLine("line3");
    }

    @Test
    public void testName() {
        assertEquals(author.getName(), AUTHOR_NAME_1);
        author.setName(AUTHOR_NAME_2);
        assertEquals(author.getName(), AUTHOR_NAME_2);
    }

    @Test
    public void testLinesOfCode() {
        assertEquals(author.getLinesOfCode(), 3);
    }

    @Test
    public void testProportion() {
        assertEquals(author.getProportion(), 100, 0);
    }

    @Test
    public void testCodeSnippets() {
        assertEquals(author.getCodeSnippets().size(), 1);
    }
}
