package io.github.henryyslin.bioinformatics.lib.alignment.similarity;

public interface ScoringScheme {
    int getScore(char char1, char char2);

    boolean useAffineGapPenalty();

    int getGapOpeningScore();

    int getGapSizeScore();
}
