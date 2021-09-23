package io.github.henryyslin.bioinformatics.lib.alignment.similarity;

import io.github.henryyslin.bioinformatics.lib.SequenceUtils;

public class SimpleSimilarityScoringScheme implements SimilarityScoringScheme {
    private int matchScore = 1;
    private int mismatchScore = -1;
    private int indelScore = -2;

    public int getMatchScore() {
        return matchScore;
    }

    public int getMismatchScore() {
        return mismatchScore;
    }

    public int getIndelScore() {
        return indelScore;
    }

    /**
     * Configures the score added for each match. Defaults to 1.
     *
     * @param score The new score added for each match.
     * @return The same SimpleSimilarity object.
     */
    public SimpleSimilarityScoringScheme withMatchScore(int score) {
        matchScore = score;
        return this;
    }

    /**
     * Configures the score added for each mismatch. Defaults to -1.
     *
     * @param score The new score added for each mismatch.
     * @return The same SimpleSimilarity object.
     */
    public SimpleSimilarityScoringScheme withMismatchScore(int score) {
        mismatchScore = score;
        return this;
    }

    /**
     * Configures the score added for each indel. Defaults to -2.
     *
     * @param score The new score added for each indel.
     * @return The same SimpleSimilarity object.
     */
    public SimpleSimilarityScoringScheme withIndelScore(int score) {
        indelScore = score;
        return this;
    }

    @Override
    public int getScore(char char1, char char2) {
        if (char1 == char2) return matchScore;
        if (char1 == SequenceUtils.GapChar || char2 == SequenceUtils.GapChar) return indelScore;
        return mismatchScore;
    }
}
