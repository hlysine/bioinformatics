package io.github.henryyslin.bioinformatics.lib.alignment.similarity;

import io.github.henryyslin.bioinformatics.lib.SequenceUtils;
import io.github.henryyslin.bioinformatics.lib.alignment.AlignedSequence;

public class SimpleSimilarity<T extends AlignedSequence<T>> extends SimilarityAlgorithm<T> {
    public SimpleSimilarity(ScoringScheme scoringScheme) {
        super(scoringScheme);
    }

    public int compute(T sequence1, T sequence2) throws IllegalStateException {
        int score = 0;

        String s1 = sequence1.getSequence();
        String s2 = sequence2.getSequence();

        if (s1.length() != s2.length()) {
            throw new IllegalStateException("Cannot compute alignment score: sequences are not of the same length.");
        }

        for (int i = 0; i < s1.length(); i++) {
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);

            if (c1 == SequenceUtils.GapChar && c2 == SequenceUtils.GapChar) {
                throw new IllegalStateException("Cannot compute alignment score: both sequences have gaps in position " + i + ".");
            }

            score += scoringScheme.getScore(c1, c2);
        }

        return score;
    }
}
