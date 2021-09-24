package io.github.henryyslin.bioinformatics.lib.alignment;

class InternalPairwiseAlignment {
    public String sequence1;
    public String sequence2;

    public InternalPairwiseAlignment(String sequence1, String sequence2) {
        this.sequence1 = sequence1;
        this.sequence2 = sequence2;
    }

    private <T extends AlignedSequence<T>> T getInstance(Class<T> cls, String sequence) {
        try {
            return cls.getDeclaredConstructor(String.class).newInstance(sequence);
        } catch (Exception ex) {
            return null;
        }
    }

    public <T extends AlignedSequence<T>> Alignment<T> packageAlignment(Class<T> sequenceClass) {
        return new Alignment<>(getInstance(sequenceClass, sequence1), getInstance(sequenceClass, sequence2));
    }
}
