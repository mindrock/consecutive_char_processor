package org.strtools;

import org.strtools.api.StringProcessingStrategy;
import org.strtools.api.ConsecutiveCharRemoverStrategy;
import org.strtools.api.ConsecutiveCharReplacerStrategy;


import java.util.Objects;

/**
 * A factory and utility class for processing strings with consecutive characters using
 * different strategies. This class provides a simple interface for accessing the
 * available processing strategies and applying them to input strings.
 *
 * @since 1.0.0
 */
public final class StringProcessor {

    /**
     * Singleton instance of the remove consecutive strategy.
     */
    private static final StringProcessingStrategy REMOVE_STRATEGY = new ConsecutiveCharRemoverStrategy();

    /**
     * Singleton instance of the replace consecutive strategy.
     */
    private static final StringProcessingStrategy REPLACE_STRATEGY = new ConsecutiveCharReplacerStrategy();

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private StringProcessor() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * Processes a string using the remove consecutive strategy.
     *
     * @param input the string to process
     * @return the processed string
     * @see ConsecutiveCharRemoverStrategy
     */
    public static String processWithRemoveStrategy(String input) {
        return process(input, REMOVE_STRATEGY);
    }

    /**
     * Processes a string using the replace consecutive strategy.
     *
     * @param input the string to process
     * @return the processed string
     * @see ConsecutiveCharReplacerStrategy
     */
    public static String processWithReplaceStrategy(String input) {
        return process(input, REPLACE_STRATEGY);
    }

    /**
     * Processes a string using the specified strategy.
     *
     * @param input    the string to process
     * @param strategy the processing strategy to use
     * @return the processed string
     * @throws NullPointerException if the strategy is null
     */
    public static String process(String input, StringProcessingStrategy strategy) {
        Objects.requireNonNull(strategy, "Processing strategy must not be null");
        return strategy.process(input);
    }

    /**
     * Returns the remove consecutive strategy instance.
     *
     * @return the remove strategy
     */
    public static StringProcessingStrategy getRemoveStrategy() {
        return REMOVE_STRATEGY;
    }

    /**
     * Returns the replace consecutive strategy instance.
     *
     * @return the replace strategy
     */
    public static StringProcessingStrategy getReplaceStrategy() {
        return REPLACE_STRATEGY;
    }
}
