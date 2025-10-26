package com.techieplanet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TimeInWords {

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String hLine = br.readLine();
            String mLine = br.readLine();

            int h = Integer.parseInt(hLine.trim());
            int m = Integer.parseInt(mLine.trim());

            try {
                String result = timeInWords(h, m);
                System.out.println(result);
            } catch (IllegalArgumentException ex) {
                System.out.println("Invalid input");
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Invalid input");
        }
    }


    public static String timeInWords(int h, int m) {
        if (h < 1 || h > 12 || m < 0 || m > 59) {
            throw new IllegalArgumentException("Hour must be 1..12 and minute 0..59");
        }

        if (m == 0) {
            return capitalize(numberToWords(h) + " o'clock");
        }

        boolean toNextHour = m > 30;
        int minutes = toNextHour ? 60 - m : m;
        int hourWord = toNextHour ? (h == 12 ? 1 : h + 1) : h;

        String minutePhrase;
        if (minutes == 15) {
            minutePhrase = "quarter";
        } else if (minutes == 30) {
            minutePhrase = "half";
        } else if (minutes == 1) {
            minutePhrase = "one minute";
        } else {
            minutePhrase = numberToWords(minutes) + " minutes";
        }

        String connector = toNextHour ? " to " : " past ";
        String phrase = minutePhrase + connector + numberToWords(hourWord);
        return capitalize(phrase);
    }


    private static String numberToWords(int n) {
        return switch (n) {
            case 1 -> "one";
            case 2 -> "two";
            case 3 -> "three";
            case 4 -> "four";
            case 5 -> "five";
            case 6 -> "six";
            case 7 -> "seven";
            case 8 -> "eight";
            case 9 -> "nine";
            case 10 -> "ten";
            case 11 -> "eleven";
            case 12 -> "twelve";
            case 13 -> "thirteen";
            case 14 -> "fourteen";
            case 15 -> "fifteen";
            case 16 -> "sixteen";
            case 17 -> "seventeen";
            case 18 -> "eighteen";
            case 19 -> "nineteen";
            case 20 -> "twenty";
            case 21 -> "twenty-one";
            case 22 -> "twenty-two";
            case 23 -> "twenty-three";
            case 24 -> "twenty-four";
            case 25 -> "twenty-five";
            case 26 -> "twenty-six";
            case 27 -> "twenty-seven";
            case 28 -> "twenty-eight";
            case 29 -> "twenty-nine";
            case 30 -> "thirty";
            default -> throw new IllegalArgumentException("Supported range is 1..29 for number words");
        };
    }

    private static String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
