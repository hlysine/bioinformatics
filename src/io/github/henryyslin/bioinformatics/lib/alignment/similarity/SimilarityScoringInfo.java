package io.github.henryyslin.bioinformatics.lib.alignment.similarity;

public class SimilarityScoringInfo {
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
    public SimilarityScoringInfo withMatchScore(int score) {
        matchScore = score;
        return this;
    }

    /**
     * Configures the score added for each mismatch. Defaults to -1.
     *
     * @param score The new score added for each mismatch.
     * @return The same SimpleSimilarity object.
     */
    public SimilarityScoringInfo withMismatchScore(int score) {
        mismatchScore = score;
        return this;
    }

    /**
     * Configures the score added for each indel. Defaults to -2.
     *
     * @param score The new score added for each indel.
     * @return The same SimpleSimilarity object.
     */
    public SimilarityScoringInfo withIndelScore(int score) {
        indelScore = score;
        return this;
    }
}
