package io.github.henryyslin.bioinformatics.assignments;

import java.text.DecimalFormat;

public class Assignment4Question1d {

    static final double ALPHA = 0.02;

    private static int charIndex(char c) {
        return "ACGT".indexOf(c);
    }

    private static char toChar(int idx) {
        return "ACGT".charAt(idx);
    }

    private static String format(double d) {
        if (d < 0.01 && d != 0) {
            return new DecimalFormat("0.#########E0").format(d);
        } else {
            return new DecimalFormat("0.#########").format(d);
        }
    }

    // hard code tree topology
    static final int[] PARENT = {0, 6, 6, 7, 7, 8, 9, 8, 9, 0};
    static final int[][] CHILDREN = {{}, {}, {}, {}, {}, {}, {1, 2}, {3, 4}, {5, 7}, {6, 8}};
    static final char[] NUCLEOTIDE = {'\0', 'C', 'C', 'A', 'T', 'G', '\0', '\0', '\0', '\0'};
    static final double[] BRANCH_LENGTH = {0, 1, 1, 1, 1, 2, 2, 1, 1, 0};

    private static int getParent(int node) {
        return PARENT[node];
    }

    private static int[] getChildren(int node) {
        return CHILDREN[node];
    }

    private static char getNucleotide(int node) {
        return NUCLEOTIDE[node];
    }

    private static double getBranchLength(int node) {
        return BRANCH_LENGTH[node];
    }

    public static void main(String[] args) {
        double[][] matrix = new double[9][4]; // matrix[0] is unused

        System.out.println(",A,C,G,T");
        for (int i = 1; i <= 8; i++) {
            System.out.print("s" + i + ",");
            double branchLength = getBranchLength(i);
            for (int j = 0; j < 4; j++) {
                System.out.print("\"");
                char nucleotide = getNucleotide(i);
                char parent = toChar(j);

                double matchingProb = 3 / 4.0 * Math.pow(1 - 4 * ALPHA, branchLength) + 1 / 4.0;
                String matchingString = "3 / 4 * (1 - 4α) ^ " + format(branchLength) + " + 1 / 4";
                double mismatchProb = 3 / 12.0 - 1 / 4.0 * Math.pow(1 - 4 * ALPHA, branchLength);
                String mismatchString = "3 / 12 - 1 / 4 * (1 - 4α) ^ " + format(branchLength);

                if (nucleotide == '\0') {
                    int[] children = getChildren(i);
                    for (int k = 0; k < 4; k++) {
                        if (j == k) {
                            matrix[i][j] = matchingProb * matrix[children[0]][k] * matrix[children[1]][k];
                            System.out.print(matchingString + " * " + format(matrix[children[0]][k]) + " * " + format(matrix[children[1]][k]));
                        } else {
                            matrix[i][j] = mismatchProb * matrix[children[0]][k] * matrix[children[1]][k];
                            System.out.print(mismatchString + " * " + format(matrix[children[0]][k]) + " * " + format(matrix[children[1]][k]));
                        }
                        if (k != 3) {
                            System.out.print(" + ");
                        }
                    }
                } else {
                    if (nucleotide == parent) {
                        matrix[i][j] = matchingProb;
                        System.out.print(matchingString);
                    } else {
                        matrix[i][j] = mismatchProb;
                        System.out.print(mismatchString);
                    }
                }
                System.out.print("\n= " + format(matrix[i][j]) + "\"");
                if (j != 3) {
                    System.out.print(",");
                }
            }
            System.out.println();
        }

        System.out.println();
        double likelihood = 0;
        System.out.print("Likelihood = ");
        for (int i = 0; i < 4; i++) {
            likelihood += 0.25 * matrix[getChildren(9)[0]][i] * matrix[getChildren(9)[1]][i];
            System.out.print("0.25 * " + format(matrix[getChildren(9)[0]][i]) + " * " + format(matrix[getChildren(9)[1]][i]));
            if (i != 3) {
                System.out.print(" + ");
            }
        }
        System.out.println(" = " + format(likelihood));
    }
}
