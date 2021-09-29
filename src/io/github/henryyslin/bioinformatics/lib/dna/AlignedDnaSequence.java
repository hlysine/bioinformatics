package io.github.henryyslin.bioinformatics.lib.dna;

import io.github.henryyslin.bioinformatics.lib.SequenceUtils;
import io.github.henryyslin.bioinformatics.lib.alignment.AlignedSequence;
import io.github.henryyslin.bioinformatics.lib.rna.AlignedRnaSequence;

public class AlignedDnaSequence extends DnaSequence<AlignedDnaSequence> implements AlignedSequence<AlignedDnaSequence> {
    int start;
    int end;

    public AlignedDnaSequence(String sequence) {
        super(sequence);
        start = 0;
        end = sequence.replace(Character.toString(SequenceUtils.GapChar), "").length() - 1;
    }

    public AlignedDnaSequence(String sequence, int start, int end) {
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
            if (!SequenceUtils.isDnaChar(c) && c != SequenceUtils.GapChar) return false;
        }
        return true;
    }

    public AlignedDnaSequence clone() {
        return new AlignedDnaSequence(sequence);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected AlignedRnaSequence getRnaCounterPart(String sequence) {
        return new AlignedRnaSequence(sequence);
    }

    public CompleteDnaSequence removeGaps() {
        return new CompleteDnaSequence(sequence.replace(Character.toString(SequenceUtils.GapChar), ""));
    }
}
