package io.github.henryyslin.bioinformatics.lib.alignment;


import io.github.henryyslin.bioinformatics.lib.SequenceUtils;

public class SequenceAlignment {
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

    /**
     * Computes the similarity between two aligned sequences using a simple, configurable algorithm.
     *
     * @param sequence1 The first sequence.
     * @param sequence2 The second sequence.
     * @param <T>       The sequence type.
     * @return A configurable object that is responsible for the similarity calculation.
     */
    public static <T extends AlignedSequence<T>> SimpleSimilarity<T> simpleSimilarity(T sequence1, T sequence2) {
        return new SimpleSimilarity<>(sequence1, sequence2);
    }

    public static class SimpleSimilarity<T extends AlignedSequence<T>> {
        T sequence1;
        T sequence2;
        int matchScore = 1;
        int mismatchScore = -1;
        int indelScore = -2;

        private SimpleSimilarity(T sequence1, T sequence2) {
            this.sequence1 = sequence1;
            this.sequence2 = sequence2;
        }

        /**
         * Configures the score added for each match. Defaults to 1.
         *
         * @param score The new score added for each match.
         * @return The same SimpleSimilarity object.
         */
        public SimpleSimilarity<T> withMatchScore(int score) {
            matchScore = score;
            return this;
        }

        /**
         * Configures the score added for each mismatch. Defaults to -1.
         *
         * @param score The new score added for each mismatch.
         * @return The same SimpleSimilarity object.
         */
        public SimpleSimilarity<T> withMismatchScore(int score) {
            mismatchScore = score;
            return this;
        }

        /**
         * Configures the score added for each indel. Defaults to -2.
         *
         * @param score The new score added for each indel.
         * @return The same SimpleSimilarity object.
         */
        public SimpleSimilarity<T> withIndelScore(int score) {
            indelScore = score;
            return this;
        }

        public int compute() throws IllegalStateException {
            int score = 0;

            String s1 = sequence1.getSequence();
            String s2 = sequence2.getSequence();

            if (s1.length() != s2.length()) {
                throw new IllegalStateException("Cannot compute alignment score: sequences are not of the same lengnth.");
            }

            for (int i = 0; i < s1.length(); i++) {
                char c1 = s1.charAt(i);
                char c2 = s2.charAt(i);

                if (c1 == SequenceUtils.GapChar && c2 == SequenceUtils.GapChar) {
                    throw new IllegalStateException("Cannot compute alignment score: both sequences have gaps in position " + i + ".");
                }

                if (c1 == SequenceUtils.GapChar || c2 == SequenceUtils.GapChar) {
                    score += indelScore;
                } else if (c1 != c2) {
                    score += mismatchScore;
                } else {
                    score += matchScore;
                }
            }

            return score;
        }
    }
}
