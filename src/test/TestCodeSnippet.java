package test;

import static org.junit.Assert.assertEquals;
import main.java.backend.Author;
import main.java.backend.CodeSnippet;
import main.java.backend.SourceFile;

import org.junit.Before;
import org.junit.Test;

public class TestCodeSnippet {

    private CodeSnippet codeSnippet;
    private Author author;
    private SourceFile file;

    @Before
    public void init() {
        author = new Author("John Doe");
        file = new SourceFile("test.java");
        CodeSnippet.resetTotalLines();
        codeSnippet = new CodeSnippet(author, file, "java");
        /*
         * public class Test {
         *     public Test() {
         *         System.out.println("hello");
         *     }
         * }
         */
        codeSnippet.addLine("public class Test {");
        codeSnippet.addLine("\tpublic Test() {");
        codeSnippet.addLine("\t\tSystem.out.println(\"hello\");");
        codeSnippet.addLine("\t}");
        codeSnippet.addLine("}");
    }
    
    @Test
    public void testGetAuthor() {
        assertEquals(codeSnippet.getAuthor(), author);
    }
    
    @Test
    public void testGetNumLines() {
        assertEquals(codeSnippet.getNumLines(), 5);
    }
    
    @Test
    public void testGetFile() {
        assertEquals(codeSnippet.getFile(), file);
    }
    
    @Test
    public void testTotalLines() {
        assertEquals(CodeSnippet.getTotalLines(), 5);
        CodeSnippet.resetTotalLines();
        assertEquals(CodeSnippet.getTotalLines(), 0);
    }   

    @Test
    public void testToString() {
        String expectedResult = "###### test.java\n" + 
                                "``` java\n" +
                                "public class Test {\n" +
                                "\tpublic Test() {\n" +
                                "\t\tSystem.out.println(\"hello\");\n" + 
                                "\t}\n" + "}\n" + "```\n";
        assertEquals(expectedResult, codeSnippet.toString());
    }

}
