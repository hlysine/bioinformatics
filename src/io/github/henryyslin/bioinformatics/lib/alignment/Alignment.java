package io.github.henryyslin.bioinformatics.lib.alignment;

import java.util.ArrayList;
import java.util.List;

public class Alignment<T extends AlignedSequence<T>> {
    public List<T> sequences;

    public Alignment(List<T> sequences) {
        this.sequences = sequences;
    }

    public Alignment(T sequence1, T sequence2) {
        this.sequences = new ArrayList<>();
        this.sequences.add(sequence1);
        this.sequences.add(sequence2);
    }

    public T getSequence1() {
        return sequences.get(0);
    }

    public T getSequence2() {
        return sequences.get(1);
    }

    public void setSequence1(T value) {
        sequences.set(0, value);
    }

    public void setSequence2(T value) {
        sequences.set(1, value);
    }
}
