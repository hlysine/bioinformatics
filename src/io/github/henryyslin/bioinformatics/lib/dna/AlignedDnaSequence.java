package io.github.henryyslin.bioinformatics.lib.dna;

import io.github.henryyslin.bioinformatics.lib.SequenceUtils;
import io.github.henryyslin.bioinformatics.lib.alignment.AlignedSequence;
import io.github.henryyslin.bioinformatics.lib.rna.AlignedRnaSequence;

public class AlignedDnaSequence extends DnaSequence<AlignedDnaSequence> implements AlignedSequence<AlignedDnaSequence> {
    public AlignedDnaSequence(String sequence) {
        super(sequence);
    }

    public boolean isValid() {
        for (int i = 0; i < sequence.length(); i++) {
            char c = sequence.charAt(i);
            if (!SequenceUtils.isDnaChar(c) && c != SequenceUtils.GapChar) return false;
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

    public CompleteDnaSequence removeGaps() {
        return new CompleteDnaSequence(sequence.replace(Character.toString(SequenceUtils.GapChar), ""));
    }
}
