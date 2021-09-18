package io.github.henryyslin.bioinformatics.lib.rna;

import io.github.henryyslin.bioinformatics.lib.SequenceUtils;
import io.github.henryyslin.bioinformatics.lib.alignment.AlignedSequence;
import io.github.henryyslin.bioinformatics.lib.dna.AlignedDnaSequence;

public class AlignedRnaSequence extends RnaSequence<AlignedRnaSequence> implements AlignedSequence<AlignedRnaSequence> {
    public AlignedRnaSequence(String sequence) {
        super(sequence);
    }

    public boolean isValid() {
        for (int i = 0; i < sequence.length(); i++) {
            char c = sequence.charAt(i);
            if (!SequenceUtils.isRnaChar(c) && c != SequenceUtils.GapChar) return false;
        }
        return true;
    }

    public AlignedRnaSequence clone() {
        return new AlignedRnaSequence(sequence);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected AlignedDnaSequence getDnaCounterPart(String sequence) {
        return new AlignedDnaSequence(sequence);
    }

    public CompleteRnaSequence removeGaps() {
        return new CompleteRnaSequence(sequence.replace(Character.toString(SequenceUtils.GapChar), ""));
    }
}
