package io.github.henryyslin.bioinformatics.tests;

import io.github.henryyslin.bioinformatics.lib.alignment.SequenceAlignmentUtils;
import io.github.henryyslin.bioinformatics.lib.alignment.similarity.SimilarityScoringInfo;
import io.github.henryyslin.bioinformatics.lib.alignment.similarity.SimpleSimilarity;
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
        System.out.println("Alignment validity:            " + SequenceAlignmentUtils.isValid(codingStrand, strand2));
        System.out.println("Alignment score:               " + new SimpleSimilarity<AlignedDnaSequence>(new SimilarityScoringInfo().withIndelScore(-20).withMismatchScore(-10).withMatchScore(10)).compute(codingStrand, strand2));
        System.out.println("Third strand:                  " + strand3.toAnnotatedString());
        System.out.println("Alignment validity:            " + SequenceAlignmentUtils.isValid(codingStrand, strand3));
    }
}
