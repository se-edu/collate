package main.java.backend;

import java.util.ArrayList;

public class CodeSnippet {
    public static int totalLines = 0;

    private static final String MARKDOWN_H6 = "###### %s\n";
    private static final String MARKDOWN_CODE_LANGUAGE_START = "``` %s\n";
    private static final String MARKDOWN_CODE_LANGUAGE_END = "```\n";
    private static final String MARKDOWN_ONE_LINE = "%s\n";

    private Author author;
    private SourceFile file;
    private ArrayList<String> lines;
    private String language;

    public CodeSnippet(Author author, SourceFile file, String language) {
        this.author = author;
        this.file = file;
        this.language = language;
        this.lines = new ArrayList<String>();
        author.addCodeSnippet(this);
    }

    public void addLine(String line) {
        lines.add(line);
        totalLines++;
        file.updateNumLines(1);
    }

    public Author getAuthor() {
        return author;
    }

    public int getNumLines() {
        return lines.size();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format(MARKDOWN_H6, file.getFileLocation()));
        builder.append(String.format(MARKDOWN_CODE_LANGUAGE_START, language));
        for (String line : lines) {
            builder.append(String.format(MARKDOWN_ONE_LINE, line));
        }
        builder.append(MARKDOWN_CODE_LANGUAGE_END);
        return builder.toString();
    }
}
