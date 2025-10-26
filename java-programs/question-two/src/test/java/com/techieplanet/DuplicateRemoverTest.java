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

    @Test
    void zerosAndNonZeros_mixed() {
        int[] row = {0, 1, 0, 2, 0, 1, 2, 0};
        int[] expected = {0, 1, 0, 2, 0, 0, 0, 0};
        int[][] in = new int[][]{row};
        int[][] out = DuplicateRemover.dedupePerRow(in);
        assertArrayEquals(expected, out[0]);
    }

    @Test
    void negativesAndExtremeInts_shouldWork() {
        int min = Integer.MIN_VALUE;
        int max = Integer.MAX_VALUE;
        int[] row = {min, max, -1, 42, min, max, -1, 42};
        int[] expected = {min, max, -1, 42, 0, 0, 0, 0};
        int[][] in = new int[][]{row};
        int[][] out = DuplicateRemover.dedupePerRow(in);
        assertArrayEquals(expected, out[0]);
    }

    @Test
    void inPlaceGuarantee_rowReferencesRemainSame() {
        int[] r1 = {1, 1, 2};
        int[] r2 = {3, 4, 4, 3};
        int[][] in = new int[][]{r1, r2};

        int[][] out = DuplicateRemover.dedupePerRow(in);

        assertSame(in, out, "Outer array ref should be same");
        assertSame(r1, out[0], "Row 0 should be modified in place");
        assertSame(r2, out[1], "Row 1 should be modified in place");

        assertArrayEquals(new int[]{1, 0, 2}, out[0]);
        assertArrayEquals(new int[]{3, 4, 0, 0}, out[1]);
    }

    @Test
    void longDeterministicRow_threeRepeatsOf50Distinct_shouldZeroLast100() {
        int distinct = 50;
        int repeats = 3;
        int[] row = new int[distinct * repeats];
        for (int r = 0; r < repeats; r++) {
            for (int i = 0; i < distinct; i++) {
                row[r * distinct + i] = i;
            }
        }

        int[][] in = new int[][]{row};
        int[][] out = DuplicateRemover.dedupePerRow(in);

        for (int i = 0; i < distinct; i++) {
            assertEquals(i, out[0][i], "First occurrence must remain");
        }
        for (int i = distinct; i < row.length; i++) {
            assertEquals(0, out[0][i], "Subsequent duplicates must be zero");
        }
    }

    @Test
    void varyingRowLengths_multipleRows() {
        int[][] in = {
                {1, 2, 3},
                {5, 5},
                {7},
                {8, 9, 8, 9, 8, 9, 8}
        };
        int[][] out = DuplicateRemover.dedupePerRow(in);

        assertArrayEquals(new int[]{1, 2, 3}, out[0]);
        assertArrayEquals(new int[]{5, 0}, out[1]);
        assertArrayEquals(new int[]{7}, out[2]);
        assertArrayEquals(new int[]{8, 9, 0, 0, 0, 0, 0}, out[3]);
    }
}
