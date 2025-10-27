package com.techieplanet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DigitSumsTest {

    @Test
    void sumDigitsRecursive_basic() {
        assertEquals(6, DigitSums.sumDigitsRecursive("123"));
        assertEquals(0, DigitSums.sumDigitsRecursive("0"));
        assertEquals(10, DigitSums.sumDigitsRecursive("19"));
        assertEquals(1, DigitSums.sumDigitsRecursive("1000"));
    }

    @Test
    void sumDigitsRecursive_sampleLongFromPrompt() {
        String s = "1234445123444512344451234445123444512344451234445";
        assertEquals(161, DigitSums.sumDigitsRecursive(s));
    }

    @Test
    void sumDigitsRecursive_leadingZeros() {
        assertEquals(9, DigitSums.sumDigitsRecursive("00045"));
        assertEquals(1, DigitSums.sumDigitsRecursive("0000001"));
    }

    @Test
    void sumDigitsRecursive_singleDigit() {
        assertEquals(7, DigitSums.sumDigitsRecursive("7"));
        assertEquals(0, DigitSums.sumDigitsRecursive("0"));
    }

    @Test
    void sumDigitsRecursive_maxLength100_allNines() {
        StringBuilder sb = new StringBuilder(100);
        for (int i = 0; i < 100; i++) sb.append('9');
        String s = sb.toString();
        assertEquals(900, DigitSums.sumDigitsRecursive(s));
    }

    @Test
    void sumDigitsRecursive_invalid_nullOrEmpty() {
        assertThrows(IllegalArgumentException.class, () -> DigitSums.sumDigitsRecursive(null));
        assertThrows(IllegalArgumentException.class, () -> DigitSums.sumDigitsRecursive(""));
    }

    @Test
    void sumDigitsRecursive_invalid_nonDigitCharacters() {
        assertThrows(IllegalArgumentException.class, () -> DigitSums.sumDigitsRecursive("12a3"));
        assertThrows(IllegalArgumentException.class, () -> DigitSums.sumDigitsRecursive(" 123"));
        assertThrows(IllegalArgumentException.class, () -> DigitSums.sumDigitsRecursive("12-3"));
        assertThrows(IllegalArgumentException.class, () -> DigitSums.sumDigitsRecursive("1_23"));
    }

}
