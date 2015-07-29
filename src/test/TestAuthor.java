package test;

import static org.junit.Assert.assertEquals;
import main.java.data.Author;
import main.java.data.CodeSnippet;
import main.java.data.SourceFile;

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
                                                   new SourceFile("test1.txt",
                                                                  "txt"));
        codeSnippet1.addLine("line1");
        codeSnippet1.addLine("line2");
        codeSnippet1.addLine("line3");

        CodeSnippet codeSnippet2 = new CodeSnippet(author1,
                                                   new SourceFile("test2.txt",
                                                                  "txt"));
        codeSnippet2.addLine("line1");
        codeSnippet2.addLine("line2");
        codeSnippet2.addLine("line3");
        codeSnippet2.addLine("line4");
        codeSnippet2.addLine("line5");

        CodeSnippet codeSnippet3 = new CodeSnippet(author2,
                                                   new SourceFile("test2.txt",
                                                                  "txt"));
        codeSnippet3.addLine("line4");
        codeSnippet3.addLine("line5");
    }

    @Test
    public void testName() {
        assertEquals(AUTHOR_NAME_1, author1.getName());
        author1.setName(AUTHOR_NAME_2);
        assertEquals(AUTHOR_NAME_2, author1.getName());
    }

    @Test
    public void testLinesOfCode() {
        assertEquals(8, author1.getLinesOfCode());
        assertEquals(2, author2.getLinesOfCode());
    }

    @Test
    public void testProportion() {
        assertEquals(80, author1.getProportion(), 0);
        assertEquals(20, author2.getProportion(), 0);
    }

    @Test
    public void testCodeSnippets() {
        assertEquals(2, author1.getCodeSnippets().size());
        assertEquals(1, author2.getCodeSnippets().size());
    }
}
