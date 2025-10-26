package com.techieplanet.utils;

public final class HashUtils {
    private HashUtils() {}

    public static int nextPow2(int x) {
        int n = 1;
        while (n < x) n <<= 1;
        return n;
    }


    public static final class IntHashSet {
        private int[] keys;
        private byte[] states;
        private int size;
        private int mask;
        private int threshold;

        public IntHashSet(int initialCapacity) {
            int cap = Math.max(4, HashUtils.nextPow2(initialCapacity));
            this.keys = new int[cap];
            this.states = new byte[cap];
            this.mask = cap - 1;
            this.threshold = (int)(cap * 0.66f);
        }


        public boolean add(int key) {
            if (size >= threshold) resize();

            int idx = probeIndex(key);
            if (states[idx] == 1) {
                return false;
            } else {
                keys[idx] = key;
                states[idx] = 1;
                size++;
                return true;
            }
        }

        private int probeIndex(int key) {
            int h = mix(key) & mask;
            int idx = h;
            while (states[idx] == 1 && keys[idx] != key) {
                idx = (idx + 1) & mask;
            }
            return idx;
        }

        private void resize() {
            int newCap = keys.length << 1;
            int[] oldKeys = keys;
            byte[] oldStates = states;

            keys = new int[newCap];
            states = new byte[newCap];
            mask = newCap - 1;
            size = 0;
            threshold = (int)(newCap * 0.66f);

            for (int i = 0; i < oldKeys.length; i++) {
                if (oldStates[i] == 1) {
                    int k = oldKeys[i];
                    int idx = probeIndex(k);
                    keys[idx] = k;
                    states[idx] = 1;
                    size++;
                }
            }
        }


        private static int mix(int x) {
            x ^= (x >>> 16);
            x *= 0x7feb352d;
            x ^= (x >>> 15);
            x *= 0x846ca68b;
            x ^= (x >>> 16);
            return x;
        }
    }
}
