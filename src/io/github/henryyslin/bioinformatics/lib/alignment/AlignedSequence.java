package io.github.henryyslin.bioinformatics.lib.alignment;

import io.github.henryyslin.bioinformatics.lib.Sequence;

public interface AlignedSequence<T extends AlignedSequence<T>> extends Sequence<T> {
    /**
     * Remove all gap characters in the sequence, converting it to a Complete sequence.
     *
     * @return A Complete sequence of the corresponding type
     */
    Sequence<?> removeGaps();
}
