package com.ugackminer.unimark;

public class UnicodeConverter {
    
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

        // Remove the asterisks at the start and end of the segment
        builder.delete(0, 2).delete(builder.length()-2, builder.length());

        return builder.toString();
    }

    public static void main(String[] args) {        
        System.out.println(UnicodeConverter.convertToBold("This text is now bold."));
    }


}