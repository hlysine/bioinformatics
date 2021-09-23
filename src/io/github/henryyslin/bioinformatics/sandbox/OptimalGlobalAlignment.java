package io.github.henryyslin.bioinformatics.sandbox;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OptimalGlobalAlignment {
    private static class Alignment {
        String sequence1;
        String sequence2;

        public Alignment(String sequence1, String sequence2) {
            this.sequence1 = sequence1;
            this.sequence2 = sequence2;
        }
    }

    private static List<Alignment> traceback(String seq1, String seq2, int[][] arrowMatrix, int i, int j) {
        List<Alignment> alignments = new ArrayList<>();
        if (i == seq1.length() && j == seq2.length()) {
            alignments.add(new Alignment("", ""));
        } else {
            if ((arrowMatrix[i][j] & 1) > 0) {
                traceback(seq1, seq2, arrowMatrix, i, j + 1).forEach(alignment -> {
                    alignment.sequence1 = "_" + alignment.sequence1;
                    alignment.sequence2 = seq2.charAt(j) + alignment.sequence2;
                    alignments.add(alignment);
                });
            }
            if ((arrowMatrix[i][j] & 2) > 0) {
                traceback(seq1, seq2, arrowMatrix, i + 1, j + 1).forEach(alignment -> {
                    alignment.sequence1 = seq1.charAt(i) + alignment.sequence1;
                    alignment.sequence2 = seq2.charAt(j) + alignment.sequence2;
                    alignments.add(alignment);
                });
            }
            if ((arrowMatrix[i][j] & 4) > 0) {
                traceback(seq1, seq2, arrowMatrix, i + 1, j).forEach(alignment -> {
                    alignment.sequence1 = seq1.charAt(i) + alignment.sequence1;
                    alignment.sequence2 = "_" + alignment.sequence2;
                    alignments.add(alignment);
                });
            }
        }
        return alignments;
    }

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

        System.out.println();

        for (int i = 0; i < sequence1.length() + 1; i++) {
            for (int j = 0; j < sequence2.length() + 1; j++) {
                System.out.print(String.format("%1$3s", scoreMatrix[i][j]) + ((arrowMatrix[i][j] & 1) > 0 ? "---" : "   "));
            }
            System.out.println();
            for (int j = 0; j < sequence2.length() + 1; j++) {
                System.out.print(((arrowMatrix[i][j] & 4) > 0 ? "  |" : "   ") + ((arrowMatrix[i][j] & 2) > 0 ? "  \\" : "   "));
            }
            System.out.println();
        }

        List<Alignment> alignments = traceback(sequence1, sequence2, arrowMatrix, 0, 0);
        for (Alignment alignment : alignments) {
            System.out.println("r=" + alignment.sequence1);
            System.out.println("s=" + alignment.sequence2);
            System.out.println();
        }
    }
}
