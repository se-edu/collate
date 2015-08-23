package main.java.tui;

import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

import main.java.backend.Logic;
import main.java.data.Author;
import main.java.data.SourceFile;

/**
 * This class handles the text ui of Collate and is ran from the command line.
 * 
 * @author Sebastian Quek
 *
 */
public class CollateTui {

    // ================================================================
    // Message prompts
    // ================================================================

    private static final String MESSAGE_INVALID_COMMAND = "Invalid command.";
    private static final String MESSAGE_WELCOME = "Welcome to Collate!";
    private static final String MESSAGE_COMMAND_PROMPT = "Command: ";
    private static final String MESSAGE_EXIT = "Goodbye!";


    // ================================================================
    // Summary view table headers
    // ================================================================

    private static final String SUMMARY_HEADER_AUTHOR = "Author";
    private static final String SUMMARY_HEADER_LOC = "LOC";
    private static final String SUMMARY_HEADER_PROPORTION = "Proportion (%)";
    private static final String SUMMARY_HEADER_DIVIDER =
        "---------------------------------------------\n";
    private static final String FORMAT_SUMMARY_HEADER = "%-15s%10s%20s\n";
    private static final String FORMAT_SUMMARY_ITEM = "%-15s%10d%20s\n";


    // ================================================================
    // Author statistics view table headers
    // ================================================================

    private static final String AUTHOR_STATS_HEADER_FILE_PATH = "File path";
    private static final String AUTHOR_STATS_HEADER_LOC = "LOC";
    private static final String AUTHOR_STATS_HEADER_PROPORTION =
        "Proportion (%)";
    private static final String AUTHOR_STATS_HEADER_DIVIDER =
        "---------------------------------------------------------------------------\n";
    private static final String FORMAT_AUTHOR_STATS_HEADER = "%-45s%10s%20s\n";
    private static final String FORMAT_AUTHOR_STATS_ITEM = "%-45s%10d%20.2f\n";


    // ================================================================
    // Other constants
    // ================================================================

    private static final String STRING_TRUNCATED_FRONT_FORMAT = "...%s";
    private static final String STRING_TRUNCATED_BACK_FORMAT = "%s...";

    private static final int MAX_AUTHOR_NAME_LENGTH = 10;
    private static final int MAX_FILENAME_LENGTH = 40;

    private static boolean isTimeToExit = false;

    /**
     * The main method that is executed when this class is called.
     * 
     * @param args
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        PrintStream output = System.out;
        Logic logic = new Logic();

        output.println(MESSAGE_WELCOME);

        while (!isTimeToExit) {
            output.print(MESSAGE_COMMAND_PROMPT);
            String userInput = input.nextLine();
            output.println();
            output.println(handleUserInput(logic, userInput));
        }

        input.close();
    }

    public static String handleUserInput(Logic logic, String userInput) {
        switch (logic.executeCommand(userInput)) {
            case COLLATE :
            case SUMMARY :
                return handleSummaryCommand(logic);
            case VIEW :
                return handleViewCommand(logic);
            case EXIT :
                isTimeToExit = true;
                return MESSAGE_EXIT;
            case INVALID :
            default :
                return handleInvalidCommand();
        }
    }


    // ================================================================
    // Methods for summary command
    // ================================================================

    private static String handleSummaryCommand(Logic logic) {
        return getSummaryHeaders() + getSummaryRows(logic.getAuthors());
    }

    private static String getSummaryHeaders() {
        return String.format(FORMAT_SUMMARY_HEADER,
                             SUMMARY_HEADER_AUTHOR,
                             SUMMARY_HEADER_LOC,
                             SUMMARY_HEADER_PROPORTION) +
               SUMMARY_HEADER_DIVIDER;
    }

    private static String getSummaryRows(Collection<Author> authors) {
        StringBuilder builder = new StringBuilder();
        for (Author author : authors) {
            builder.append(String.format(FORMAT_SUMMARY_ITEM,
                                         generateTruncatedPhrase(author.getName(),
                                                                 MAX_AUTHOR_NAME_LENGTH,
                                                                 false),
                                         author.getLinesOfCode(),
                                         author.getProportion()));
        }
        return builder.toString();
    }


    // ================================================================
    // Methods for view command
    // ================================================================

    private static String handleViewCommand(Logic logic) {
        HashMap<SourceFile, Integer> authorStats =
            logic.getTargetAuthorStatistics();
        if (!authorStats.isEmpty()) {
            return showAuthorStatsHeaders() + showAuthorStatsRows(authorStats);
        } else {
            return handleInvalidCommand();
        }
    }

    private static String showAuthorStatsHeaders() {
        return String.format(FORMAT_AUTHOR_STATS_HEADER,
                             AUTHOR_STATS_HEADER_FILE_PATH,
                             AUTHOR_STATS_HEADER_LOC,
                             AUTHOR_STATS_HEADER_PROPORTION) +
               AUTHOR_STATS_HEADER_DIVIDER;
    }

    private static String showAuthorStatsRows(HashMap<SourceFile, Integer> targetAuthorStatistics) {
        StringBuilder builder = new StringBuilder();
        for (SourceFile file : targetAuthorStatistics.keySet()) {
            int authorLinesOfCode = targetAuthorStatistics.get(file);
            builder.append(String.format(FORMAT_AUTHOR_STATS_ITEM,
                                         generateTruncatedPhrase(file.getRelativeFilePath(),
                                                                 MAX_FILENAME_LENGTH,
                                                                 true),
                                         authorLinesOfCode,
                                         generatePercentage(authorLinesOfCode,
                                                            file.getNumLines())));
        }
        return builder.toString();
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

    private static double generatePercentage(int authorLinesOfCode,
                                             int fileLinesOfCode) {
        return (double) authorLinesOfCode / fileLinesOfCode * 100;
    }


    // ================================================================
    // Methods for invalid command
    // ================================================================

    private static String handleInvalidCommand() {
        return MESSAGE_INVALID_COMMAND;
    }
}
