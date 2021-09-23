package io.github.henryyslin.bioinformatics.lib.alignment.similarity;

import io.github.henryyslin.bioinformatics.lib.alignment.AlignedSequence;

public class SimilarityFactory<T extends AlignedSequence<T>> {
    ScoringScheme scoringScheme;

    public SimilarityFactory(ScoringScheme scoringScheme) {
        this.scoringScheme = scoringScheme;
    }

    public SimilarityAlgorithm<T> create() throws ClassNotFoundException {
        if (scoringScheme.useAffineGapPenalty()) {
            throw new ClassNotFoundException("No suitable class to utilize the specified scoring scheme - affine gap penalty is not supported.");
        } else {
            return new SimpleSimilarity<>(scoringScheme);
        }
    }
}
