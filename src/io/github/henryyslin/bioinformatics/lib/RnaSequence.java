package io.github.henryyslin.bioinformatics.lib;

public class RnaSequence extends Sequence<RnaSequence> {

    public RnaSequence(String sequence) {
        super(sequence);
    }

    public boolean isValid() {
        for (int i = 0; i < sequence.length(); i++) {
            char c = sequence.charAt(i);
            if (!SequenceUtils.isRnaChar(c)) return false;
        }
        return true;
    }

    public String toAnnotatedString(boolean isReversed) {
        StringBuilder sb = new StringBuilder();

        if (isReversed)
            sb.append("3'-").append(sequence).append("-5'");
        else
            sb.append("5'-").append(sequence).append("-3'");

        return sb.toString();
    }

    public RnaSequence clone() {
        return new RnaSequence(sequence);
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

