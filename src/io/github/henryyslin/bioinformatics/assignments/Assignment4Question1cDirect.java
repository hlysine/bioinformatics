package io.github.henryyslin.bioinformatics.assignments;

import java.text.DecimalFormat;

public class Assignment4Question1cDirect {

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

        System.out.println("t,X->X,X->Y");

        for (int i = 0; i < 11; i++) {
            matrix[i][0] = 3 / 4.0 * Math.pow(1 - 4 * ALPHA, i) + 1 / 4.0;
            matrix[i][1] = (1 - matrix[i][0]) / 3;
            System.out.println(i +
                    ",\"3 / 4 * (1 - 4α) ^ " + i + " + 1 / 4\n=" + format(matrix[i][0]) + "\"" +
                    ",\"(1 - " + matrix[i][0] + ") / 3\n=" + format(matrix[i][1]) + "\"");
        }
    }
}
