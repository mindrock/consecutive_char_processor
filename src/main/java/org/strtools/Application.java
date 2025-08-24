package org.strtools;

import org.strtools.api.ConsecutiveCharRemoverStrategy;
import org.strtools.api.StringProcessingStrategy;

import java.util.List;
import java.util.Scanner;

/**
 * A command-line interface for demonstrating the string processing functionality.
 *
 * <p>This class provides an interactive console application that allows users to:
 * <ul>
 *   <li>Enter strings to process</li>
 *   <li>Choose between removal and replacement strategies</li>
 *   <li>View the processing results</li>
 * </ul>1
 *
 * @since 1.0.0
 */
public final class Application {

    /**
     * Private constructor to prevent instantiation.
     */
    private Application() {
        throw new AssertionError("Main class should not be instantiated");
    }

    /**
     * Main method that starts the interactive console application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        printWelcomeMessage();

        try (Scanner scanner = new Scanner(System.in)) {
            boolean continueProcessing = true;

            while (continueProcessing) {
                // Get input string from user
                String input = getInputString(scanner);

                // Choose processing strategy
                StringProcessingStrategy strategy = chooseStrategy(scanner);

                // Process and display result
                String result = StringProcessor.process(input, strategy);
                displayResult(input, result, strategy);

                // Ask to continue
                continueProcessing = askToContinue(scanner);
            }
        }

        System.out.println("\nThank you for using Consecutive Character Processor!");
    }

    /**
     * Prints a welcome message and brief introduction.
     */
    private static void printWelcomeMessage() {
        System.out.println("==============================================");
        System.out.println("      Consecutive Character Conversions Processor   ");
        System.out.println("==============================================");
        System.out.println("This tool processes strings by handling sequences");
        System.out.println("of 3 or more consecutive identical characters.");
        System.out.println("Only lowercase letters (a-z) are supported.");
        System.out.println();
    }

    /**
     * Prompts the user to enter a string for processing.
     *
     * @param scanner the scanner to read user input
     * @return the input string
     */
    private static String getInputString(Scanner scanner) {
        System.out.print("Enter the string to process: ");
        return scanner.nextLine().trim();
    }

    /**
     * Allows the user to choose between available processing strategies.
     *
     * @param scanner the scanner to read user input
     * @return the selected processing strategy
     */
    private static StringProcessingStrategy chooseStrategy(Scanner scanner) {
        System.out.println("\nChoose processing strategy:");
        System.out.println("1 - Remove sequences of 3+ consecutive characters");
        System.out.println("2 - Replace sequences of 3+ consecutive characters with previous letter");
        System.out.print("Enter your choice (1 or 2): ");

        while (true) {
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    return StringProcessor.getRemoveStrategy();
                case "2":
                    return StringProcessor.getReplaceStrategy();
                default:
                    System.out.print("Invalid choice. Please enter 1 or 2: ");
            }
        }
    }

    /**
     * Displays the processing result to the user.
     *
     * @param input    the original input string
     * @param result   the processed result
     * @param strategy the strategy used for processing
     */
    private static void displayResult(String input, String result, StringProcessingStrategy strategy) {
        System.out.println("\nProcessing Results:");
        System.out.println("-------------------");
        System.out.println("Original string: " + input);
        System.out.println("Strategy used:   " + strategy.getName());
        System.out.println("Processed string: " + result);
        System.out.println("-------------------");
    }

    /**
     * Asks the user if they want to process another string.
     *
     * @param scanner the scanner to read user input
     * @return true if the user wants to continue, false otherwise
     */
    private static boolean askToContinue(Scanner scanner) {
        System.out.print("\nProcess another string? (y/n): ");
        String response = scanner.nextLine().trim().toLowerCase();
        return response.startsWith("y");
    }
}
