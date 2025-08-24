package org.strtools.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for the ValidationUtils class.
 *
 * <p>This class verifies that all validation methods work correctly
 * for valid and invalid inputs.
 *
 * @since 1.0.0
 */
class ValidationUtilsTest {
    /**
     * Tests that valid characters are recognized as valid.
     */
    @Test
    void testValidCharacters() {
        for (char c = 'a'; c <= 'z'; c++) {
            assertTrue(ValidationUtils.isValidCharacter(c), "Character " + c + " should be valid");
        }
    }

    /**
     * Tests that invalid characters are recognized as invalid.
     */
    @Test
    void testInvalidCharacters() {
        assertFalse(ValidationUtils.isValidCharacter('A'));
        assertFalse(ValidationUtils.isValidCharacter('Z'));
        assertFalse(ValidationUtils.isValidCharacter('0'));
        assertFalse(ValidationUtils.isValidCharacter('9'));
        assertFalse(ValidationUtils.isValidCharacter(' '));
        assertFalse(ValidationUtils.isValidCharacter('!'));
        assertFalse(ValidationUtils.isValidCharacter('@'));
        assertFalse(ValidationUtils.isValidCharacter('['));
        assertFalse(ValidationUtils.isValidCharacter(']'));
        assertFalse(ValidationUtils.isValidCharacter('\t'));
        assertFalse(ValidationUtils.isValidCharacter('\n'));
    }

    /**
     * Tests that valid strings are recognized as valid.
     *
     * @param input the input string to test
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "", "a", "abc", "xyz", "abcdefghijklmnopqrstuvwxyz",
            "aaabbbccc", "abacab"
    })
    void testValidStrings(String input) {
        assertTrue(ValidationUtils.isValidString(input));
    }

    /**
     * Tests that invalid strings are recognized as invalid.
     *
     * @param input the input string to test
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "A", "ABC", "aBc", "Xyz", "123", "abc123",
            "abc ", " abc", "a bc", "a@b", "a-b", "a\nb"
    })
    void testInvalidStrings(String input) {
        assertFalse(ValidationUtils.isValidString(input));
    }

    /**
     * Tests that null input is normalized to an empty string.
     */
    @Test
    void testNormalizeNullInput() {
        assertEquals("", ValidationUtils.normalizeInput(null));
    }

    /**
     * Tests that non-null input is returned unchanged.
     */
    @Test
    void testNormalizeNonNullInput() {
        String input = "test";
        assertSame(input, ValidationUtils.normalizeInput(input));
    }

    /**
     * Tests that validateOrThrow throws an exception for invalid strings.
     */
    @Test
    void testValidateOrThrowWithInvalidString() {
        assertThrows(IllegalArgumentException.class, () ->
                ValidationUtils.validateOrThrow("invalidString123"));
    }

    /**
     * Tests that validateOrThrow does not throw an exception for valid strings.
     *
     * @param input the valid input string
     */
    @ParameterizedTest
    @ValueSource(strings = {"", "validstring", "anothervalidstring"})
    void testValidateOrThrowWithValidString(String input) {
        assertDoesNotThrow(() -> ValidationUtils.validateOrThrow(input));
    }
}
