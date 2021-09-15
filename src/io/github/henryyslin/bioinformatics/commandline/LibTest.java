package io.github.henryyslin.bioinformatics.commandline;

import io.github.henryyslin.bioinformatics.lib.DnaSequence;
import io.github.henryyslin.bioinformatics.lib.RnaSequence;

public class LibTest {

    public static void main(String[] args) {
        DnaSequence codingStrand = new DnaSequence("CACTATCGCTAGTCC");
        DnaSequence templateStrand = codingStrand.clone().toComplementarySequence();

        System.out.println("Coding strand:                 " + codingStrand.toAnnotatedString());
        System.out.println("Template strand:               " + templateStrand.toAnnotatedString(true));
        System.out.println("Verify coding strand:          " + codingStrand.isValid());
        System.out.println("Verify template strand:        " + templateStrand.isValid());

        RnaSequence rna = codingStrand.transcribeAsCoding();
        RnaSequence complementaryRna = codingStrand.transcribeAsTemplate();

        System.out.println("Transcribed RNA:               " + rna.toAnnotatedString());
        System.out.println("Complementary transcribed RNA: " + complementaryRna.toAnnotatedString(true));

        System.out.println("Protein 1:                     " + rna.forceTranslate().toAnnotatedString());
        System.out.println("Protein 2:                     " + rna.clone().splice(1, 1).forceTranslate().toAnnotatedString());
        System.out.println("Protein 3:                     " + rna.clone().splice(1, 2).forceTranslate().toAnnotatedString());

        complementaryRna.reverse();

        System.out.println("Protein 4:                     " + complementaryRna.forceTranslate().reverse().toAnnotatedString(true));
        System.out.println("Protein 5:                     " + complementaryRna.clone().splice(1, 1).forceTranslate().reverse().toAnnotatedString(true));
        System.out.println("Protein 6:                     " + complementaryRna.clone().splice(1, 2).forceTranslate().reverse().toAnnotatedString(true));
    }
}
