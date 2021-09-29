package io.github.henryyslin.bioinformatics.lib.alignment.algorithms;

import io.github.henryyslin.bioinformatics.lib.alignment.AlignedSequence;

class InternalAlignedSequence {
    public int start;
    public int end;
    public String sequence;

    public InternalAlignedSequence(String sequence, int start, int end) {
        this.sequence = sequence;
        this.start = start;
        this.end = end;
    }

    private static <T extends AlignedSequence<T>> T getInstance(Class<T> cls, String sequence, int start, int end) {
        try {
            return cls.getDeclaredConstructor(String.class, int.class, int.class).newInstance(sequence, start, end);
        } catch (Exception ex) {
            return null;
        }
    }

    public <T extends AlignedSequence<T>> T packageSequence(Class<T> sequenceClass) {
        return getInstance(sequenceClass, sequence, start, end);
    }
}
