package io.github.henryyslin.bioinformatics.lib.dna;

import io.github.henryyslin.bioinformatics.lib.SequenceUtils;
import io.github.henryyslin.bioinformatics.lib.rna.AlignedRnaSequence;

public class AlignedDnaSequence extends DnaSequence<AlignedDnaSequence> {
    public AlignedDnaSequence(String sequence) {
        super(sequence);
    }

    public boolean isValid() {
        for (int i = 0; i < sequence.length(); i++) {
            char c = sequence.charAt(i);
            if (!SequenceUtils.isDnaChar(c) && c != '_') return false;
        }
        return true;
    }

    public AlignedDnaSequence clone() {
        return new AlignedDnaSequence(sequence);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected AlignedRnaSequence getRnaCounterPart(String sequence) {
        return new AlignedRnaSequence(sequence);
    }
}
