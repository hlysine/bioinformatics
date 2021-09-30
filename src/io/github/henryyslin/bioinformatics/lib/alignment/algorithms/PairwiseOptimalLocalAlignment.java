package io.github.henryyslin.bioinformatics.lib.alignment.algorithms;

import io.github.henryyslin.bioinformatics.lib.Sequence;
import io.github.henryyslin.bioinformatics.lib.alignment.AlignedSequence;
import io.github.henryyslin.bioinformatics.lib.alignment.Alignment;
import io.github.henryyslin.bioinformatics.lib.alignment.similarity.ScoringScheme;
import io.github.henryyslin.bioinformatics.sandbox.OptimalLocalAlignment;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PairwiseOptimalLocalAlignment<T extends AlignedSequence<T>> extends AlignmentAlgorithm<T> {

    public PairwiseOptimalLocalAlignment(Class<T> cls, ScoringScheme scoringScheme) {
        super(cls, scoringScheme);
    }

    private List<InternalPairwiseAlignment> traceback(String seq1, String seq2, int[][] scoreMatrix, int[][] arrowMatrix, int i, int j) {
        List<InternalPairwiseAlignment> alignments = new ArrayList<>();
        if (scoreMatrix[i][j] == 0) {
            alignments.add(new InternalPairwiseAlignment(new InternalAlignedSequence("", i, i - 1), new InternalAlignedSequence("", j, j - 1)));
        } else {
            if ((arrowMatrix[i][j] & 1) > 0) {
                traceback(seq1, seq2, scoreMatrix, arrowMatrix, i, j + 1).forEach(alignment -> {
                    alignment.sequence1.sequence = "_" + alignment.sequence1.sequence;
                    alignment.sequence2.sequence = seq2.charAt(j) + alignment.sequence2.sequence;
                    alignment.sequence2.start--;
                    alignments.add(alignment);
                });
            }
            if ((arrowMatrix[i][j] & 2) > 0) {
                traceback(seq1, seq2, scoreMatrix, arrowMatrix, i + 1, j + 1).forEach(alignment -> {
                    alignment.sequence1.sequence = seq1.charAt(i) + alignment.sequence1.sequence;
                    alignment.sequence2.sequence = seq2.charAt(j) + alignment.sequence2.sequence;
                    alignment.sequence1.start--;
                    alignment.sequence2.start--;
                    alignments.add(alignment);
                });
            }
            if ((arrowMatrix[i][j] & 4) > 0) {
                traceback(seq1, seq2, scoreMatrix, arrowMatrix, i + 1, j).forEach(alignment -> {
                    alignment.sequence1.sequence = seq1.charAt(i) + alignment.sequence1.sequence;
                    alignment.sequence1.start--;
                    alignment.sequence2.sequence = "_" + alignment.sequence2.sequence;
                    alignments.add(alignment);
                });
            }
        }
        return alignments;
    }

    @Override
    public List<Alignment<T>> compute(List<? extends Sequence<?>> list) {
        if (list.size() != 2)
            throw new InvalidParameterException("A pairwise alignment algorithm can only accept sequence lists of size 2.");
        return compute(list.get(0), list.get(1));
    }

    public List<Alignment<T>> compute(Sequence<?> sequence1, Sequence<?> sequence2) {
        int[][] scoreMatrix = new int[sequence1.length() + 1][sequence2.length() + 1];
        int[][] arrowMatrix = new int[sequence1.length() + 1][sequence2.length() + 1];

        List<Integer> choices = new ArrayList<>();

        for (int i = sequence1.length(); i >= 0; i--) {
            for (int j = sequence2.length(); j >= 0; j--) {
                if (i == sequence1.length() || j == sequence2.length()) continue;

                choices.clear();
                choices.add(scoreMatrix[i][j + 1] + scoringScheme.getScore('_', sequence2.charAt(j)));
                choices.add(scoreMatrix[i + 1][j + 1] + scoringScheme.getScore(sequence1.charAt(i), sequence2.charAt(j)));
                choices.add(scoreMatrix[i + 1][j] + scoringScheme.getScore(sequence1.charAt(i), '_'));
                choices.add(0);

                int max = choices.stream().max(Integer::compare).get();
                scoreMatrix[i][j] = max;
                for (int k = 0; k < choices.size() - 1; k++) {
                    if (choices.get(k) == max) arrowMatrix[i][j] += 1 << k;
                }
            }
        }

        List<InternalPairwiseAlignment> alignments = new ArrayList<>();
        int max = Arrays.stream(scoreMatrix).map(row -> Arrays.stream(row).max().orElse(0)).max(Integer::compare).orElse(0);
        for (int i = 0; i < sequence1.length(); i++) {
            for (int j = 0; j < sequence2.length(); j++) {
                if (scoreMatrix[i][j] == max) {
                    alignments.addAll(traceback(sequence1.getSequence(), sequence2.getSequence(), scoreMatrix, arrowMatrix, i, j));
                }
            }
        }

        return alignments
                .stream()
                .map(alignment -> alignment.packageAlignment(sequenceClass))
                .collect(Collectors.toList());
    }
}
