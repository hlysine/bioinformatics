package io.github.henryyslin.bioinformatics.lib.alignment.algorithms;

import io.github.henryyslin.bioinformatics.lib.alignment.AlignedSequence;
import io.github.henryyslin.bioinformatics.lib.alignment.Alignment;

class InternalPairwiseAlignment {
    public InternalAlignedSequence sequence1;
    public InternalAlignedSequence sequence2;

    public InternalPairwiseAlignment(InternalAlignedSequence sequence1, InternalAlignedSequence sequence2) {
        this.sequence1 = sequence1;
        this.sequence2 = sequence2;
    }

    public <T extends AlignedSequence<T>> Alignment<T> packageAlignment(Class<T> sequenceClass) {
        return new Alignment<>(sequence1.packageSequence(sequenceClass), sequence2.packageSequence(sequenceClass));
    }
}
