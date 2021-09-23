package io.github.henryyslin.bioinformatics.lib.alignment;

import io.github.henryyslin.bioinformatics.lib.Sequence;
import io.github.henryyslin.bioinformatics.lib.alignment.similarity.ScoringScheme;

import java.util.List;

public abstract class AlignmentAlgorithm<T extends AlignedSequence<T>> {
    ScoringScheme scoringScheme;
    Class<T> cls;

    public AlignmentAlgorithm(Class<T> cls, ScoringScheme scoringScheme) {
        this.cls = cls;
        this.scoringScheme = scoringScheme;
    }

    public abstract List<Alignment<T>> compute(List<? extends Sequence<?>> sequences);
}
