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

    @Test
    void nullAndEmptyRows_shouldBeSafelyIgnored() {
        int[][] in = new int[][]{
                null,
                {},
                {5, 5, 5},
                null,
                {}
        };
        int[][] out = DuplicateRemover.dedupePerRow(in);
        assertSame(in, out);

        assertNull(out[0]);
        assertEquals(0, out[1].length);
        assertArrayEquals(new int[]{5, 0, 0}, out[2]);
        assertNull(out[3]);
        assertEquals(0, out[4].length);
    }

    @Test
    void allSame_shouldZeroAllButFirst() {
        int[][] in = new int[][]{
                {4, 4, 4, 4},
                {0, 0, 0}
        };
        int[][] out = DuplicateRemover.dedupePerRow(in);
        assertArrayEquals(new int[]{4, 0, 0, 0}, out[0]);
        assertArrayEquals(new int[]{0, 0, 0}, out[1]);
    }

    @Test
    void interleavedDuplicates_shouldOnlyKeepFirstOccurrence() {
        int[] row = {1, 2, 1, 3, 2, 4, 1, 5, 2};
        int[] expected = {1, 2, 0, 3, 0, 4, 0, 5, 0};
        int[][] in = new int[][]{row};
        int[][] out = DuplicateRemover.dedupePerRow(in);
        assertArrayEquals(expected, out[0]);
    }
}
