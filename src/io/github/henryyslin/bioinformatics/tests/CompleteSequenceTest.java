package io.github.henryyslin.bioinformatics.tests;

import io.github.henryyslin.bioinformatics.lib.dna.CompleteDnaSequence;
import io.github.henryyslin.bioinformatics.lib.rna.CompleteRnaSequence;

public class CompleteSequenceTest {

    public static void main(String[] args) {
        CompleteDnaSequence codingStrand = new CompleteDnaSequence("CACTATCGCTAGTCC");
        CompleteDnaSequence templateStrand = codingStrand.clone().toComplementarySequence();

        System.out.println("Coding strand:                 " + codingStrand.toAnnotatedString());
        System.out.println("Template strand:               " + templateStrand.toAnnotatedString(true));
        System.out.println("Verify coding strand:          " + codingStrand.isValid());
        System.out.println("Verify template strand:        " + templateStrand.isValid());

        CompleteRnaSequence rna = codingStrand.transcribeAsCoding();
        CompleteRnaSequence complementaryRna = codingStrand.transcribeAsTemplate();

        System.out.println("Transcribed RNA:               " + rna.toAnnotatedString());
        System.out.println("Complementary transcribed RNA: " + complementaryRna.toAnnotatedString(true));

        System.out.println("Protein 1:                     " + rna.forceTranslate().toAnnotatedString());
        System.out.println("Protein 2:                     " + rna.clone().splice(0, 1).forceTranslate().toAnnotatedString());
        System.out.println("Protein 3:                     " + rna.clone().splice(0, 2).forceTranslate().toAnnotatedString());

        complementaryRna.reverse();

        System.out.println("Protein 4:                     " + complementaryRna.forceTranslate().reverse().toAnnotatedString(true));
        System.out.println("Protein 5:                     " + complementaryRna.clone().splice(0, 1).forceTranslate().reverse().toAnnotatedString(true));
        System.out.println("Protein 6:                     " + complementaryRna.clone().splice(0, 2).forceTranslate().reverse().toAnnotatedString(true));
    }
}
