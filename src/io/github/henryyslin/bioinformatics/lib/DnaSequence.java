package io.github.henryyslin.bioinformatics.lib;

public class DnaSequence extends Sequence<DnaSequence> {
    public DnaSequence(String sequence) {
        super(sequence);
    }

    public boolean isValid() {
        for (int i = 0; i < sequence.length(); i++) {
            char c = sequence.charAt(i);
            if (!SequenceUtils.isDnaChar(c)) return false;
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

    public DnaSequence clone() {
        return new DnaSequence(sequence);
    }

    /**
     * Get the complementary DNA sequence. The sequence is not reversed.
     *
     * @return The same DnaSequence.
     */
    public DnaSequence toComplementarySequence() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sequence.length(); i++) {
            char c = sequence.charAt(i);
            sb.append(SequenceUtils.getComplementaryChar(c));
        }
        sequence = sb.toString();
        return this;
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
