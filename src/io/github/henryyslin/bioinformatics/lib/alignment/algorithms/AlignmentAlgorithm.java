package io.github.henryyslin.bioinformatics.lib.alignment.algorithms;

import io.github.henryyslin.bioinformatics.lib.Sequence;
import io.github.henryyslin.bioinformatics.lib.alignment.AlignedSequence;
import io.github.henryyslin.bioinformatics.lib.alignment.Alignment;
import io.github.henryyslin.bioinformatics.lib.alignment.similarity.ScoringScheme;

import java.util.List;

public abstract class AlignmentAlgorithm<T extends AlignedSequence<T>> {
    ScoringScheme scoringScheme;
    Class<T> sequenceClass;

    public AlignmentAlgorithm(Class<T> sequenceClass, ScoringScheme scoringScheme) {
        this.sequenceClass = sequenceClass;
        this.scoringScheme = scoringScheme;
    }

    public abstract List<Alignment<T>> compute(List<? extends Sequence<?>> sequences);
}
