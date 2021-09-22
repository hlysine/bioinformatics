package io.github.henryyslin.bioinformatics.lib.alignment;


import io.github.henryyslin.bioinformatics.lib.SequenceUtils;

public class SequenceAlignmentUtils {
    /**
     * Compares two sequences and check if they are properly aligned.
     * <p>
     * Properly aligned sequences have the same length and don't have gap chars appearing in the same position.
     *
     * @param sequence1 The first sequence.
     * @param sequence2 The second sequence.
     * @return Whether the two sequences are properly aligned.
     */
    public static boolean isValid(AlignedSequence<?> sequence1, AlignedSequence<?> sequence2) {
        String s1 = sequence1.getSequence();
        String s2 = sequence2.getSequence();

        if (s1.length() != s2.length()) return false;

        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) == SequenceUtils.GapChar && s2.charAt(i) == SequenceUtils.GapChar) return false;
        }

        return true;
    }
}
