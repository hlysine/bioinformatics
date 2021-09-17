package io.github.henryyslin.bioinformatics.lib.dna;

import io.github.henryyslin.bioinformatics.lib.rna.RnaSequence;
import io.github.henryyslin.bioinformatics.lib.Sequence;
import io.github.henryyslin.bioinformatics.lib.SequenceUtils;

public abstract class DnaSequence<T extends DnaSequence<T>> extends Sequence<T> {
    public DnaSequence(String sequence) {
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

    protected abstract <TSequence extends RnaSequence<TSequence>> TSequence getRnaCounterPart(String sequence);

    /**
     * Get the complementary DNA sequence. The sequence is not reversed.
     *
     * @return The same DnaSequence.
     */
    @SuppressWarnings("unchecked")
    public T toComplementarySequence() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sequence.length(); i++) {
            char c = sequence.charAt(i);
            sb.append(SequenceUtils.getComplementaryChar(c));
        }
        sequence = sb.toString();
        return (T)this;
    }

    /**
     * Get the transcribed RNA sequence, treating the current sequence as the coding strand. The sequence is not
     * reversed.
     *
     * @return A new RnaSequence.
     */
    public <TSequence extends RnaSequence<TSequence>> TSequence transcribeAsCoding() {
        return getRnaCounterPart(sequence.replace('T', 'U'));
    }

    /**
     * Get the transcribed RNA sequence, treating the current sequence as the template strand. The sequence is not
     * reversed.
     *
     * @return A new RnaSequence.
     */
    public <TSequence extends RnaSequence<TSequence>> TSequence transcribeAsTemplate() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sequence.length(); i++) {
            char c = sequence.charAt(i);
            sb.append(SequenceUtils.getTranscribedChar(c));
        }
        return getRnaCounterPart(sb.toString());
    }
}
