package com.techieplanet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DuplicateRemoverTest {

    @Test
    void sampleCase_shouldMatchExpected() {
        int[][] in = {
                {1, 3, 1, 2, 3, 4, 4, 3, 5},
                {1, 1, 1, 1, 1, 1, 1}
        };
        int[][] expected = {
                {1, 3, 0, 2, 0, 4, 0, 0, 5},
                {1, 0, 0, 0, 0, 0, 0}
        };

        int[][] out = DuplicateRemover.dedupePerRow(in);

        assertNotNull(out);
        assertSame(in, out, "Should operate in-place and return same reference");
        assertArrayEquals(expected[0], out[0]);
        assertArrayEquals(expected[1], out[1]);
    }

    @Test
    void nullArray_shouldReturnNull() {
        assertNull(DuplicateRemover.dedupePerRow(null));
    }

    @Test
    void emptyOuterArray_shouldReturnSameEmpty() {
        int[][] in = new int[0][];
        int[][] out = DuplicateRemover.dedupePerRow(in);
        assertSame(in, out);
        assertEquals(0, out.length);
    }

}
