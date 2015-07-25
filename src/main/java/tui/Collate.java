package main.java.tui;

import java.util.HashMap;
import java.util.Scanner;

import main.java.backend.Author;
import main.java.backend.Logic;
import main.java.backend.SourceFile;

public class Collate {

    private static final String MESSAGE_INVALID_COMMAND = "Invalid command.";
    private static final String MESSAGE_WELCOME = "Welcome to Collate!";
    private static final String MESSAGE_COMMAND_PROMPT = "Command: ";
    private static final String MESSAGE_EXIT = "Goodbye!";

    private static final String SUMMARY_HEADER_AUTHOR = "Author";
    private static final String SUMMARY_HEADER_LOC = "LOC";
    private static final String SUMMARY_HEADER_PROPORTION = "Proportion (%)";
    private static final String SUMMARY_HEADER_DIVIDER = "---------------------------------------------";

    private static final String AUTHOR_STATS_HEADER_FILE_PATH = "File path";
    private static final String AUTHOR_STATS_HEADER_LOC = "LOC";
    private static final String AUTHOR_STATS_HEADER_PROPORTION = "Proportion (%)";
    private static final String AUTHOR_STATS_HEADER_DIVIDER = "---------------------------------------------------------------------------";

    private static final String FORMAT_SUMMARY_HEADER = "%-15s%10s%20s%n";
    private static final String FORMAT_SUMMARY_ITEM = "%-15s%10d%20s%n";
    private static final String FORMAT_AUTHOR_STATS_HEADER = "%-45s%10s%20s%n";
    private static final String FORMAT_AUTHOR_STATS_ITEM = "%-45s%10d%20.2f%n";

    private static final String STRING_TRUNCATED_FRONT_FORMAT = "...%s";
    private static final String STRING_TRUNCATED_BACK_FORMAT = "%s...";

    private static final int MAX_AUTHOR_NAME_LENGTH = 10;
    private static final int MAX_FILENAME_LENGTH = 40;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Logic logic = new Logic();
        boolean isTimeToExit = false;

        System.out.println(MESSAGE_WELCOME);

        while (!isTimeToExit) {
            System.out.print(MESSAGE_COMMAND_PROMPT);
            String userInput = scanner.nextLine();
            System.out.println();

            switch (logic.executeCommand(userInput)) {
                case COLLATE :
                case SUMMARY :
                    showSummary(logic);
                    break;
                case VIEW :
                    handleViewCommand(logic);
                    break;
                case EXIT :
                    isTimeToExit = true;
                    System.out.println(MESSAGE_EXIT);
                    break;
                case INVALID :
                default :
                    showInvalidCommand();
                    break;
            }

            System.out.println();
        }

        scanner.close();
    }

    private static void showSummary(Logic logic) {
        System.out.format(FORMAT_SUMMARY_HEADER,
                          SUMMARY_HEADER_AUTHOR,
                          SUMMARY_HEADER_LOC,
                          SUMMARY_HEADER_PROPORTION);
        System.out.println(SUMMARY_HEADER_DIVIDER);

        for (Author author : logic.getAuthors()) {
            System.out.format(FORMAT_SUMMARY_ITEM,
                              generateTruncatedPhrase(author.getName(),
                                                      MAX_AUTHOR_NAME_LENGTH,
                                                      false),
                              author.getLinesOfCode(),
                              author.getProportion());
        }
    }

    private static void handleViewCommand(Logic logic) {
        HashMap<SourceFile, Integer> authorStats = logic.getTargetAuthorStatistics();
        if (!authorStats.isEmpty()) {
            showAuthorStats(authorStats);
        }
    }

    private static void showAuthorStats(HashMap<SourceFile, Integer> targetAuthorStatistics) {
        System.out.format(FORMAT_AUTHOR_STATS_HEADER,
                          AUTHOR_STATS_HEADER_FILE_PATH,
                          AUTHOR_STATS_HEADER_LOC,
                          AUTHOR_STATS_HEADER_PROPORTION);
        System.out.println(AUTHOR_STATS_HEADER_DIVIDER);

        for (SourceFile file : targetAuthorStatistics.keySet()) {
            int numLinesOfCode = targetAuthorStatistics.get(file);
            System.out.format(FORMAT_AUTHOR_STATS_ITEM,
                              generateTruncatedPhrase(file.getRelativeFilePath(),
                                                      MAX_FILENAME_LENGTH,
                                                      true),
                              numLinesOfCode,
                              (double) numLinesOfCode / file.getNumLines() *
                                      100);
        }
    }

    private static String generateTruncatedPhrase(String phrase,
                                                  int maxLength,
                                                  boolean truncateFromFront) {
        if (phrase.length() >= maxLength) {
            if (truncateFromFront) {
                return String.format(STRING_TRUNCATED_FRONT_FORMAT,
                                     phrase.substring(phrase.length() -
                                                      maxLength));
            } else {
                return String.format(STRING_TRUNCATED_BACK_FORMAT,
                                     phrase.substring(0, maxLength));
            }
        }
        return phrase;
    }

    private static void showInvalidCommand() {
        System.out.println(MESSAGE_INVALID_COMMAND);
    }
}
