package io.github.henryyslin.bioinformatics.lib.alignment.similarity;

import io.github.henryyslin.bioinformatics.lib.alignment.AlignedSequence;

public abstract class SimilarityAlgorithm<T extends AlignedSequence<T>> {
    ScoringScheme scoringScheme;

    public SimilarityAlgorithm(ScoringScheme scoringScheme) {
        this.scoringScheme = scoringScheme;
    }

    public abstract int compute(T sequence1, T sequence2);
}
