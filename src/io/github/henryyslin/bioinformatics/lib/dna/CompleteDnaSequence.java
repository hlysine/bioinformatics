package io.github.henryyslin.bioinformatics.lib.dna;

import io.github.henryyslin.bioinformatics.lib.SequenceUtils;
import io.github.henryyslin.bioinformatics.lib.rna.CompleteRnaSequence;

public class CompleteDnaSequence extends DnaSequence<CompleteDnaSequence> {
    public CompleteDnaSequence(String sequence) {
        super(sequence);
    }

    public boolean isValid() {
        for (int i = 0; i < sequence.length(); i++) {
            char c = sequence.charAt(i);
            if (!SequenceUtils.isDnaChar(c)) return false;
        }
        return true;
    }

    public CompleteDnaSequence clone() {
        return new CompleteDnaSequence(sequence);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected CompleteRnaSequence getRnaCounterPart(String sequence) {
        return new CompleteRnaSequence(sequence);
    }
}
