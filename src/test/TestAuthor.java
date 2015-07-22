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
    private Author author1;
    private Author author2;

    @Before
    public void init() {
        author1 = new Author(AUTHOR_NAME_1);
        author2 = new Author(AUTHOR_NAME_2);
        CodeSnippet.resetTotalLines();
        CodeSnippet codeSnippet1 = new CodeSnippet(author1,
                                                   new SourceFile("test1.txt"),
                                                   "txt");
        codeSnippet1.addLine("line1");
        codeSnippet1.addLine("line2");
        codeSnippet1.addLine("line3");

        CodeSnippet codeSnippet2 = new CodeSnippet(author1,
                                                   new SourceFile("test2.txt"),
                                                   "txt");
        codeSnippet2.addLine("line1");
        codeSnippet2.addLine("line2");
        codeSnippet2.addLine("line3");
        codeSnippet2.addLine("line4");
        codeSnippet2.addLine("line5");

        CodeSnippet codeSnippet3 = new CodeSnippet(author2,
                                                   new SourceFile("test2.txt"),
                                                   "txt");
        codeSnippet3.addLine("line4");
        codeSnippet3.addLine("line5");
    }

    @Test
    public void testName() {
        assertEquals(author1.getName(), AUTHOR_NAME_1);
        author1.setName(AUTHOR_NAME_2);
        assertEquals(author1.getName(), AUTHOR_NAME_2);
    }

    @Test
    public void testLinesOfCode() {
        assertEquals(author1.getLinesOfCode(), 8);
        assertEquals(author2.getLinesOfCode(), 2);
    }

    @Test
    public void testProportion() {
        assertEquals(author1.getProportion(), 80, 0);
        assertEquals(author2.getProportion(), 20, 0);
    }

    @Test
    public void testCodeSnippets() {
        assertEquals(author1.getCodeSnippets().size(), 2);
        assertEquals(author2.getCodeSnippets().size(), 1);
    }
}
