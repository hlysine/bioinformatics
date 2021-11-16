package io.github.henryyslin.bioinformatics.assignments;

import java.util.HashMap;

public class Assignment3Question1 {

    private static final HashMap<String, Character> DEGENERATE_NUCLEOTIDES = new HashMap<String, Character>() {{
        put("A", 'A');
        put("C", 'C');
        put("G", 'G');
        put("T", 'T');
        put("AG", 'R');
        put("CT", 'Y');
        put("CG", 'S');
        put("AT", 'W');
        put("GT", 'K');
        put("AC", 'M');
        put("CGT", 'B');
        put("AGT", 'D');
        put("ACT", 'H');
        put("ACG", 'V');
        put("ACGT", 'N');
    }};

    private static int charIndex(char c) {
        return "ACGT".indexOf(c);
    }

    private static char toChar(int idx) {
        return "ACGT".charAt(idx);
    }

    private static String consensusSequence(String[] sequences) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < sequences[0].length(); i++) {
            int[] counts = new int[4];
            for (String sequence : sequences) {
                int charIdx = charIndex(sequence.charAt(i));
                if (charIdx >= 0)
                    counts[charIndex(sequence.charAt(i))]++;
            }
            int maxIdx = 0;
            for (int j = 1; j < counts.length; j++) {
                if (counts[j] > counts[maxIdx])
                    maxIdx = j;
            }
            result.append(toChar(maxIdx));
        }
        return result.toString();
    }

    private static String degenerateSequence(String[] sequences) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < sequences[0].length(); i++) {
            int[] counts = new int[4];
            for (String sequence : sequences) {
                int charIdx = charIndex(sequence.charAt(i));
                if (charIdx >= 0)
                    counts[charIndex(sequence.charAt(i))]++;
            }
            StringBuilder degenerateKey = new StringBuilder();
            for (int j = 0; j < counts.length; j++) {
                if (counts[j] > 1) {
                    degenerateKey.append(toChar(j));
                }
            }
            result.append(DEGENERATE_NUCLEOTIDES.get(degenerateKey.toString()));
        }
        return result.toString();
    }

    private static int[][] positionWeightMatrix(String[] sequences) {
        int[][] result = new int[4][sequences[0].length()];
        for (int i = 0; i < sequences[0].length(); i++) {
            for (String sequence : sequences) {
                int charIdx = charIndex(sequence.charAt(i));
                if (charIdx >= 0)
                    result[charIdx][i]++;
            }
            // add pseudo-counts
            for (int j = 0; j < result.length; j++) {
                result[j][i] += 1;
            }
        }
        return result;
    }

    private static void printPositionWeightMatrix(int[][] positionWeightMatrix) {
        System.out.print("  ");
        for (int i = 0; i < positionWeightMatrix[0].length; i++) {
            System.out.printf("%4d ", i + 1);
        }
        System.out.println();
        for (int i = 0; i < positionWeightMatrix.length; i++) {
            int[] weightMatrix = positionWeightMatrix[i];
            System.out.print(toChar(i) + " ");
            for (int j = 0; j < weightMatrix.length; j++) {
                int sum = 0;
                for (int[] row : positionWeightMatrix) {
                    sum += row[j];
                }
                System.out.print(weightMatrix[j] + "/" + sum + " ");
            }
            System.out.println();
        }
    }

    private static HashMap<String, Integer> twoMerSpectra(String sequence) {
        HashMap<String, Integer> result = new HashMap<>();
        for (int i = 0; i < sequence.length() - 1; i++) {
            String twoMer = sequence.substring(i, i + 2);
            if (result.containsKey(twoMer)) {
                result.put(twoMer, result.get(twoMer) + 1);
            } else {
                result.put(twoMer, 1);
            }
        }
        return result;
    }

    private static int twoMerInnerProduct(HashMap<String, Integer> spectrum1, HashMap<String, Integer> spectrum2) {
        int result = 0;
        for (String twoMer : spectrum1.keySet()) {
            if (spectrum2.containsKey(twoMer)) {
                result += spectrum1.get(twoMer) * spectrum2.get(twoMer);
            }
        }
        return result;
    }

    // produce a similarity matrix of a list of sequences, where the similarity between two sequences is their inner product of two-mer spectra
    private static int[][] similarityMatrix(String[] sequences) {
        int[][] result = new int[sequences.length][sequences.length];
        for (int i = 0; i < sequences.length; i++) {
            for (int j = i; j < sequences.length; j++) {
                result[i][j] = twoMerInnerProduct(twoMerSpectra(sequences[i]), twoMerSpectra(sequences[j]));
                result[j][i] = result[i][j];
            }
        }
        return result;
    }

    private static int factorial(int n) {
        int ret = 1;
        int i = 1;
        while (i <= n) {
            ret *= i;
            i++;
        }
        return ret;
    }

    private static int combination(int n, int r) {
        return factorial(n) / (factorial(r) * factorial(n - r));
    }

    // compute the inner product of 3-gapped 2-mer spectra of two sequences
    private static int threeGappedTwoMerInnerProduct(String sequence1, String sequence2) {
        int similarity = 0;
        System.out.println("Initialize cumulative sum to " + similarity);
        for (int i = 0; i < sequence1.length() - 4; i++) {
            for (int j = 0; j < sequence2.length() - 4; j++) {
                int mismatches = 0;
                System.out.printf("Comparing %s (O1[%d-%d]) and %s (O2[%d-%d]):%n", sequence1.substring(i, i + 5), i + 1, i + 5, sequence2.substring(j, j + 5), j + 1, j + 5);
                for (int k = 0; k < 5; k++) {
                    if (sequence1.charAt(i + k) != sequence2.charAt(j + k)) {
                        mismatches++;
                    }
                }
                System.out.printf("There are %d mismatches%n", mismatches);
                if (mismatches <= 3) {
                    int contribution = combination(5 - mismatches, 2);
                    similarity += contribution;
                    System.out.print("Contribution: " + contribution);
                } else {
                    System.out.print("Contribution: 0");
                }
                System.out.println(", Current sum: " + similarity);
            }
        }
        return similarity;
    }

    public static void main(String[] args) {
        String[] sequences = new String[]{
                "CTTGCTACAT",
                "CCAACAGCAG",
                "GATGCGCCAC",
                "CTTACCGCGT",
                "GATACGTCAT",
                "CGCACATCAC",
                "ATTCCGGCAG",
                "GTTGCGCCAT",
        };

        System.out.println("Consensus sequence:");
        System.out.println(consensusSequence(sequences));

        System.out.println("Degenerate sequence:");
        System.out.println(degenerateSequence(sequences));

        System.out.println("Position weight matrix:");
        int[][] positionWeightMatrix = positionWeightMatrix(sequences);
        printPositionWeightMatrix(positionWeightMatrix);
        for (String sequence : sequences) {
            System.out.println("2-mer spectrum of " + sequence + ":");
            HashMap<String, Integer> spectrum = twoMerSpectra(sequence);
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    String key = "" + toChar(i) + toChar(j);
                    if (spectrum.containsKey(key)) {
                        System.out.print(key + ":" + spectrum.get(key) + " ");
                    } else {
                        System.out.print(key + ":0 ");
                    }
                }
            }
            System.out.println();
        }

        System.out.println("Similarity matrix:");
        int[][] similarityMatrix = similarityMatrix(sequences);
        System.out.print("   ");
        for (int i = 0; i < sequences.length; i++) {
            System.out.print(" O" + (i + 1));
        }
        System.out.println();
        for (int i = 0; i < similarityMatrix.length; i++) {
            int[] row = similarityMatrix[i];
            System.out.print("O" + (i + 1) + " ");
            for (int num : row) {
                System.out.printf("%3d", num);
            }
            System.out.println();
        }

        System.out.println("3-gapped 2-mer inner product:");
        System.out.println("Final similarity: " + threeGappedTwoMerInnerProduct(sequences[0], sequences[1]));

        System.out.println("Similarity 1: " + threeGappedTwoMerInnerProduct("CGCGGGCC", "GCCGGCGC"));
        System.out.println("Similarity 2: " + threeGappedTwoMerInnerProduct("CGATCGCG", "ATATCGCT"));
    }
}
