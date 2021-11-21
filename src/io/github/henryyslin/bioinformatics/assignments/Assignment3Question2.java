package io.github.henryyslin.bioinformatics.assignments;

// CSCI3220 2021-22 First Term Assignment 3
//
// I declare that the assignment here submitted is original except for source material
// explicitly acknowledged, and that the same or closely related material has not been
// previously submitted for another course. I also acknowledge that I am aware of
// University policy and regulations on honesty in academic work, and of the
// disciplinary guidelines and procedures applicable to breaches of such policy and
// regulations, as contained in the following websites.
//
// University Guideline on Academic Honesty:
// http://www.cuhk.edu.hk/policy/academichonesty/
//
// Student Name: Lin Yik Shun
// Student ID: 1155157489

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Assignment3Question2 {

    /**
     * Search for the index of the emission symbol in the emission character array.
     *
     * @param emissionChars the emission character array.
     * @param c             the emission symbol to find.
     * @return the index of the emission symbol in the emission character array, or -1 if not found.
     */
    private static int getEmissionIndex(char[] emissionChars, char c) {
        for (int i = 0; i < emissionChars.length; i++) {
            if (emissionChars[i] == c) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Trace back from the given position and state to find the most likely state sequences.
     *
     * @param backtrack the matrix for backtracking.
     * @param i         the sequence index.
     * @param state     the state to trace back from.
     * @return the most likely state sequences.
     */
    private static List<String> traceback(int[][] backtrack, int i, int state) {
        List<String> result = new ArrayList<>();
        if (i == 0) {
            result.add("s" + (state + 1));
            return result;
        }
        for (int j = 0; j < backtrack[i].length; j++) {
            if ((backtrack[i][state] & (1 << j)) != 0) {
                List<String> pre = traceback(backtrack, i - 1, j);
                result.addAll(pre.stream().map(s -> s + " s" + (state + 1)).collect(Collectors.toList()));
            }
        }
        return result;
    }

    public static void main(String[] args) {
        // Read inputs
        Scanner console = new Scanner(System.in);

        int stateCount = console.nextInt();

        double[] initialProbs = new double[stateCount];
        for (int i = 0; i < stateCount; i++) {
            initialProbs[i] = console.nextDouble();
        }

        double[][] transitionProbs = new double[stateCount][stateCount];
        for (int i = 0; i < stateCount; i++) {
            for (int j = 0; j < stateCount; j++) {
                transitionProbs[i][j] = console.nextDouble();
            }
        }

        int emissionCount = console.nextInt();

        console.nextLine();
        char[] emissionChars = new char[emissionCount];
        for (int i = 0; i < emissionCount; i++) {
            emissionChars[i] = console.nextLine().charAt(0);
        }

        double[][] emissionProbs = new double[stateCount][emissionCount];
        for (int i = 0; i < stateCount; i++) {
            for (int j = 0; j < emissionCount; j++) {
                emissionProbs[i][j] = console.nextDouble();
            }
        }

        console.nextLine();
        String sequence = console.nextLine();

        // Viterbi algorithm

        double[][] table = new double[sequence.length()][stateCount];
        int[][] backtrack = new int[sequence.length()][stateCount];

        // Initialization
        for (int i = 0; i < stateCount; i++) {
            table[0][i] = initialProbs[i] * emissionProbs[i][getEmissionIndex(emissionChars, sequence.charAt(0))];
        }

        // Fill the table
        for (int i = 1; i < sequence.length(); i++) {
            for (int j = 0; j < stateCount; j++) {
                double[] probs = new double[stateCount];
                double max = Double.NEGATIVE_INFINITY;
                // Fill the array and find the max
                for (int k = 0; k < stateCount; k++) {
                    probs[k] = table[i - 1][k] * transitionProbs[k][j];
                    if (probs[k] > max) {
                        max = probs[k];
                    }
                }
                // Record path for backtracking
                table[i][j] = max * emissionProbs[j][getEmissionIndex(emissionChars, sequence.charAt(i))]; // Small optimization: emission probability is independent of the state
                for (int k = 0; k < stateCount; k++) {
                    if (probs[k] == max) {
                        backtrack[i][j] += 1 << k; // encode backtrack paths using bitwise operations
                    }
                }
            }
        }

        // Find max probability
        double max = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < stateCount; i++) {
            if (table[sequence.length() - 1][i] > max) {
                max = table[sequence.length() - 1][i];
            }
        }

        // Backtrack
        List<String> result = new ArrayList<>();
        for (int i = 0; i < stateCount; i++) {
            if (table[sequence.length() - 1][i] == max) {
                result.addAll(traceback(backtrack, sequence.length() - 1, i));
            }
        }
        result.forEach(System.out::println);
    }
}
