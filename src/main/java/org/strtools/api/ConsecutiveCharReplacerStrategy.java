package org.strtools.api;

import org.strtools.util.ValidationUtils;

/**
 * A processing strategy that replaces sequences of 3 or more consecutive identical characters
 * with a single character that comes immediately before it in the alphabet.
 *
 * <p>This implementation uses a stack-based approach to efficiently track character sequences
 * and handle replacements. After replacement, it continues processing to handle any new sequences
 * that may have been created by the replacement operation.
 *
 * <p>Example:
 * <pre>
 * Input:  "abcccbad"
 * Output: "d"
 * </pre>
 *
 * <p>Note: The character 'a' has no preceding character in the alphabet, so sequences of 'a's
 * will be removed rather than replaced.
 *
 * @since 1.0.0
 */
public final class ConsecutiveCharReplacerStrategy implements StringProcessingStrategy {

    /**
     * Processes the input string by replacing all sequences of 3 or more consecutive
     * identical characters with the preceding alphabet character, repeating until
     * no more such sequences exist.
     *
     * @param input the string to process, may be null or empty
     * @return the processed string with all qualifying sequences replaced
     * @throws IllegalArgumentException if the input contains invalid characters
     */
    @Override
    public String process(String input) {
        // Validate input and handle null/empty cases
        String normalizedInput = ValidationUtils.normalizeInput(input);
        ValidationUtils.validateOrThrow(normalizedInput);

        if (normalizedInput.isEmpty()) {
            return "";
        }

        // Process in a loop to handle cases where replacement creates new sequences
        String current = normalizedInput;
        String previous;
        do {
            previous = current;
            current = processSinglePass(previous);
        } while (!current.equals(previous));

        return current;
    }

    /**
     * Processes a single pass through the string, replacing all 3+ consecutive character sequences
     * with their preceding character (or removing if no preceding character exists).
     *
     * @param input the string to process
     * @return the string after one processing pass
     */
    private String processSinglePass(String input) {
        StringBuilder result = new StringBuilder();
        int i = 0;

        while (i < input.length()) {
            // Check if current position starts a sequence of 3+ identical characters
            char currentChar = input.charAt(i);
            int j = i;

            // Find end of current sequence
            while (j < input.length() && input.charAt(j) == currentChar) {
                j++;
            }

            int sequenceLength = j - i;

            if (sequenceLength >= 3) {
                // Get preceding character (if any exists in the result so far)
                char precedingChar = (result.length() > 0) ? result.charAt(result.length() - 1) : 0;

                // Replace sequence with preceding character (or remove if none)
                if (precedingChar != 0) {
                    result.append(precedingChar);
                }

                // Move to end of this sequence
                i = j;
            } else {
                // No replacement needed - add characters to result
                result.append(input, i, j);
                i = j;
            }
        }

        return result.toString();
    }

    /**
     * Returns a descriptive name for this processing strategy.
     *
     * @return the strategy name
     */
    @Override
    public String getName() {
        return "ConsecutiveCharReplacerStrategy";
    }
}
