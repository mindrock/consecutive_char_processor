package org.strtools.util;

import java.util.Objects;

/**
 * Utility class providing validation methods for input strings and characters.
 *
 * <p>This class contains static methods to validate that inputs conform to the
 * expected format (lowercase alphabetic characters only) and handle null values
 * appropriately.
 *
 * @since 1.0.0
 */
public final class ValidationUtils {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private ValidationUtils() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * Validates that a character is a lowercase letter (a-z).
     *
     * @param c the character to validate
     * @return true if the character is a lowercase letter, false otherwise
     */
    public static boolean isValidCharacter(char c) {
        return c >= 'a' && c <= 'z';
    }

    /**
     * Validates that all characters in a string are lowercase letters (a-z).
     *
     * @param input the string to validate, may be null
     * @return true if all characters are valid lowercase letters or the string is null/empty
     */
    public static boolean isValidString(String input) {
        if (input == null || input.isEmpty()) {
            return true;
        }

        return input.chars().allMatch(ValidationUtils::isValidCharacterCodePoint);
    }

    /**
     * Helper method to check if a code point represents a valid lowercase letter.
     *
     * @param codePoint the code point to check
     * @return true if the code point is a valid lowercase letter
     */
    private static boolean isValidCharacterCodePoint(int codePoint) {
        return codePoint >= 'a' && codePoint <= 'z';
    }

    /**
     * Normalizes input by converting null to an empty string.
     *
     * @param input the input string to normalize, may be null
     * @return the original string if not null, otherwise an empty string
     */
    public static String normalizeInput(String input) {
        return Objects.requireNonNullElse(input, "");
    }

    /**
     * Validates input and throws an exception if invalid characters are found.
     *
     * @param input the string to validate
     * @throws IllegalArgumentException if the string contains invalid characters
     */
    public static void validateOrThrow(String input) {
        String normalized = normalizeInput(input);
        if (!isValidString(normalized)) {
            throw new IllegalArgumentException(
                    "Input contains invalid characters. Only lowercase letters (a-z) are allowed."
            );
        }
    }
}
