package io.github.henryyslin.bioinformatics.lib.alignment;

import io.github.henryyslin.bioinformatics.lib.Sequence;

public interface AlignedSequence<T extends AlignedSequence<T>> extends Sequence<T> {

    /**
     * Get the zero-based inclusive index of the starting position of the aligned sub-sequence.
     *
     * @return The zero-based inclusive index.
     */
    int getStart();

    /**
     * Get the zero-based inclusive index of the ending position of the aligned sub-sequence.
     *
     * @return The zero-based inclusive index.
     */
    int getEnd();

    /**
     * Remove all gap characters in the sequence, converting it to a Complete sequence.
     *
     * @return A Complete sequence of the corresponding type
     */
    Sequence<?> removeGaps();
}
