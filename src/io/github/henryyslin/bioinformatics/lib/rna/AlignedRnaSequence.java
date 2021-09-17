package io.github.henryyslin.bioinformatics.lib.rna;

import io.github.henryyslin.bioinformatics.lib.AminoAcidSequence;
import io.github.henryyslin.bioinformatics.lib.Codon;
import io.github.henryyslin.bioinformatics.lib.SequenceUtils;
import io.github.henryyslin.bioinformatics.lib.dna.AlignedDnaSequence;
import io.github.henryyslin.bioinformatics.lib.dna.CompleteDnaSequence;

public class AlignedRnaSequence extends RnaSequence<AlignedRnaSequence> {
    public AlignedRnaSequence(String sequence) {
        super(sequence);
    }

    public boolean isValid() {
        for (int i = 0; i < sequence.length(); i++) {
            char c = sequence.charAt(i);
            if (!SequenceUtils.isRnaChar(c) && c != '_') return false;
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
}
