package io.github.henryyslin.bioinformatics.lib;

import java.security.InvalidParameterException;
import java.util.Arrays;

public class SequenceUtils {
    /**
     * Check whether a char is a valid RNA sequence char.
     *
     * @param c The char to be checked.
     * @return Whether the char is a valid RNA sequence char.
     */
    public static boolean isRnaChar(char c) {
        return c == 'A' || c == 'C' || c == 'G' || c == 'U';
    }

    /**
     * Check whether a char is a valid DNA sequence char.
     *
     * @param c The char to be checked.
     * @return Whether the char is a valid DNA sequence char.
     */
    public static boolean isDnaChar(char c) {
        return c == 'A' || c == 'C' || c == 'G' || c == 'T';
    }

    /**
     * Check whether a char is a valid amino acid char.
     *
     * @param c The char to be checked.
     * @return Whether the char is a valid amino acid char.
     */
    public static boolean isAminoAcidChar(char c) {
        if (c < 'A' || c > 'Z') return false;
        if (c == 'B' || c == 'J' || c == 'O' || c == 'U' || c == 'X' || c == 'Z') return false;
        return true;
    }

    /**
     * Get the complementary char for a given DNA char.
     *
     * @param c A DNA char.
     * @return The complementary char.
     * @throws InvalidParameterException The given char is not a valid DNA char.
     */
    public static char getComplementaryChar(char c) throws InvalidParameterException {
        switch (c) {
            case 'A':
                return 'T';
            case 'C':
                return 'G';
            case 'T':
                return 'A';
            case 'G':
                return 'C';
            default:
                throw new InvalidParameterException("The given char (" + c + ") is not a valid DNA char.");
        }
    }

    /**
     * Get the transcribed RNA char for a given DNA char.
     *
     * @param c A DNA char.
     * @return The transcribed RNA char.
     * @throws InvalidParameterException The given char is not a valid DNA char.
     */
    public static char getTranscribedChar(char c) throws InvalidParameterException {
        switch (c) {
            case 'A':
                return 'U';
            case 'C':
                return 'G';
            case 'T':
                return 'A';
            case 'G':
                return 'C';
            default:
                throw new InvalidParameterException("The given char (" + c + ") is not a valid DNA char.");
        }
    }

    /**
     * Get the original DNA char that would be transcribed to a given RNA char.
     *
     * @param c An RNA char.
     * @return The original DNA char.
     * @throws InvalidParameterException The given char is not a valid RNA char.
     */
    public static char getUntranscribedChar(char c) throws InvalidParameterException {
        switch (c) {
            case 'A':
                return 'T';
            case 'C':
                return 'G';
            case 'U':
                return 'A';
            case 'G':
                return 'C';
            default:
                throw new InvalidParameterException("The given char (" + c + ") is not a valid RNA char.");
        }
    }

    public static final String StartCodon = "AUG";
    public static final String StopCodonSymbol = "*";

    public static final Codon[] CodonTable = {
            new Codon("UUU", "F"),
            new Codon("UUC", "F"),
            new Codon("UUA", "L"),
            new Codon("UUG", "L"),

            new Codon("UCU", "S"),
            new Codon("UCC", "S"),
            new Codon("UCA", "S"),
            new Codon("UCG", "S"),

            new Codon("UAU", "Y"),
            new Codon("UAC", "Y"),
            new Codon("UAA", StopCodonSymbol),
            new Codon("UAG", StopCodonSymbol),

            new Codon("UGU", "C"),
            new Codon("UGC", "C"),
            new Codon("UGA", StopCodonSymbol),
            new Codon("UGG", "W"),

            new Codon("CUU", "L"),
            new Codon("CUC", "L"),
            new Codon("CUA", "L"),
            new Codon("CUG", "L"),

            new Codon("CCU", "P"),
            new Codon("CCC", "P"),
            new Codon("CCA", "P"),
            new Codon("CCG", "P"),

            new Codon("CAU", "H"),
            new Codon("CAC", "H"),
            new Codon("CAA", "Q"),
            new Codon("CAG", "Q"),

            new Codon("CGU", "R"),
            new Codon("CGC", "R"),
            new Codon("CGA", "R"),
            new Codon("CGG", "R"),

            new Codon("AUU", "I"),
            new Codon("AUC", "I"),
            new Codon("AUA", "I"),
            new Codon("AUG", "M"),

            new Codon("ACU", "T"),
            new Codon("ACC", "T"),
            new Codon("ACA", "T"),
            new Codon("ACG", "T"),

            new Codon("AAU", "N"),
            new Codon("AAC", "N"),
            new Codon("AAA", "K"),
            new Codon("AAG", "K"),

            new Codon("AGU", "S"),
            new Codon("AGC", "S"),
            new Codon("AGA", "R"),
            new Codon("AGG", "R"),

            new Codon("GUU", "V"),
            new Codon("GUC", "V"),
            new Codon("GUA", "V"),
            new Codon("GUG", "V"),

            new Codon("GCU", "A"),
            new Codon("GCC", "A"),
            new Codon("GCA", "A"),
            new Codon("GCG", "A"),

            new Codon("GAU", "D"),
            new Codon("GAC", "D"),
            new Codon("GAA", "E"),
            new Codon("GAG", "E"),

            new Codon("GGU", "G"),
            new Codon("GGC", "G"),
            new Codon("GGA", "G"),
            new Codon("GGG", "G"),
    };

    /**
     * Get the amino acid represented by a given 3-letter RNA codon.
     *
     * @param rna The 3-letter RNA codon.
     * @return Information of the corresponding codon, including the amino acid represented.
     * @throws InvalidParameterException The given string is not a valid RNA codon.
     */
    public static Codon getCodon(String rna) throws InvalidParameterException {
        return Arrays.stream(CodonTable).filter(c -> c.Rna.equals(rna)).findFirst().orElseThrow(() -> new InvalidParameterException(rna + " is not a valid RNA codon."));
    }
}
