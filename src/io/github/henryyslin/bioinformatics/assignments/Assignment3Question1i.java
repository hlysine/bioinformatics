package io.github.henryyslin.bioinformatics.assignments;

import java.text.DecimalFormat;

public class Assignment3Question1i {

    private static int charIndex(char c) {
        return "ACGT".indexOf(c);
    }

    private static char toChar(int idx) {
        return "ACGT".charAt(idx);
    }

    private static String format(double d) {
        if (d < 0.01) {
            return new DecimalFormat("0.#########E0").format(d);
        } else {
            return new DecimalFormat("0.#########").format(d);
        }
    }

    public static void main(String[] args) {
        String sequence = "CTCACGACAA";

        double[][] table = new double[sequence.length()][2];
        double[] initialProb = {0.6, 0.4};
        double[][] transitionProb = {{0.2, 0.8}, {0.6, 0.4}};
        double[][] emissionProb = {{0.4, 0.2, 0.3, 0.1}, {0.1, 0.5, 0.1, 0.3}};

        System.out.println(",s1=Purines,s2=Pyrimidines");

        for (int i = 0; i < sequence.length(); i++) {
            int charIdx = charIndex(sequence.charAt(i));
            if (i == 0) {
                table[i][0] = initialProb[0] * emissionProb[0][charIdx];
                table[i][1] = initialProb[1] * emissionProb[1][charIdx];
                System.out.printf("%d,%s*%s=%s,%s*%s=%s%n", i + 1, format(initialProb[0]), format(emissionProb[0][charIdx]), format(table[i][0]), format(initialProb[1]), format(emissionProb[1][charIdx]), format(table[i][1]));
            } else {
                table[i][0] = table[i - 1][0] * transitionProb[0][0] * emissionProb[0][charIdx] + table[i - 1][1] * transitionProb[1][0] * emissionProb[0][charIdx];
                table[i][1] = table[i - 1][0] * transitionProb[0][1] * emissionProb[1][charIdx] + table[i - 1][1] * transitionProb[1][1] * emissionProb[1][charIdx];
                System.out.printf("%d,%s*%s*%s+%s*%s*%s=%s,%s*%s*%s+%s*%s*%s=%s%n", i + 1, format(table[i - 1][0]), format(transitionProb[0][0]), format(emissionProb[0][charIdx]), format(table[i - 1][1]), format(transitionProb[1][0]), format(emissionProb[0][charIdx]), format(table[i][0]), format(table[i - 1][0]), format(transitionProb[0][1]), format(emissionProb[1][charIdx]), format(table[i - 1][1]), format(transitionProb[1][1]), format(emissionProb[1][charIdx]), format(table[i][1]));
            }
        }

        System.out.println(table[sequence.length() - 1][0] + table[sequence.length() - 1][1]);
    }
}
