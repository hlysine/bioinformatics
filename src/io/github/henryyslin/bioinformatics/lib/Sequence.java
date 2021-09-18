package io.github.henryyslin.bioinformatics.lib;

public interface Sequence<T extends Sequence<T>> {
    String getSequence();

    /**
     * Check whether the sequence string is valid.
     *
     * @return Whether the sequence string is valid.
     */
    boolean isValid();

    /**
     * Return the sequence string with direction annotation.
     *
     * @return A sequence string with direction annotation.
     */
    default String toAnnotatedString() {
        return toAnnotatedString(false);
    }

    /**
     * Return the sequence string with direction annotation.
     *
     * @param isReversed Whether this sequence is reversed.
     * @return A sequence string with direction annotation.
     */
    String toAnnotatedString(boolean isReversed);

    /**
     * Clone this sequence.
     *
     * @return A clone of this sequence.
     */
    T clone();

    /**
     * Remove a range of this sequence.
     *
     * @param start       The 0-based index to start removing chars.
     * @param deleteCount The number of chars to remove.
     * @return The same Sequence with the specified range removed.
     */
    T splice(int start, int deleteCount);

    /**
     * Take a sub-sequence, discarding the rest of the sequence.
     *
     * @param start  The 0-based index to start preserving chars.
     * @param length The number of chars to keep.
     * @return The same Sequence with the specified range kept and others removed.
     */
    T subsequence(int start, int length);

    /**
     * Reverse this sequence.
     *
     * @return The same Sequence.
     */
    T reverse();
}
