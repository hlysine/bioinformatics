package io.github.henryyslin.bioinformatics.lib;

import java.util.Objects;

public class RnaSequence {
    private String sequence;

    public RnaSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getSequence() {
        return sequence;
    }

    /**
     * Check whether the sequence string is valid.
     *
     * @return Whether the sequence string is valid.
     */
    public boolean isValid() {
        for (int i = 0; i < sequence.length(); i++) {
            char c = sequence.charAt(i);
            if (!SequenceUtils.isRnaChar(c)) return false;
        }
        return true;
    }

    /**
     * Return the sequence string with direction annotation.
     *
     * @return A sequence string with direction annotation.
     */
    public String toAnnotatedString() {
        return toAnnotatedString(false);
    }

    /**
     * Return the sequence string with direction annotation.
     *
     * @param isReversed Whether this sequence is reversed (i.e. from 3' to 5').
     * @return A sequence string with direction annotation.
     */
    public String toAnnotatedString(boolean isReversed) {
        StringBuilder sb = new StringBuilder();

        if (isReversed)
            sb.append("3'-").append(sequence).append("-5'");
        else
            sb.append("5'-").append(sequence).append("-3'");

        return sb.toString();
    }

    /**
     * Clone this RNA sequence.
     *
     * @return A clone of this RNA sequence.
     */
    public RnaSequence clone() {
        return new RnaSequence(sequence);
    }

    /**
     * Remove a range of this RNA sequence.
     *
     * @param start       The 1-based index to start removing chars.
     * @param deleteCount The number of chars to remove.
     * @return The same RnaSequence with the specified range removed.
     */
    public RnaSequence splice(int start, int deleteCount) {
        sequence = sequence.substring(0, start - 1) + sequence.substring(start + deleteCount - 1);
        return this;
    }

    /**
     * Reverse this RNA sequence.
     *
     * @return The same RnaSequence.
     */
    public RnaSequence reverse() {
        sequence = new StringBuilder(sequence).reverse().toString();
        return this;
    }

    /**
     * Get the original DNA coding strand that give rise to this RNA sequence.
     *
     * @return A new DnaSequence.
     */
    public DnaSequence getCodingDna() {
        return new DnaSequence(sequence.replace('U', 'T'));
    }

    /**
     * Get the original DNA template strand that give rise to this RNA sequence.
     *
     * @return A new DnaSequence.
     */
    public DnaSequence getTemplateDna() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sequence.length(); i++) {
            char c = sequence.charAt(i);
            sb.append(SequenceUtils.getUntranscribedChar(c));
        }
        return new DnaSequence(sb.toString());
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

