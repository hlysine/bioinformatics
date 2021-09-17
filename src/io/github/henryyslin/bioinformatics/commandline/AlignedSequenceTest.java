package io.github.henryyslin.bioinformatics.commandline;

import io.github.henryyslin.bioinformatics.lib.dna.AlignedDnaSequence;
import io.github.henryyslin.bioinformatics.lib.dna.CompleteDnaSequence;
import io.github.henryyslin.bioinformatics.lib.rna.AlignedRnaSequence;
import io.github.henryyslin.bioinformatics.lib.rna.CompleteRnaSequence;

public class AlignedSequenceTest {

    public static void main(String[] args) {
        AlignedDnaSequence codingStrand = new AlignedDnaSequence("CACTA_CGCTA_TCC");
        AlignedDnaSequence templateStrand = codingStrand.clone().toComplementarySequence();

        System.out.println("Coding strand:                 " + codingStrand.toAnnotatedString());
        System.out.println("Template strand:               " + templateStrand.toAnnotatedString(true));
        System.out.println("Verify coding strand:          " + codingStrand.isValid());
        System.out.println("Verify template strand:        " + templateStrand.isValid());

        AlignedRnaSequence rna = codingStrand.transcribeAsCoding();
        AlignedRnaSequence complementaryRna = codingStrand.transcribeAsTemplate();

        System.out.println("Transcribed RNA:               " + rna.toAnnotatedString());
        System.out.println("Complementary transcribed RNA: " + complementaryRna.toAnnotatedString(true));
    }
}
