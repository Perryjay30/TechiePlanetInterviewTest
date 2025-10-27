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

}
