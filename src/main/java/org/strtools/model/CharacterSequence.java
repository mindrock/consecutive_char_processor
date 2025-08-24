package org.strtools.model;

/**
 * A record representing a sequence of identical characters with their count.
 *
 * <p>This immutable data carrier holds a character and the number of consecutive
 * occurrences. It provides utility methods for common operations like incrementing
 * the count and checking if the sequence meets the minimum length threshold for processing.
 *
 * @param character the character in the sequence
 * @param count     the number of consecutive occurrences
 * @since 1.0.0
 */
public record CharacterSequence(char character, int count) {

    /**
     * The minimum sequence length that triggers processing.
     */
    public static final int MIN_PROCESS_CHAR_LENGTH = 3;

    /**
     * Creates a new CharacterSequence with validation to ensure count is positive.
     *
     * @param character the character in the sequence
     * @param count     the number of consecutive occurrences, must be at least 1
     * @throws IllegalArgumentException if count is less than 1
     */
    public CharacterSequence {
        if (count < 1) {
            throw new IllegalArgumentException("Count must be positive: " + count);
        }
    }

    /**
     * Returns a new CharSequence with the count incremented by 1.
     *
     * @return a new CharSequence instance with incremented count
     */
    public CharacterSequence increment() {
        return new CharacterSequence(character, count + 1);
    }

    /**
     * Checks if this sequence meets or exceeds the minimum length required for processing.
     *
     * @return true if count >= MIN_PROCESS_LENGTH, false otherwise
     */
    public boolean shouldProcess() {
        return count >= MIN_PROCESS_CHAR_LENGTH;
    }

    /**
     * Returns a new CharSequence with the same character and the specified count.
     *
     * @param newCount the new count value, must be at least 1
     * @return a new CharSequence instance with the updated count
     */
    public CharacterSequence withCount(int newCount) {
        return new CharacterSequence(character, newCount);
    }

    /**
     * Returns a string representation of this sequence in the format "character:count".
     *
     * @return string representation
     */
    @Override
    public String toString() {
        return character + ":" + count;
    }
}
