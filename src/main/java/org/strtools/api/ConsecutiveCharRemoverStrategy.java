package org.strtools.api;

import org.strtools.model.CharacterSequence;
import org.strtools.util.ValidationUtils;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * A processing strategy that removes sequences of 3 or more consecutive identical characters.
 *
 * <p>This implementation processes the input string in a single pass using a stack-based
 * approach for efficient handling of consecutive characters. After removing a qualifying
 * sequence, it continues processing the resulting string to handle any new sequences
 * that may have been created by the removal.
 *
 * <p>Example:
 * <pre>
 * Input:  "aabcccbbad"
 * Output: "d"
 * </pre>
 *
 * @since 1.0.0
 */
public final class ConsecutiveCharRemoverStrategy implements StringProcessingStrategy {

    /**
     * Processes the input string by removing all sequences of 3 or more consecutive
     * identical characters, repeating until no more such sequences exist.
     *
     * @param input the string to process, may be null or empty
     * @return the processed string with all qualifying sequences removed
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

        // Use Deque as a stack for efficient character sequence tracking
        Deque<CharacterSequence> stack = new ArrayDeque<>();

        for (char c : normalizedInput.toCharArray()) {
            processCharacter(c, stack);
        }

        // Process any remaining sequences that need removal
        processRemainingStack(stack);

        // Build the final result from the stack
        return buildResult(stack);
    }

    /**
     * Processes a single character, updating the stack with the current character sequences.
     *
     * @param c     the current character being processed
     * @param stack the stack tracking character sequences
     */
    private void processCharacter(char c, Deque<CharacterSequence> stack) {
        if (!stack.isEmpty() && stack.peek().character() == c) {
            // Increment count for existing sequence
            CharacterSequence current = stack.pop();
            stack.push(current.increment());
        } else {
            // Start new sequence for different character
            stack.push(new CharacterSequence(c, 1));
        }

        // Check if top of stack needs processing after adding current character
        if (stack.peek().shouldProcess()) {
            stack.pop(); // Remove the qualifying sequence
        }
    }

    /**
     * Processes the remaining stack to handle any sequences that need removal
     * after initial character processing.
     *
     * @param stack the stack tracking character sequences
     */
    private void processRemainingStack(Deque<CharacterSequence> stack) {
        // No additional processing needed for removal strategy
        // as we already remove sequences as they qualify
    }

    /**
     * Builds the final result string from the stack of character sequences.
     *
     * @param stack the stack containing remaining character sequences
     * @return the resulting string after all processing is complete
     */
    private String buildResult(Deque<CharacterSequence> stack) {
        // Use StringBuilder for efficient string construction
        StringBuilder result = new StringBuilder();

        // Process stack in reverse order to maintain original sequence
        stack.descendingIterator()
                .forEachRemaining(seq -> result.append(String.valueOf(seq.character()).repeat(seq.count())));

        return result.toString();
    }

    /**
     * Returns a descriptive name for this processing strategy.
     *
     * @return the strategy name
     */
    @Override
    public String getName() {
        return "ConsecutiveCharRemoverStrategy";
    }
}
