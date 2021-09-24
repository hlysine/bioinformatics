package io.github.henryyslin.bioinformatics.sandbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class OptimalLocalAlignment {
    private static class Alignment {
        String sequence1;
        String sequence2;

        public Alignment(String sequence1, String sequence2) {
            this.sequence1 = sequence1;
            this.sequence2 = sequence2;
        }
    }

    private static List<Alignment> traceback(String seq1, String seq2, int[][] scoreMatrix, int[][] arrowMatrix, int i, int j) {
        List<Alignment> alignments = new ArrayList<>();
        if (scoreMatrix[i][j] == 0) {
            alignments.add(new Alignment("", ""));
        }
        if ((arrowMatrix[i][j] & 1) > 0) {
            traceback(seq1, seq2, scoreMatrix, arrowMatrix, i, j + 1).forEach(alignment -> {
                alignment.sequence1 = "_" + alignment.sequence1;
                alignment.sequence2 = seq2.charAt(j) + alignment.sequence2;
                alignments.add(alignment);
            });
        }
        if ((arrowMatrix[i][j] & 2) > 0) {
            traceback(seq1, seq2, scoreMatrix, arrowMatrix, i + 1, j + 1).forEach(alignment -> {
                alignment.sequence1 = seq1.charAt(i) + alignment.sequence1;
                alignment.sequence2 = seq2.charAt(j) + alignment.sequence2;
                alignments.add(alignment);
            });
        }
        if ((arrowMatrix[i][j] & 4) > 0) {
            traceback(seq1, seq2, scoreMatrix, arrowMatrix, i + 1, j).forEach(alignment -> {
                alignment.sequence1 = seq1.charAt(i) + alignment.sequence1;
                alignment.sequence2 = "_" + alignment.sequence2;
                alignments.add(alignment);
            });
        }
        return alignments;
    }

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        System.out.print("Match score: ");
        int matchScore = console.nextInt();
        console.nextLine();
        System.out.print("Mismatch score: ");
        int mismatchScore = console.nextInt();
        console.nextLine();
        System.out.print("Indel score: ");
        int indelScore = console.nextInt();
        console.nextLine();
        System.out.print("Sequence 1: ");
        String sequence1 = console.nextLine();
        System.out.print("Sequence 2: ");
        String sequence2 = console.nextLine();

        int[][] scoreMatrix = new int[sequence1.length() + 1][sequence2.length() + 1];
        int[][] arrowMatrix = new int[sequence1.length() + 1][sequence2.length() + 1];

        List<Integer> choices = new ArrayList<>();

        for (int i = sequence1.length(); i >= 0; i--) {
            for (int j = sequence2.length(); j >= 0; j--) {
                if (i == sequence1.length() || j == sequence2.length()) continue;

                choices.clear();
                choices.add(scoreMatrix[i][j + 1] + indelScore);
                choices.add(scoreMatrix[i + 1][j + 1] + (sequence1.charAt(i) == sequence2.charAt(j) ? matchScore : mismatchScore));
                choices.add(scoreMatrix[i + 1][j] + indelScore);
                choices.add(0);

                int max = choices.stream().max(Integer::compare).get();
                scoreMatrix[i][j] = max;
                for (int k = 0; k < choices.size() - 1; k++) {
                    if (choices.get(k) == max) arrowMatrix[i][j] += 1 << k;
                }
            }
        }

        System.out.println("Arrow matrix:");
        for (int[] row : arrowMatrix) {
            for (int col : row) {
                System.out.print(col + " ");
            }
            System.out.println();
        }

        System.out.println();

        System.out.println("Score matrix with paths:");
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

        System.out.println("Optimal alignments:");
        List<Alignment> alignments = new ArrayList<>();
        int max = Arrays.stream(scoreMatrix).map(row -> Arrays.stream(row).max().orElse(0)).max(Integer::compare).get();
        for (int i = 0; i < sequence1.length(); i++) {
            for (int j = 0; j < sequence2.length(); j++) {
                if (scoreMatrix[i][j] == max) {
                    alignments.addAll(traceback(sequence1, sequence2, scoreMatrix, arrowMatrix, i, j));
                }
            }
        }
        for (Alignment alignment : alignments) {
            System.out.println("r=" + alignment.sequence1);
            System.out.println("s=" + alignment.sequence2);
            System.out.println();
        }
    }
}
