package io.github.henryyslin.bioinformatics.assignments;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Assignment1Question1 {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        int matchScore = console.nextInt();
        console.nextLine();
        int mismatchScore = console.nextInt();
        console.nextLine();
        int indelScore = console.nextInt();
        console.nextLine();
        String sequence1 = console.nextLine();
        String sequence2 = console.nextLine();

        int[][] scoreMatrix = new int[sequence1.length() + 1][sequence2.length() + 1];
        int[][] arrowMatrix = new int[sequence1.length() + 1][sequence2.length() + 1];

        List<Integer> choices = new ArrayList<>();

        for (int i = sequence1.length(); i >= 0; i--) {
            for (int j = sequence2.length(); j >= 0; j--) {
                if (i == sequence1.length() && j == sequence2.length()) continue;
                if (i == sequence1.length()) {
                    scoreMatrix[i][j] = scoreMatrix[i][j + 1] + indelScore;
                    arrowMatrix[i][j] += 1;
                } else if (j == sequence2.length()) {
                    scoreMatrix[i][j] = scoreMatrix[i + 1][j] + indelScore;
                    arrowMatrix[i][j] += 4;
                } else {
                    choices.clear();
                    choices.add(scoreMatrix[i][j + 1] + indelScore);
                    choices.add(scoreMatrix[i + 1][j + 1] + (sequence1.charAt(i) == sequence2.charAt(j) ? matchScore : mismatchScore));
                    choices.add(scoreMatrix[i + 1][j] + indelScore);

                    int max = choices.stream().max(Integer::compare).get();
                    scoreMatrix[i][j] = max;
                    for (int k = 0; k < choices.size(); k++) {
                        if (choices.get(k) == max) arrowMatrix[i][j] += 1 << k;
                    }
                }
            }
        }

        for (int[] row : arrowMatrix) {
            for (int col : row) {
                System.out.print(col + " ");
            }
            System.out.println();
        }
    }
}
