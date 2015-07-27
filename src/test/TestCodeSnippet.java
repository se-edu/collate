package test;

import static org.junit.Assert.assertEquals;
import main.java.data.Author;
import main.java.data.CodeSnippet;
import main.java.data.SourceFile;

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
        assertEquals(author, codeSnippet.getAuthor());
    }
    
    @Test
    public void testGetNumLines() {
        assertEquals(5, codeSnippet.getNumLines());
    }
    
    @Test
    public void testGetFile() {
        assertEquals(file, codeSnippet.getFile());
    }
    
    @Test
    public void testTotalLines() {
        assertEquals(5, CodeSnippet.getTotalLines());
        CodeSnippet.resetTotalLines();
        assertEquals(0, CodeSnippet.getTotalLines());
    }   

    @Test
    public void testToString() {
        String expectedResult = "public class Test {\n" +
                                "\tpublic Test() {\n" +
                                "\t\tSystem.out.println(\"hello\");\n" + 
                                "\t}\n" + "}";
        assertEquals(expectedResult, codeSnippet.toString());
    }

}
