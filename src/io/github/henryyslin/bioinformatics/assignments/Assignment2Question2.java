package io.github.henryyslin.bioinformatics.assignments;

import java.util.Scanner;

public class Assignment2Question2 {
    /**
     * Convert a DNA sequence character to its representative index in bwt-related data structures.
     *
     * @param c The character to convert.
     * @return The index, -1 if the character is unexpected.
     */
    private static int charIndex(char c) {
        switch (c) {
            case '$':
                return 0;
            case 'A':
                return 1;
            case 'C':
                return 2;
            case 'G':
                return 3;
            case 'T':
                return 4;
            default:
                return -1;
        }
    }

    /**
     * Return the run-length encoding of a string.
     *
     * @param original The original string.
     * @return The run-length encoded string.
     */
    private static String runLength(String original) {
        if (original == null || original.length() == 0) return original;

        char lastChar = original.charAt(0);
        int count = 1;
        StringBuilder result = new StringBuilder();
        for (int i = 1; i < original.length(); i++) {
            char c = original.charAt(i);
            if (c == lastChar) {
                count++;
            } else {
                result.append(lastChar).append(count);
                lastChar = c;
                count = 1;
            }
        }
        result.append(lastChar).append(count);
        return result.toString();
    }

    /**
     * Given the BWT output of a DNA sequence, retrieve the original sequence.
     * @param bwt The BWT output of a DNA sequence.
     * @return The original DNA sequence.
     */
    private static String revertBwt(String bwt) {
        // The occurrences matrix.
        int[][] occurrences = new int[bwt.length()][5];

        // Fill in the matrix column by column.
        occurrences[0][charIndex(bwt.charAt(0))]++;
        for (int i = 1; i < bwt.length(); i++) {
            System.arraycopy(occurrences[i - 1], 0, occurrences[i], 0, 5);
            occurrences[i][charIndex(bwt.charAt(i))]++;
        }

        // The zero-based index of the first occurrence of a character in the BWT sequence.
        int[] firstOccIndex = new int[5];

        // Fill in the array using the last column of `occurrences`.
        firstOccIndex[0] = 0;
        for (int i = 1; i < 5; i++) {
            firstOccIndex[i] = firstOccIndex[i - 1] + occurrences[bwt.length() - 1][i - 1];
        }

        // Trace the original sequence backwards, starting from '$'.
        StringBuilder result = new StringBuilder();

        char target = '$';
        int index = 0;

        for (int i = 0; i < bwt.length(); i++) {
            result.insert(0, target);

            target = bwt.charAt(index);
            index = firstOccIndex[charIndex(target)] + occurrences[index][charIndex(target)] - 1;
        }

        return result.toString();
    }

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        String bwt = console.nextLine();
        System.out.println(bwt.length() + "," + runLength(revertBwt(bwt)).length() + "," + runLength(bwt).length());
    }
}
