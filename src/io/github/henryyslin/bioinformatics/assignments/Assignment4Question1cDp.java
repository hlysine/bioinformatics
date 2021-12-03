package io.github.henryyslin.bioinformatics.assignments;

import java.text.DecimalFormat;

public class Assignment4Question1cDp {

    static final double ALPHA = 0.02;

    private static String format(double d) {
        if (d < 0.01 && d != 0) {
            return new DecimalFormat("0.#########E0").format(d);
        } else {
            return new DecimalFormat("0.#########").format(d);
        }
    }

    public static void main(String[] args) {

        double[][] matrix = new double[11][2];
        matrix[0][0] = 1;
        matrix[0][1] = 0;

        System.out.println("t,X->X,X->Y");
        System.out.println("0," + format(matrix[0][0]) + "," + format(matrix[0][1]));

        for (int i = 1; i < 11; i++) {
            matrix[i][0] = matrix[i - 1][0] * (1 - 3 * ALPHA) + (1 - matrix[i - 1][0]) * ALPHA;
            matrix[i][1] = matrix[i - 1][1] * (1 - 3 * ALPHA) + (1 - matrix[i - 1][1]) * ALPHA;
            System.out.println(i +
                    ",\"" + format(matrix[i - 1][0]) + " * (1 - 3α) + α(1 - " + format(matrix[i - 1][0]) + ")\n=" + format(matrix[i][0]) + "\"" +
                    ",\"" + format(matrix[i - 1][1]) + " * (1 - 3α) + α(1 - " + format(matrix[i - 1][1]) + ")\n=" + format(matrix[i][1]) + "\"");
        }
    }
}
