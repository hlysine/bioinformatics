package io.github.henryyslin.bioinformatics.lib;

public class AminoAcidSequence {
    private String sequence;

    public AminoAcidSequence(String sequence) {
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
            if (!SequenceUtils.isAminoAcidChar(c)) return false;
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
     * @param isReversed Whether this sequence is reversed (i.e. from C terminal to N terminal).
     * @return A sequence string with direction annotation.
     */
    public String toAnnotatedString(boolean isReversed) {
        StringBuilder sb = new StringBuilder();

        if (isReversed)
            sb.append("COOH-").append(sequence).append("-NH3");
        else
            sb.append("NH3-").append(sequence).append("-COOH");

        return sb.toString();
    }

    /**
     * Clone this amino acid sequence.
     *
     * @return A clone of this AminoAcidSequence.
     */
    public AminoAcidSequence clone() {
        return new AminoAcidSequence(sequence);
    }

    /**
     * Remove a range of this amino acid sequence.
     *
     * @param start       The 1-based index to start removing chars.
     * @param deleteCount The number of chars to remove.
     * @return The same AminoAcidSequence.
     */
    public AminoAcidSequence splice(int start, int deleteCount) {
        sequence = sequence.substring(0, start - 1) + sequence.substring(start + deleteCount - 1);
        return this;
    }

    /**
     * Reverse this amino acid sequence.
     *
     * @return The same AminoAcidSequence.
     */
    public AminoAcidSequence reverse() {
        sequence = new StringBuilder(sequence).reverse().toString();
        return this;
    }
}
