package io.github.henryyslin.bioinformatics.assignments;

// CSCI3220 2021-22 First Term Assignment 1
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
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Assignment1Question4 {
    /**
     * Perform trace-back using the arrow matrix, returning the number of optimal alignments.
     *
     * @return The number of optimal alignments when tracing back from the specified cell.
     */
    private static int tracebackCount(int[][] scoreMatrix, int[][] arrowMatrix, int i, int j) {
        int alignments = 0;

        // Each cell with value 0 represents the end of a new alignment
        if (scoreMatrix[i][j] == 0) {
            alignments++;
        }

        // Trace back and recurse according to the arrows
        if ((arrowMatrix[i][j] & 1) > 0) {
            alignments += tracebackCount(scoreMatrix, arrowMatrix, i, j + 1);
        }
        if ((arrowMatrix[i][j] & 2) > 0) {
            alignments += tracebackCount(scoreMatrix, arrowMatrix, i + 1, j + 1);
        }
        if ((arrowMatrix[i][j] & 4) > 0) {
            alignments += tracebackCount(scoreMatrix, arrowMatrix, i + 1, j);
        }

        return alignments;
    }

    public static void main(String[] args) {
        // Read data from stdin
        Scanner console = new Scanner(System.in);
        int matchScore = console.nextInt();
        console.nextLine();
        int mismatchScore = console.nextInt();
        console.nextLine();
        int indelScore = console.nextInt();
        console.nextLine();
        String sequence1 = console.nextLine();
        String sequence2 = console.nextLine();

        // Matrices for DP
        int[][] scoreMatrix = new int[sequence1.length() + 1][sequence2.length() + 1];
        int[][] arrowMatrix = new int[sequence1.length() + 1][sequence2.length() + 1];

        // List for storing difference cases in each sub-problem
        List<Integer> choices = new ArrayList<>();

        for (int i = sequence1.length(); i >= 0; i--) {
            for (int j = sequence2.length(); j >= 0; j--) {
                // Keep the score and arrow value at 0 if either one of the sub-sequences is empty
                if (i == sequence1.length() || j == sequence2.length()) continue;

                // Add the different cases
                choices.clear();
                choices.add(scoreMatrix[i][j + 1] + indelScore);
                choices.add(scoreMatrix[i + 1][j + 1] + (sequence1.charAt(i) == sequence2.charAt(j) ? matchScore : mismatchScore));
                choices.add(scoreMatrix[i + 1][j] + indelScore);
                choices.add(0);

                // Find the max case
                int max = choices.stream().max(Integer::compare).get();
                scoreMatrix[i][j] = max;

                // Add arrows for the cases chosen
                // Only iterate until choices.size() - 1 because we don't want to add an arrow for the 0-score case
                for (int k = 0; k < choices.size() - 1; k++) {
                    if (choices.get(k) == max) arrowMatrix[i][j] += 1 << k; // Use a simple bit shift to get 1,2,4
                }
            }
        }

        int alignments = 0;
        // Find the maximum score
        int max = Arrays.stream(scoreMatrix).map(row -> Arrays.stream(row).max().orElse(0)).max(Integer::compare).orElse(0);
        // For each cell with maximum score, trace back and count the number of alignments
        for (int i = 0; i < sequence1.length(); i++) {
            for (int j = 0; j < sequence2.length(); j++) {
                if (scoreMatrix[i][j] == max) {
                    alignments += tracebackCount(scoreMatrix, arrowMatrix, i, j);
                }
            }
        }
        // Print total
        System.out.println(alignments);
    }
}
