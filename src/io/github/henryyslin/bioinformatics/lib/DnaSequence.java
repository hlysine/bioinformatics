package io.github.henryyslin.bioinformatics.lib;

public class DnaSequence {
    private String sequence;

    public DnaSequence(String sequence) {
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
            if (!SequenceUtils.isDnaChar(c)) return false;
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
     * Clone this DNA sequence.
     *
     * @return A clone of this DNA sequence.
     */
    public DnaSequence clone() {
        return new DnaSequence(sequence);
    }

    /**
     * Remove a range of this DNA sequence.
     *
     * @param start       The 1-based index to start removing chars.
     * @param deleteCount The number of chars to remove.
     * @return The same DnaSequence with the specified range removed.
     */
    public DnaSequence splice(int start, int deleteCount) {
        sequence = sequence.substring(0, start - 1) + sequence.substring(start + deleteCount - 1);
        return this;
    }

    /**
     * Reverse this DNA sequence.
     *
     * @return The same DnaSequence.
     */
    public DnaSequence reverse() {
        sequence = new StringBuilder(sequence).reverse().toString();
        return this;
    }

    /**
     * Get the complementary DNA sequence. The sequence is not reversed.
     *
     * @return A new DnaSequence.
     */
    public DnaSequence toComplementarySequence() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sequence.length(); i++) {
            char c = sequence.charAt(i);
            sb.append(SequenceUtils.getComplementaryChar(c));
        }
        return new DnaSequence(sb.toString());
    }

    /**
     * Get the transcribed RNA sequence, treating the current sequence as the coding strand. The sequence is not
     * reversed.
     *
     * @return A new RnaSequence.
     */
    public RnaSequence transcribeAsCoding() {
        return new RnaSequence(sequence.replace('T', 'U'));
    }

    /**
     * Get the transcribed RNA sequence, treating the current sequence as the template strand. The sequence is not
     * reversed.
     *
     * @return A new RnaSequence.
     */
    public RnaSequence transcribeAsTemplate() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sequence.length(); i++) {
            char c = sequence.charAt(i);
            sb.append(SequenceUtils.getTranscribedChar(c));
        }
        return new RnaSequence(sb.toString());
    }
}
