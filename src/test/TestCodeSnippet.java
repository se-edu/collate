package test;

import static org.junit.Assert.assertEquals;
import main.java.backend.Author;
import main.java.backend.CodeSnippet;

import org.junit.Before;
import org.junit.Test;

public class TestCodeSnippet {

    private CodeSnippet codeSnippet;

    @Before
    public void init() {
        Author author = new Author("John Doe");
        codeSnippet = new CodeSnippet(author, "test.java", "java");
        codeSnippet.addLine("public class Test {");
        codeSnippet.addLine("");
        codeSnippet.addLine("\tpublic Test() {");
        codeSnippet.addLine("\t\tSystem.out.println(\"hello\");");
        codeSnippet.addLine("\t}");
        codeSnippet.addLine("");
        codeSnippet.addLine("}");
    }

    @Test
    public void testToString() {
        String expectedResult = "###### test.java\n" + "```java\n"
                                + "public class Test {\n" + "\n"
                                + "\tpublic Test() {\n"
                                + "\t\tSystem.out.println(\"hello\");\n"
                                + "\t}\n" + "\n" + "}\n" + "```";
        assertEquals(expectedResult, codeSnippet.toString());
    }

}
