package org.strtools.api;

import org.strtools.api.StringProcessingStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for the ConsecutiveCharReplacerStrategy class.
 *
 * <p>This test class verifies all functional aspects of the replacement strategy,
 * including basic functionality, chain replacements, edge cases, and error conditions.
 *
 * @since 1.0.0
 */
class ConsecutiveCharReplacerStrategyTest {

    private final StringProcessingStrategy strategy = new ConsecutiveCharReplacerStrategy();

    /**
     * Tests the first example from requirements:
     * Input: abcccbad → abbbad → aaad → d
     * <p>
     * Breakdown:
     * 1. abcccbad → abbbad (ccc replaced by preceding 'b')
     * 2. abbbad → aaad (bbb replaced by preceding 'a')
     * 3. aaad → d (aaa replaced by nothing as no preceding character)
     */
    @Test
    void testFirstExample() {
        assertEquals("d", strategy.process("abcccbad"));
    }

    /**
     * Tests the second example from requirements:
     * Input: aabcccbbad → aabbbbad → aaaad → d
     * <p>
     * Breakdown:
     * 1. aabcccbbad → aabbbbad (ccc replaced by preceding 'b')
     * 2. aabbbbad → aaaad (bbbb replaced by preceding 'a')
     * 3. aaaad → d (aaaa replaced by nothing as no preceding character)
     */
    @Test
    void testSecondExample() {
        assertEquals("d", strategy.process("aabcccbbad"));
    }

    /**
     * Tests the specific case: xccc → xx
     * Because "ccc" is preceded by 'x', so replaced by 'x' resulting in "xx"
     */
    @Test
    void testXcccCase() {
        assertEquals("xx", strategy.process("xccc"));
    }

    /**
     * Tests the specific case: baaa → bb
     * Because "aaa" is preceded by 'b', so replaced by 'b' resulting in "bb"
     */
    @Test
    void testBaaaCase() {
        assertEquals("bb", strategy.process("baaa"));
    }

    /**
     * Tests basic replacement functionality with various inputs
     *
     * @param input    the input string to process
     * @param expected the expected result after full processing
     */
    @ParameterizedTest
    @CsvSource({
            "ccc, ''",         // 3 c's with no preceding character → ''
            "bbb, ''",         // 3 b's with no preceding character → ''
            "aaa, ''",         // 3 a's with no preceding character → ''
            "xccc, 'xx'",      // x followed by ccc → x + x → xx
            "baaa, 'bb'",      // b followed by aaa → b + b → bb
            "abbbcc, 'aacc'",  // a followed by bbb followed by cc → a + a + cc → aacc
            "abc, 'abc'",      // No 3+ sequences
            "a, 'a'",          // Single character
            "aa, 'aa'",        // Two characters
            "xxxyyyzzz, ''",   // xxx (no preceding) → removed, then yyy (no preceding) → removed, then zzz → ''
            "axxx, 'aa'",      // a followed by xxx → a + a → aa
            "xxxa, 'a'",       // xxx (no preceding) → '', then a → a
            "abccc, 'abb'",    // ab followed by ccc → ab + b → abb
            "aabbb, ''",    // aa followed by bbb → aa + a → aaa → which then gets processed to ""
            "aabbb, ''"        // Final result after full processing of aabbb → aaa → ""
    })
    void testBasicReplacements(String input, String expected) {
        assertEquals(expected, strategy.process(input));
    }

    /**
     * Tests multi-pass replacement chains where one replacement creates
     * new sequences that need processing.
     */
    @Test
    void testReplacementChains() {
        // abcccbad → abbbad (ccc→b) → aaad (bbb→a) → d (aaa→'')
        assertEquals("d", strategy.process("abcccbad"));

        // aabcccbbad → aabbbbad (ccc→b) → aaaad (bbbb→a) → d (aaaa→ '')
        assertEquals("d", strategy.process("aabcccbbad"));

        // aabbb → aaa (bbb→a) → "" (aaa→removed)
        assertEquals("", strategy.process("aabbb"));

        // xaabbccc → xaabb + b (ccc→b) → xaabbb → xaa + a (bbb→a) → xaaa → xx (aaa→x)
        assertEquals("xx", strategy.process("xaabbccc"));

        // bbaaa → bb + b (aaa→b) → bbb → "" (bbb→'')
        assertEquals("", strategy.process("bbaaa"));
    }

    /**
     * Tests handling of empty and blank inputs.
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
        assertThrows(IllegalArgumentException.class, () -> strategy.process(input),
                "Blank input with spaces should throw IllegalArgumentException");
    }

    /**
     * Tests null input handling.
     */
    @Test
    void testNullInput() {
        assertEquals("", strategy.process(null));
    }

    /**
     * Tests validation of input characters.
     */
    @ParameterizedTest
    @ValueSource(strings = {"CCC", "cc1dd", "c d", "c-d", "c@d", "Aaa"})
    void testInvalidCharacters(String input) {
        assertThrows(IllegalArgumentException.class, () -> strategy.process(input));
    }

    /**
     * Verifies strategy name.
     */
    @Test
    void testStrategyName() {
        assertEquals("ConsecutiveCharReplacerStrategy", strategy.getName());
    }

    /**
     * Tests performance with very long input strings.
     */
    @Test
    void testPerformanceWithLongString() {
        // Create string with 1,000,000 'z's preceded by an 'x'
        String longInput = "x" + "z".repeat(1_000_000);

        long startTime = System.currentTimeMillis();
        String result = strategy.process(longInput);
        long endTime = System.currentTimeMillis();

        // x followed by 1,000,000 z's → x + x → xx
        assertEquals("xx", result);
        assertTrue(endTime - startTime < 1000,
                "Processing took too long: " + (endTime - startTime) + "ms");
    }

}

