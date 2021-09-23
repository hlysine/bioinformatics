package io.github.henryyslin.bioinformatics.lib.alignment.similarity;

public interface SimilarityScoringScheme {
    int getScore(char char1, char char2);
}
