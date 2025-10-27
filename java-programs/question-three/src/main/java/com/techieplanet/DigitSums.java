package com.techieplanet;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DigitSums {

    public static int sumDigitsRecursive(String s) {
        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException("Input must be a non-empty string of digits.");
        }
        char[] cs = s.toCharArray();
        for (char c : cs) {
            if (!Character.isDigit(c)) {
                throw new IllegalArgumentException("Input must contain only digits 0-9.");
            }
        }
        return sumChars(cs, 0);
    }


    private static int sumChars(char[] cs, int idx) {
        if (idx == cs.length) return 0;
        return (cs[idx] - '0') + sumChars(cs, idx + 1);
    }


    public static int digitalRoot(String s) {
        int sum = sumDigitsRecursive(s);
        while (sum >= 10) {
            sum = sumDigitsOfInt(sum);
        }
        return sum;
    }


    private static int sumDigitsOfInt(int n) {
        if (n < 10) return n;
        return (n % 10) + sumDigitsOfInt(n / 10);
    }


    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String input = br.readLine();
            int sum = sumDigitsRecursive(input);
            int root = digitalRoot(input);
            System.out.println(sum);
            System.out.println(root);
        }
    }
}
