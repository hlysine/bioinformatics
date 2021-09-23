package io.github.henryyslin.bioinformatics.lib.alignment.similarity;

import io.github.henryyslin.bioinformatics.lib.alignment.AlignedSequence;

public abstract class SimilarityAlgorithm<T extends AlignedSequence<T>> {
    SimilarityScoringScheme scoringScheme;

    public SimilarityAlgorithm(SimilarityScoringScheme scoringScheme) {
        this.scoringScheme = scoringScheme;
    }

    public abstract int compute(T sequence1, T sequence2);
}
