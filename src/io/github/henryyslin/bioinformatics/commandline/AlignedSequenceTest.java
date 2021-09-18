package io.github.henryyslin.bioinformatics.commandline;

import io.github.henryyslin.bioinformatics.lib.alignment.SequenceAlignment;
import io.github.henryyslin.bioinformatics.lib.dna.AlignedDnaSequence;
import io.github.henryyslin.bioinformatics.lib.rna.AlignedRnaSequence;

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

        AlignedDnaSequence strand2 = new AlignedDnaSequence("C_CTATCGCTAAT_C");
        AlignedDnaSequence strand3 = new AlignedDnaSequence("CACTA_CGCTATTCC");

        System.out.println("Second strand:                 " + strand2.toAnnotatedString());
        System.out.println("Alignment validity:            " + SequenceAlignment.isValid(codingStrand, strand2));
        System.out.println("Alignment score:               " + SequenceAlignment.simpleSimilarity(codingStrand, strand2).withMatchScore(10).withMismatchScore(-10).withIndelScore(-20).compute());
        System.out.println("Third strand:                  " + strand3.toAnnotatedString());
        System.out.println("Alignment validity:            " + SequenceAlignment.isValid(codingStrand, strand3));
    }
}
