package io.github.henryyslin.bioinformatics.lib.alignment;

import io.github.henryyslin.bioinformatics.lib.alignment.algorithms.AlignmentAlgorithm;
import io.github.henryyslin.bioinformatics.lib.alignment.algorithms.PairwiseOptimalGlobalAlignment;
import io.github.henryyslin.bioinformatics.lib.alignment.similarity.ScoringScheme;

public class AlignmentFactory<T extends AlignedSequence<T>> {
    Class<T> cls;
    ScoringScheme scoringScheme;
    boolean local = false;
    boolean multiple = false;
    boolean heuristic = false;

    public AlignmentFactory(Class<T> cls, ScoringScheme scoringScheme) {
        this.cls = cls;
        this.scoringScheme = scoringScheme;
    }

    public AlignmentFactory<T> useLocal(boolean value) {
        this.local = value;
        return this;
    }

    public AlignmentFactory<T> useLocal() {
        return this.useLocal(true);
    }

    public AlignmentFactory<T> useMultiple(boolean value) {
        this.multiple = value;
        return this;
    }

    public AlignmentFactory<T> useMultiple() {
        return this.useMultiple(true);
    }

    public AlignmentFactory<T> useHeuristic(boolean value) {
        this.heuristic = value;
        return this;
    }

    public AlignmentFactory<T> useHeuristic() {
        return this.useHeuristic(true);
    }

    public AlignmentAlgorithm<T> create() {
        if (!scoringScheme.useAffineGapPenalty() && !local && !multiple && !heuristic) {
            return new PairwiseOptimalGlobalAlignment<>(cls, scoringScheme);
        }
        return null;
    }
}
