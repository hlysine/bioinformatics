package io.github.henryyslin.bioinformatics.lib;

public abstract class Sequence<T extends Sequence<T>> {
    protected String sequence;

    public Sequence(String sequence) {
        this.sequence = sequence;
    }

    public String getSequence() {
        return sequence;
    }

    /**
     * Check whether the sequence string is valid.
     *
     * @return Whether the sequence string is valid.
     */
    public abstract boolean isValid();

    /**
     * Return the sequence string with direction annotation.
     *
     * @return A sequence string with direction annotation.
     */
    public String toAnnotatedString() {
        return toAnnotatedString(false);
    }

    /**
     * Return the sequence string with direction annotation.
     *
     * @param isReversed Whether this sequence is reversed.
     * @return A sequence string with direction annotation.
     */
    public abstract String toAnnotatedString(boolean isReversed);

    /**
     * Clone this sequence.
     *
     * @return A clone of this sequence.
     */
    public abstract T clone();

    /**
     * Remove a range of this sequence.
     *
     * @param start       The 0-based index to start removing chars.
     * @param deleteCount The number of chars to remove.
     * @return The same Sequence with the specified range removed.
     */
    @SuppressWarnings("unchecked")
    public T splice(int start, int deleteCount) {
        sequence = sequence.substring(0, start) + sequence.substring(start + deleteCount);
        return (T)this;
    }

    /**
     * Reverse this sequence.
     *
     * @return The same Sequence.
     */
    @SuppressWarnings("unchecked")
    public T reverse() {
        sequence = new StringBuilder(sequence).reverse().toString();
        return (T)this;
    }
}
