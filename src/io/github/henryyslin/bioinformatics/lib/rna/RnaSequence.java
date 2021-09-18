package io.github.henryyslin.bioinformatics.lib.rna;

import io.github.henryyslin.bioinformatics.lib.SequenceBase;
import io.github.henryyslin.bioinformatics.lib.SequenceUtils;
import io.github.henryyslin.bioinformatics.lib.dna.DnaSequence;

public abstract class RnaSequence<T extends RnaSequence<T>> extends SequenceBase<T> {

    public RnaSequence(String sequence) {
        super(sequence);
    }

    public String toAnnotatedString(boolean isReversed) {
        StringBuilder sb = new StringBuilder();

        if (isReversed)
            sb.append("3'-").append(sequence).append("-5'");
        else
            sb.append("5'-").append(sequence).append("-3'");

        return sb.toString();
    }

    protected abstract <TSequence extends DnaSequence<TSequence>> TSequence getDnaCounterPart(String sequence);

    /**
     * Get the original DNA coding strand that give rise to this RNA sequence.
     *
     * @return A new DnaSequence.
     */
    public <TSequence extends DnaSequence<TSequence>> TSequence getCodingDna() {
        return getDnaCounterPart(sequence.replace('U', 'T'));
    }

    /**
     * Get the original DNA template strand that give rise to this RNA sequence.
     *
     * @return A new DnaSequence.
     */
    public <TSequence extends DnaSequence<TSequence>> TSequence getTemplateDna() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sequence.length(); i++) {
            char c = sequence.charAt(i);
            sb.append(SequenceUtils.getUntranscribedChar(c));
        }
        return getDnaCounterPart(sb.toString());
    }
}

