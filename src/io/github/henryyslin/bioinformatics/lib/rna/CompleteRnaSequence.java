package io.github.henryyslin.bioinformatics.lib.rna;

import io.github.henryyslin.bioinformatics.lib.AminoAcidSequence;
import io.github.henryyslin.bioinformatics.lib.Codon;
import io.github.henryyslin.bioinformatics.lib.SequenceUtils;
import io.github.henryyslin.bioinformatics.lib.dna.CompleteDnaSequence;
import io.github.henryyslin.bioinformatics.lib.dna.DnaSequence;

public class CompleteRnaSequence extends RnaSequence<CompleteRnaSequence> {
    public CompleteRnaSequence(String sequence) {
        super(sequence);
    }

    public boolean isValid() {
        for (int i = 0; i < sequence.length(); i++) {
            char c = sequence.charAt(i);
            if (!SequenceUtils.isRnaChar(c)) return false;
        }
        return true;
    }

    public CompleteRnaSequence clone() {
        return new CompleteRnaSequence(sequence);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected CompleteDnaSequence getDnaCounterPart(String sequence) {
        return new CompleteDnaSequence(sequence);
    }

    /**
     * Forcefully translate this complete RNA sequence into an amino acid sequence. This always starts at the first char and ignores start/stop codons.
     *
     * @return A new AminoAcidSequence.
     */
    public AminoAcidSequence forceTranslate() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sequence.length(); i += 3) {
            if (i + 3 > sequence.length()) continue;
            String str = sequence.substring(i, i + 3);
            sb.append(SequenceUtils.getCodon(str).AminoAcid);
        }
        return new AminoAcidSequence(sb.toString());
    }

    /**
     * Translate this RNA sequence to an amino acid sequence, taking start and stop codons into consideration. The final
     * stop codon is always omitted in the amino acid sequence.
     *
     * @return A new AminoAcidSequence.
     * @throws IllegalStateException A start/stop codon is missing.
     */
    public AminoAcidSequence translate() throws IllegalStateException {
        return translate(false);
    }

    /**
     * Translate this RNA strand to an amino acid sequence, taking start and stop codons into consideration.
     *
     * @param keepStopCodon Whether to keep the stop codon as a '*' char.
     * @return A new AminoAcidSequence.
     * @throws IllegalStateException A start/stop codon is missing.
     */
    public AminoAcidSequence translate(boolean keepStopCodon) throws IllegalStateException {
        int start = sequence.indexOf(SequenceUtils.StartCodon);
        if (start == -1) throw new IllegalStateException("The current sequence does not contain a start codon.");

        StringBuilder sb = new StringBuilder();
        for (int i = start; i < sequence.length(); i += 3) {
            if (i + 3 > sequence.length())
                throw new IllegalStateException("The current sequence has an invalid ending.");
            String str = sequence.substring(i, i + 3);
            Codon codon = SequenceUtils.getCodon(str);
            if (codon.isStopCodon()) {
                if (keepStopCodon) sb.append(codon.AminoAcid);
                return new AminoAcidSequence(sb.toString());
            }
            sb.append(codon.AminoAcid);
        }
        throw new IllegalStateException("The current sequence has no stop codon.");
    }
}
