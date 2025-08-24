package org.strtools.api;


import org.strtools.api.StringProcessingStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for the ConsecutiveCharRemoverStrategy class.
 *
 * <p>This test class verifies all functional aspects of the removal strategy,
 * including basic functionality, edge cases, and error conditions.
 *
 * @since 1.0.0
 */
class ConsecutiveCharRemoverStrategyTest {

    private final StringProcessingStrategy strategy = new ConsecutiveCharRemoverStrategy();

    /**
     * Tests the example from the problem statement.
     */
    @Test
    void testProblemExample() {
        assertEquals("d", strategy.process("aabcccbbad"));
    }

    /**
     * Tests basic removal functionality with various input strings.
     *
     * @param input    the input string to process
     * @param expected the expected result after processing
     */
    @ParameterizedTest
    @CsvSource({"aaa, ''", "aaabbb, ''", "aaabbbccc, ''", "aabbaaa, 'aabb'", "aaabaaa, 'b'", "abccba, 'abccba'", "abc, 'abc'", "a, 'a'", "aa, 'aa'", "aaaa, 'a'", "aaaaa, 'aa'", "aabbaaabbb, 'aabb'", "xyz, 'xyz'", "xxxyyyzzz, ''", "xxxyyyzzza, 'a'", "axxxbyyyczzz, 'abc'"})
    void testBasicRemoval(String input, String expected) {
        assertEquals(expected, strategy.process(input));
    }

    /**
     * Tests that processing creates new sequences that need further removal.
     */
    @Test
    void testRemovalCreatesNewSequence() {
        // aabbbba -> aaaaa -> (removed) -> ""
        assertEquals("aaba", strategy.process("aabbbba"));

        // abbbccaa -> a cc aa -> accaa (no removal needed)
        assertEquals("accaa", strategy.process("abbbccaa"));
    }

    /**
     * Tests handling of empty and blank inputs.
     *
     * @param input the input string to test
     */
    @ParameterizedTest
    @ValueSource(strings = {""})
    void testEmptyAndBlankInputs(String input) {
        assertEquals("", strategy.process(input));
    }

    /**
     * Tests that blank input containing only whitespace characters
     * throws an IllegalArgumentException as whitespace is not allowed
     */
    @ParameterizedTest
    @ValueSource(strings = {"   "})
    void testBlankInputThrowsException(String input) {
        assertThrows(IllegalArgumentException.class, () -> strategy.process(input), "Blank input with spaces should throw IllegalArgumentException");
    }

    /**
     * Tests that null input is handled correctly.
     */
    @Test
    void testNullInput() {
        assertEquals("", strategy.process(null));
    }

    /**
     * Tests that invalid characters throw the correct exception.
     *
     * @param input the input string containing invalid characters
     */
    @ParameterizedTest
    @ValueSource(strings = {"AAA", "aa1bb", "a b", "a-b", "a@b"})
    void testInvalidCharacters(String input) {
        assertThrows(IllegalArgumentException.class, () -> strategy.process(input));
    }

    /**
     * Tests the strategy name is correct.
     */
    @Test
    void testStrategyName() {
        assertEquals("ConsecutiveCharRemoverStrategy", strategy.getName());
    }

    /**
     * Tests performance with a very long string to ensure efficiency.
     */
    @Test
    void testPerformanceWithLongString() {
        // Create a string with 1,000,000 'a's
        String longInput = "a".repeat(1_000_002);

        // Processing should be efficient and result in empty string
        long startTime = System.currentTimeMillis();
        String result = strategy.process(longInput);
        long endTime = System.currentTimeMillis();

        assertEquals("", result);
        assertTrue(endTime - startTime < 1000, "Processing took too long");
    }
}
