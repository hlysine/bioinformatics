package io.github.henryyslin.bioinformatics.lib.alignment.similarity;

public abstract class BaseScoringScheme implements ScoringScheme {
    boolean affineGapPenalty = false;
    int gapOpeningScore;
    int gapSizeScore;

    @Override
    public abstract int getScore(char char1, char char2);

    @Override
    public boolean useAffineGapPenalty() {
        return affineGapPenalty;
    }

    public BaseScoringScheme useAffineGapPenalty(boolean value) {
        affineGapPenalty = value;
        return this;
    }

    @Override
    public int getGapOpeningScore() {
        return gapOpeningScore;
    }

    public BaseScoringScheme withGapOpeningScore(int value) {
        gapOpeningScore = value;
        return this;
    }

    @Override
    public int getGapSizeScore() {
        return gapSizeScore;
    }

    public BaseScoringScheme withGapSizeScore(int value) {
        gapSizeScore = value;
        return this;
    }
}
