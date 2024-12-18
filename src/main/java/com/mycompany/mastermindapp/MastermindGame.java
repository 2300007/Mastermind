package com.mycompany.mastermindapp;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MastermindGame {

    private static final char[] COLORS = {'R', 'G', 'B', 'Y', 'O', 'P'};
    private static final Map<Character, Integer> COLOR_INDEX_MAP = createColorIndexMap();

    private final int codeLength;
    private final int maxAttempts;
    private final char[] secretCode;

    private int attemptsMade;
    private boolean gameWon;
    private boolean gameOver;

    public MastermindGame(int codeLength, int maxAttempts) {
        this.codeLength = codeLength;
        this.maxAttempts = maxAttempts;
        this.secretCode = generateSecretCode();
        this.attemptsMade = 0;
        this.gameWon = false;
        this.gameOver = false;
    }

    private static Map<Character, Integer> createColorIndexMap() {
        Map<Character, Integer> map = new HashMap<>();
        map.put('R', 0);
        map.put('G', 1);
        map.put('B', 2);
        map.put('Y', 3);
        map.put('O', 4);
        map.put('P', 5);
        return map;
    }

    private char[] generateSecretCode() {
        Random rand = new Random();
        char[] code = new char[codeLength];
        for (int i = 0; i < codeLength; i++) {
            code[i] = COLORS[rand.nextInt(COLORS.length)];
        }
        System.out.println(code);
        return code;
    }

    private int colorToIndex(char c) {
        return COLOR_INDEX_MAP.getOrDefault(c, -1); // Use the map for fast lookup
    }

    public int[] guess(String guessStr) {
        if (gameOver) {
            return new int[]{0, 0};
        }

        attemptsMade++;

        char[] guessChars = guessStr.toCharArray();

        int blackPins = 0;
        int whitePins = 0;

        // First pass: count black pins and record unmatched chars
        // We'll mark black pin positions so we don't count them later
        boolean[] blackMatched = new boolean[codeLength];
        for (int i = 0; i < codeLength; i++) {
            if (guessChars[i] == secretCode[i]) {
                blackPins++;
                blackMatched[i] = true;
            }
        }

        // If all are black, game is won immediately
        if (blackPins == codeLength) {
            gameWon = true;
            gameOver = true;
            return new int[]{blackPins, whitePins};
        }

        // Frequency arrays for unmatched colors
        int[] codeCount = new int[COLORS.length];
        int[] guessCount = new int[COLORS.length];

        // Count frequencies of unmatched characters in secret code
        for (int i = 0; i < codeLength; i++) {
            if (!blackMatched[i]) {
                int idx = colorToIndex(secretCode[i]);
                codeCount[idx]++;
//                codeCount = [0, 1, 1, 0, 1, 0]; // Indices: R=0, G=1, B=2, Y=3, O=4, P=5
            }
        }

        // Count frequencies of unmatched characters in guess
        for (int i = 0; i < codeLength; i++) {
            if (!blackMatched[i]) {
                int idx = colorToIndex(guessChars[i]);
                guessCount[idx]++;
//                guessCount = [0, 1, 1, 0, 1, 0]; // Indices: R=0, G=1, B=2, Y=3, O=4, P=5

            }
        }

        // White pins: sum the minimum matches for each color
        for (int i = 0; i < COLORS.length; i++) {
            whitePins += Math.min(codeCount[i], guessCount[i]);
        }

        // Check if attempts are finished
        if (attemptsMade >= maxAttempts) {
            gameOver = true;
        }

        return new int[]{blackPins, whitePins};
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getAttemptsMade() {
        return attemptsMade;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public char[] getSecretCode() {
        return secretCode.clone();
    }
}
