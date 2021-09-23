package io.github.henryyslin.bioinformatics.lib.alignment;

import io.github.henryyslin.bioinformatics.lib.Sequence;
import io.github.henryyslin.bioinformatics.lib.alignment.similarity.ScoringScheme;
import io.github.henryyslin.bioinformatics.sandbox.OptimalGlobalAlignment;

import java.lang.reflect.InvocationTargetException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class PairwiseOptimalGlobalAlignment<T extends AlignedSequence<T>> extends AlignmentAlgorithm<T> {

    public PairwiseOptimalGlobalAlignment(Class<T> cls, ScoringScheme scoringScheme) {
        super(cls, scoringScheme);
    }

    private T createSequence(String sequence) {
        try {
            return cls.getDeclaredConstructor(String.class).newInstance(sequence);
        } catch (Exception ex) {
            return null;
        }
    }

    private List<Alignment<T>> traceback(Sequence<?> seq1, Sequence<?> seq2, int[][] arrowMatrix, int i, int j) {
        List<Alignment<T>> alignments = new ArrayList<>();
        if (i == seq1.length() && j == seq2.length()) {
            alignments.add(new Alignment<>(createSequence(""), createSequence("")));
        } else {
            if ((arrowMatrix[i][j] & 1) > 0) {
                traceback(seq1, seq2, arrowMatrix, i, j + 1).forEach(alignment -> {
                    alignment.setSequence1(createSequence("_" + alignment.getSequence1().getSequence()));
                    alignment.setSequence2(createSequence(seq2.charAt(j) + alignment.getSequence2().getSequence()));
                    alignments.add(alignment);
                });
            }
            if ((arrowMatrix[i][j] & 2) > 0) {
                traceback(seq1, seq2, arrowMatrix, i + 1, j + 1).forEach(alignment -> {
                    alignment.setSequence1(createSequence(seq1.charAt(i) + alignment.getSequence1().getSequence()));
                    alignment.setSequence2(createSequence(seq2.charAt(j) + alignment.getSequence2().getSequence()));
                    alignments.add(alignment);
                });
            }
            if ((arrowMatrix[i][j] & 4) > 0) {
                traceback(seq1, seq2, arrowMatrix, i + 1, j).forEach(alignment -> {
                    alignment.setSequence1(createSequence(seq1.charAt(i) + alignment.getSequence1().getSequence()));
                    alignment.setSequence2(createSequence("_" + alignment.getSequence2().getSequence()));
                    alignments.add(alignment);
                });
            }
        }
        return alignments;
    }

    @Override
    public List<Alignment<T>> compute(List<? extends Sequence<?>> list) {
        if (list.size() != 2) throw new InvalidParameterException("A pairwise alignment algorithm can only accept sequence lists of size 2.");
        return compute(list.get(0), list.get(1));
    }

    public List<Alignment<T>> compute(Sequence<?> sequence1, Sequence<?> sequence2) {
        int[][] scoreMatrix = new int[sequence1.length() + 1][sequence2.length() + 1];
        int[][] arrowMatrix = new int[sequence1.length() + 1][sequence2.length() + 1];

        List<Integer> choices = new ArrayList<>();

        for (int i = sequence1.length(); i >= 0; i--) {
            for (int j = sequence2.length(); j >= 0; j--) {
                if (i == sequence1.length() && j == sequence2.length()) continue;
                if (i == sequence1.length()) {
                    scoreMatrix[i][j] = scoreMatrix[i][j + 1] + scoringScheme.getScore('_', sequence2.charAt(j));
                    arrowMatrix[i][j] += 1;
                } else if (j == sequence2.length()) {
                    scoreMatrix[i][j] = scoreMatrix[i + 1][j] + scoringScheme.getScore(sequence1.charAt(i), '_');
                    arrowMatrix[i][j] += 4;
                } else {
                    choices.clear();
                    choices.add(scoreMatrix[i][j + 1] + scoringScheme.getScore('_', sequence2.charAt(j)));
                    choices.add(scoreMatrix[i + 1][j + 1] + scoringScheme.getScore(sequence1.charAt(i), sequence2.charAt(j)));
                    choices.add(scoreMatrix[i + 1][j] + scoringScheme.getScore(sequence1.charAt(i), '_'));

                    int max = choices.stream().max(Integer::compare).get();
                    scoreMatrix[i][j] = max;
                    for (int k = 0; k < choices.size(); k++) {
                        if (choices.get(k) == max) arrowMatrix[i][j] += 1 << k;
                    }
                }
            }
        }

        return traceback(sequence1, sequence2, arrowMatrix, 0, 0);
    }
}
