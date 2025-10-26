package com.techieplanet;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TimeInWordsTest {

    @Test
    void testOClock() {
        assertEquals("Five o'clock", TimeInWords.timeInWords(5, 0));
        assertEquals("Twelve o'clock", TimeInWords.timeInWords(12, 0));
    }

    @Test
    void testSingularMinutePast() {
        assertEquals("One minute past five", TimeInWords.timeInWords(5, 1));
    }

    @Test
    void testSimpleMinutesPast() {
        assertEquals("Ten minutes past five", TimeInWords.timeInWords(5, 10));
        assertEquals("Twenty-eight minutes past five", TimeInWords.timeInWords(5, 28));
    }

    @Test
    void testHalfPast() {
        assertEquals("Half past five", TimeInWords.timeInWords(5, 30));
    }

    @Test
    void testQuarterPast() {
        assertEquals("Quarter past five", TimeInWords.timeInWords(5, 15));
    }

    @Test
    void testQuarterTo() {
        assertEquals("Quarter to six", TimeInWords.timeInWords(5, 45));
        assertEquals("Quarter to one", TimeInWords.timeInWords(12, 45));
    }

    @Test
    void testEdgeMinutes() {
        assertEquals("One minute to six", TimeInWords.timeInWords(5, 59));
        assertEquals("One minute to one", TimeInWords.timeInWords(12, 59));
    }

    @Test
    void testToNextHour() {
        assertEquals("Twenty minutes to six", TimeInWords.timeInWords(5, 40));
        assertEquals("Thirteen minutes to six", TimeInWords.timeInWords(5, 47));
    }

    @Test
    void testInvalidHour() {
        assertThrows(IllegalArgumentException.class, () -> TimeInWords.timeInWords(0, 10));
        assertThrows(IllegalArgumentException.class, () -> TimeInWords.timeInWords(13, 10));
    }

    @Test
    void testInvalidMinute() {
        assertThrows(IllegalArgumentException.class, () -> TimeInWords.timeInWords(5, -1));
        assertThrows(IllegalArgumentException.class, () -> TimeInWords.timeInWords(5, 60));
    }
}
