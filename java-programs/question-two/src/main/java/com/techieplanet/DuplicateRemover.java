package com.techieplanet;


import com.techieplanet.utils.HashUtils;
import java.util.Arrays;

public class DuplicateRemover {

    public static int[][] dedupePerRow(int[][] a) {
        if (a == null) return null;
        for (int i = 0; i < a.length; i++) {
            int[] row = a[i];
            if (row == null || row.length == 0) continue;

            int cap = HashUtils.nextPow2(Math.max(4, (int)(row.length / 0.66f)));
            HashUtils.IntHashSet seen = new HashUtils.IntHashSet(cap);

            for (int j = 0; j < row.length; j++) {
                int v = row[j];
                if (!seen.add(v)) {
                    row[j] = 0;
                }
            }
        }
        return a;
    }


    public static void main(String[] args) {
        int[][] input = new int[][]{
                {1, 3, 1, 2, 3, 4, 4, 3, 5},
                {1, 1, 1, 1, 1, 1, 1}
        };
        int[][] out = dedupePerRow(input);
        for (int[] row : out) System.out.println(Arrays.toString(row));
    }
}
