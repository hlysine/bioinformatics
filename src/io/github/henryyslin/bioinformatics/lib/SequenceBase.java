package io.github.henryyslin.bioinformatics.lib;

public abstract class SequenceBase<T extends SequenceBase<T>> implements Sequence<T> {
    protected String sequence;

    public SequenceBase(String sequence) {
        this.sequence = sequence;
    }

    public String getSequence() {
        return sequence;
    }

    public abstract boolean isValid();

    public String toAnnotatedString() {
        return toAnnotatedString(false);
    }

    public abstract String toAnnotatedString(boolean isReversed);

    public abstract T clone();

    @SuppressWarnings("unchecked")
    public T splice(int start, int deleteCount) {
        sequence = sequence.substring(0, start) + sequence.substring(start + deleteCount);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T subsequence(int start, int length) {
        sequence = sequence.substring(start, start + length);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T reverse() {
        sequence = new StringBuilder(sequence).reverse().toString();
        return (T) this;
    }
}
