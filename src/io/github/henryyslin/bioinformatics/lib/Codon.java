package io.github.henryyslin.bioinformatics.lib;

public final class Codon {
    public final String Rna;
    public final String AminoAcid;

    public Codon(String rna, String aminoAcid) {
        Rna = rna;
        AminoAcid = aminoAcid;
    }

    public boolean isStartCodon() {
        return Rna.equals(SequenceUtils.StartCodon);
    }

    public boolean isStopCodon() {
        return AminoAcid.equals(SequenceUtils.StopCodonSymbol);
    }
}
