package io.github.henryyslin.bioinformatics.lib.alignment.similarity;

import io.github.henryyslin.bioinformatics.lib.alignment.AlignedSequence;

public abstract class SimilarityAlgorithm<T extends AlignedSequence<T>> {
    SimilarityScoringInfo scoringInfo;

    public SimilarityAlgorithm(SimilarityScoringInfo scoringInfo) {
        this.scoringInfo = scoringInfo;
    }

    public abstract int compute(T sequence1, T sequence2);
}
