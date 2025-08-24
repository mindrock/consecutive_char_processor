package org.strtools.api;

/**
 * A functional interface representing a strategy for processing strings containing
 * consecutive characters. Implementations define specific behaviors for handling
 * sequences of identical characters.
 *
 * <p>This is a sealed interface that can only be implemented by specified classes
 * to ensure consistent behavior across all processing strategies.
 *
 * @since 1.0.0
 */
public sealed interface StringProcessingStrategy permits
        ConsecutiveCharRemoverStrategy,
        ConsecutiveCharReplacerStrategy {

    /**
     * Processes the input string according to the specific strategy implementation.
     *
     * @param input the string to process, may be null or empty
     * @return the processed string, will never be null
     */
    String process(String input);

    /**
     * Returns the name of this processing strategy.
     *
     * @return the strategy name
     */
    default String getName() {
        return this.getClass().getSimpleName();
    }
}
