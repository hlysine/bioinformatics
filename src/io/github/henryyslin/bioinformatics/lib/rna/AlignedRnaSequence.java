package io.github.henryyslin.bioinformatics.lib.rna;

import io.github.henryyslin.bioinformatics.lib.SequenceUtils;
import io.github.henryyslin.bioinformatics.lib.alignment.AlignedSequence;
import io.github.henryyslin.bioinformatics.lib.dna.AlignedDnaSequence;

public class AlignedRnaSequence extends RnaSequence<AlignedRnaSequence> implements AlignedSequence<AlignedRnaSequence> {
    int start;
    int end;

    public AlignedRnaSequence(String sequence) {
        super(sequence);
        start = 0;
        end = sequence.replace(Character.toString(SequenceUtils.GapChar), "").length() - 1;
    }

    public AlignedRnaSequence(String sequence, int start, int end) {
        super(sequence);
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public String toAnnotatedString(boolean isReversed) {
        StringBuilder sb = new StringBuilder();

        if (isReversed)
            sb.append("3'- ").append(start + 1).append(" ").append(sequence).append(" ").append(end + 1).append(" -5'");
        else
            sb.append("5'- ").append(start + 1).append(" ").append(sequence).append(" ").append(end + 1).append(" -3'");

        return sb.toString();
    }

    public boolean isValid() {
        for (int i = 0; i < sequence.length(); i++) {
            char c = sequence.charAt(i);
            if (!SequenceUtils.isRnaChar(c) && c != SequenceUtils.GapChar) return false;
        }
        return true;
    }

    public AlignedRnaSequence clone() {
        return new AlignedRnaSequence(sequence);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected AlignedDnaSequence getDnaCounterPart(String sequence) {
        return new AlignedDnaSequence(sequence);
    }

    public CompleteRnaSequence removeGaps() {
        return new CompleteRnaSequence(sequence.replace(Character.toString(SequenceUtils.GapChar), ""));
    }
}
