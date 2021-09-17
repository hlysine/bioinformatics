package io.github.henryyslin.bioinformatics.lib;

public class AminoAcidSequence extends Sequence<AminoAcidSequence> {

    public AminoAcidSequence(String sequence) {
        super(sequence);
    }

    public boolean isValid() {
        for (int i = 0; i < sequence.length(); i++) {
            char c = sequence.charAt(i);
            if (!SequenceUtils.isAminoAcidChar(c)) return false;
        }
        return true;
    }

    public String toAnnotatedString(boolean isReversed) {
        StringBuilder sb = new StringBuilder();

        if (isReversed)
            sb.append("COOH-").append(sequence).append("-NH3");
        else
            sb.append("NH3-").append(sequence).append("-COOH");

        return sb.toString();
    }

    public AminoAcidSequence clone() {
        return new AminoAcidSequence(sequence);
    }
}
