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
}
