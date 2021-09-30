package io.github.henryyslin.bioinformatics.tests;

import io.github.henryyslin.bioinformatics.lib.alignment.Alignment;
import io.github.henryyslin.bioinformatics.lib.alignment.AlignmentFactory;
import io.github.henryyslin.bioinformatics.lib.alignment.similarity.ScoringScheme;
import io.github.henryyslin.bioinformatics.lib.alignment.similarity.SimpleScoringScheme;
import io.github.henryyslin.bioinformatics.lib.dna.AlignedDnaSequence;
import io.github.henryyslin.bioinformatics.lib.dna.CompleteDnaSequence;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PairwiseOptimalLocalAlignment {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        System.out.print("Match score: ");
        int matchScore = console.nextInt();
        console.nextLine();
        System.out.print("Mismatch score: ");
        int mismatchScore = console.nextInt();
        console.nextLine();
        System.out.print("Indel score: ");
        int indelScore = console.nextInt();
        console.nextLine();

        List<CompleteDnaSequence> sequences = new ArrayList<>();

        System.out.print("Sequence 1: ");
        sequences.add(new CompleteDnaSequence(console.nextLine()));
        System.out.print("Sequence 2: ");
        sequences.add(new CompleteDnaSequence(console.nextLine()));

        ScoringScheme scoringScheme = new SimpleScoringScheme()
                .withMatchScore(matchScore)
                .withMismatchScore(mismatchScore)
                .withIndelScore(indelScore);
        List<Alignment<AlignedDnaSequence>> alignments = new AlignmentFactory<>(AlignedDnaSequence.class, scoringScheme).useLocal()
                .create().compute(sequences);


        System.out.println("Optimal alignments:");
        for (Alignment<AlignedDnaSequence> alignment : alignments) {
            System.out.println("r=" + alignment.getSequence1().toAnnotatedString());
            System.out.println("s=" + alignment.getSequence2().toAnnotatedString());
            System.out.println();
        }
    }
}
