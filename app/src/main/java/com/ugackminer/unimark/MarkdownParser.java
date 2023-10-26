package com.ugackminer.unimark;
// import java.util.regex.;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkdownParser {

    static Pattern boldPattern = Pattern.compile("(?!\\\\)\\*\\*.*?[^\\\\]\\*\\*");
    static Pattern italicPattern = Pattern.compile("(?!\\\\)\\*.*?[^\\\\]\\*");

    public static String parseMarkdown(String input) {
        input = parsePattern(boldPattern, input, 2, UnicodeConverter::convertToBold);
        input = parsePattern(italicPattern, input, 1, UnicodeConverter::convertToItalic);
        return input;
    }

    /**
     * A function that takes in a String and a Regex and outputs the function applied to all matches.
     * Meant to be used to run a Unicode convertor on each Markdown match.
     * 
     * @param pattern The Regex pattern that the function tries to parse
     * @param input The input string that the function parses
     * @param outliners The number of characters to trim from the beginning and end of the string
     * @param conversionFunction The function that takes in matched patterns and converts them to special Unicode characters 
     * @return A new String with the outputs of the conversionFunction applied to each 
     */
    private static String parsePattern(Pattern pattern, String input, int outliners, UnicodeMethod conversionFunction) {
        Matcher matcher = pattern.matcher(input);
        StringBuffer result = new StringBuffer(input.length());

        while (matcher.find()) {
            matcher.appendReplacement(result, "");
            result.append(conversionFunction.convert(matcher.group(0).substring(outliners, matcher.group(0).length() - outliners)));
        }
        matcher.appendTail(result);
        return result.toString();
    }

    /**
     * {@code outliners} defaults to {@number 0}.
     *
     * @see MarkdownParser#parsePattern(Pattern, String, int, UnicodeMethod)
     */
    private static String parsePattern(Pattern pattern, String input, UnicodeMethod conversionFunction) {
        return parsePattern(pattern, input, 0, conversionFunction);
    }
}

interface UnicodeMethod {
    public String convert(String input);
}