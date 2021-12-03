package io.github.henryyslin.bioinformatics.assignments;

public class Assignment4Question1e {

    static final int[][] MATRIX = {
            {0, 1, 6, 5, 7},
            {1, 0, 5, 4, 6},
            {6, 5, 0, 2, 3},
            {5, 4, 2, 0, 4},
            {7, 6, 3, 4, 0}
    };

    public static void main(String[] args) throws Exception {
        System.out.println();
        System.out.println("Check property 1");
        System.out.println();

        for (int i = 0; i < MATRIX.length; i++) {
            for (int j = 0; j < MATRIX[i].length; j++) {
                System.out.println("d(s" + (i + 1) + ",s" + (j + 1) + ") >= 0");
            }
        }

        System.out.println();
        System.out.println("Check property 2");
        System.out.println();

        for (int i = 0; i < MATRIX.length; i++) {
            System.out.println("d(s" + (i + 1) + ",s" + (i + 1) + ") = 0");
        }

        System.out.println();
        System.out.println("Check property 3");
        System.out.println();

        for (int i = 0; i < MATRIX.length; i++) {
            for (int j = i + 1; j < MATRIX[i].length; j++) {
                if (MATRIX[i][j] == MATRIX[j][i])
                    System.out.println("d(s" + (i + 1) + ",s" + (j + 1) + ") = d(s" + (j + 1) + ",s" + (i + 1) + ")");
                else
                    throw new Exception("Property 3 fail");
            }
        }

        System.out.println();
        System.out.println("Check property 4");
        System.out.println();

        for (int i = 0; i < MATRIX.length; i++) {
            for (int j = 0; j < MATRIX.length; j++) {
                for (int k = 0; k < MATRIX.length; k++) {
                    if (MATRIX[i][j] + MATRIX[j][k] >= MATRIX[i][k])
                        System.out.println("d(s" + (i + 1) + ",s" + (j + 1) + ") + d(s" + (j + 1) + ",s" + (k + 1) + ") >= d(s" + (i + 1) + ",s" + (k + 1) + ")");
                    else
                        throw new Exception("Property 4 fail");
                }
            }
        }

        System.out.println();
        System.out.println("Check property 5");
        System.out.println();

        for (int i = 0; i < MATRIX.length; i++) {
            for (int j = 0; j < MATRIX.length; j++) {
                for (int k = 0; k < MATRIX.length; k++) {
                    if (MATRIX[i][j] <= Math.max(MATRIX[i][k], MATRIX[j][k]))
                        System.out.println("d(s" + (i + 1) + ",s" + (j + 1) + ") <= max(d(s" + (i + 1) + ",s" + (k + 1) + "), d(s" + (j + 1) + ",s" + (k + 1) + "))");
                    else
                        throw new Exception("Property 5 fail (s" + (i + 1) + ",s" + (j + 1) + ",s" + (k + 1) + ")");
                }
            }
        }
    }
}
