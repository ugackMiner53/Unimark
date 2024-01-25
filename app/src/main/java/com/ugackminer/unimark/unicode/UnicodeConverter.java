package com.ugackminer.unimark.unicode;

public class UnicodeConverter {
    /**
     * Takes in a String and converts all alphanumeric characters 
     * to be in the {@code MATHEMATICAL BOLD SANS-SERIF} Unicode range ({@code U+1D622}/{@code U+1D6E2}).
     * @param text The input String
     * @return The bolded version of the String
     */
    public static String convertToBold(String text) {
        StringBuilder builder = new StringBuilder(text.length());
        char[] characters = text.toCharArray();
        int characterCode;        

        for (int i=0; i<characters.length; i++) {
            if (Character.isAlphabetic(characters[i])) {
                builder.append(Character.toChars(characters[i] + (0x1D58D + (Character.isUpperCase(characters[i]) ? 6 : 0))));
            } else if (Character.isDigit(characters[i])) {
                builder.append(Character.toChars(characters[i] + 0x1D7BC));
            } else if (((characterCode = text.codePointAt(i)) != 0) && (characterCode >= 0x1D608 && characterCode <= 0x1D63B)) { 
                // If the integer chars at i in the string are between italic A and italic z then make it bold+italics
                builder.append(Character.toChars(characterCode + 0x34));
                i += Character.charCount(characterCode)-1;
            } else {
                builder.append(characters[i]);
            }
        }

        return builder.toString();
    }

    /**
     * Takes in a String and converts all letters 
     * to be in the {@code MATHEMATICAL ITALIC SANS-SERIF} Unicode range ({@code U+1D608}/{@code U+1D622}).
     * @param text The input String
     * @return The italicized version of the String
     */
    public static String convertToItalic(String text) {
        StringBuilder builder = new StringBuilder(text.length());
        char[] characters = text.toCharArray();
        int characterCode;

        for (int i=0; i<characters.length; i++) {
            if (Character.isAlphabetic(characters[i])) {
                builder.append(Character.toChars(characters[i] + (0x1D5C1 + (Character.isUpperCase(characters[i]) ? 6 : 0))));
            } else if (((characterCode = text.codePointAt(i)) != 0) && (characterCode >= 0x1D5D4 && characterCode <= 0x1D607)) { 
                // If the integer chars at i in the string are between bold A and bold z then make it bold+italics
                builder.append(Character.toChars(characterCode + 0x68));
                i += Character.charCount(characterCode)-1;
            } else {
                builder.append(characters[i]);
            }
        }

        return builder.toString();
    }

    /**
     * Takes in a String and converts all letters
     * to be in the {@code MATHEMATICAL MONOSPACE} Unicode range ({@code U+1D670}/{@code U+1D7F6})
     * @param text The input String
     * @return The monospaced version of the String
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

    /**
     * Takes in a String and converts all letters
     * to be in the {@code MATHEMATICAL CURSIVE} Unicode range ({@code U+1D49C}/{@code U+1D4CF})
     * @param text The input String
     * @return The cursive version of the String
     */
    public static String convertToCursive(String text) {
        StringBuilder builder = new StringBuilder(text.length());

        for (char character : text.toCharArray()) {
            if (Character.isAlphabetic(character)) {
                if ("BEFHILMRego".indexOf(character) != -1) {
                    switch (character) {
                        case 'B':
                            builder.append((char)0x212C);
                            break;
                        case 'E':
                            builder.append((char)0x2130);
                            break;
                        case 'F':
                            builder.append((char)0x2131);
                            break;
                        case 'H':
                            builder.append((char)0x210B);
                            break;
                        case 'I':
                            builder.append((char)0x2110);
                            break;
                        case 'L':
                            builder.append((char)0x2112);
                            break;
                        case 'M':
                            builder.append((char)0x2133);
                            break;
                        case 'R':
                            builder.append((char)0x211B);
                            break;
                        case 'e':
                            builder.append((char)0x212F);
                            break;
                        case 'g':
                            builder.append((char)0x210a);
                            break;
                        case 'o':
                            builder.append((char)0x2134);
                            break;
                        default:
                            builder.append(character);
                    }
                } else {
                    builder.append(Character.toChars(character + (0x1D455 + (Character.isUpperCase(character) ? 6 : 0))));
                }
            } else {
                builder.append(character);
            }
        }

        return builder.toString();
    }


    /**
     * Takes in a string and puts {@code U+035F} after every character 
     * in order to make an underlined String.
     * @param text The input String
     * @return The underlined version of the String
     */
    public static String convertToUnderline(String text) {
        StringBuilder builder = new StringBuilder(text.length()*3);

        for (int i = 0; i < text.length();) {
            int character = text.codePointAt(i);
            builder.append(Character.toChars(character));
            builder.append("\u035F");
            i += Character.charCount(character);
        }
        return builder.toString();
    }

    /**
     * Takes in a string and puts {@code U+0336} after every character
     * in order to make a strikethrough String
     * @param text The input String
     * @return The underlined version of the String
     */
    public static String convertToStrikethrough(String text) {
        StringBuilder builder = new StringBuilder(text.length()*2);

        for (int i=0; i<text.length();) {
            int character = text.codePointAt(i);
            builder.append(Character.toChars(character));
            builder.append("\u0336");
            i += Character.charCount(character);
        }
        return builder.toString();
    }
}