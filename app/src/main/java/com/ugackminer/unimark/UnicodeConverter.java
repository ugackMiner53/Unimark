package com.ugackminer.unimark;

public class UnicodeConverter {
    
    /**
     * Takes in a String and converts all alphanumeric characters 
     * to be in the {@code MATHEMATICAL BOLD SANS-SERIF} Unicode range ({@code U+1D622}/{@code U+1D6E2}).
     * @param text The input string
     * @return The bolded version of the string
     */
    public static String convertToBold(String text) {
        StringBuilder builder = new StringBuilder(text.length());

        for (char character : text.toCharArray()) {
            if (Character.isAlphabetic(character)) {
                builder.append(Character.toChars(character + (120205 + (Character.isUpperCase(character) ? 6 : 0))));
            } else if (Character.isDigit(character)) {
                builder.append(Character.toChars(character + 120764));
            } else {
                builder.append(character);
            }
        }

        return builder.toString();
    }

    /**
     * Takes in a String and converts all letters 
     * to be in the {@code MATHEMATICAL ITALIC SANS-SERIF} Unicode range ({@code U+1D608}/{@code U+1D622}).
     * @param text The input string
     * @return The italicized version of the string
     */
    public static String convertToItalic(String text) {
        StringBuilder builder = new StringBuilder(text.length());

        for (char character : text.toCharArray()) {
            if (Character.isAlphabetic(character)) {
                builder.append(Character.toChars(character + (0x1D5C1 + (Character.isUpperCase(character) ? 6 : 0)))); // 0x1D5C1 lowercase vs 0x1D5C7
            } else {
                builder.append(character);
            }
        }

        return builder.toString();
    }

    /**
     * Takes in a String and converts all letters
     * to be in the {@code MATHEMATICAL MONOSPACE} Unicode range ({@code U+1D670}/{@code U+1D7F6})
     * @param text The input string
     * @return The monospaced version of the string
     */    
    public static String convertToMonospace(String text) {
        StringBuilder builder = new StringBuilder(text.length());

        for (char character : text.toCharArray()) {
            if (Character.isAlphabetic(character)) {
                builder.append(Character.toChars(character + (0x1D629 + (Character.isUpperCase(character) ? 6 : 0)))); // 1D62F uppercase vs 1D629 lowercase
            } else if (Character.isDigit(character)) {
                builder.append(Character.toChars(character + 0x1D7C6));
            } else {
                builder.append(character);
            }
        }

        return builder.toString();
    }

    
    public static String convertToCursive(String text) {
        return text;
    }


    /**
     * Takes in a string and puts {@code U+035F} after every alphanumeric character
     * in order to make an underlined string.
     * @param text The input string
     * @return The underlined version of the string
     */
    public static String convertToUnderline(String text) {
        StringBuilder builder = new StringBuilder(text.length()*2);

        for (char character : text.toCharArray()) {
            builder.append(character);
            if (Character.UnicodeBlock.of(character) == Character.UnicodeBlock.BASIC_LATIN) {
                builder.append("\u035F");
            }
        }
        return builder.toString();
    }


    public static String convertToStrikethrough(String text) {
        return text;
    }
}