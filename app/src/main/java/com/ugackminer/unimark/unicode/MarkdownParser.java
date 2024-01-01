package com.ugackminer.unimark.unicode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkdownParser {

    static final Pattern shortcodePattern = Pattern.compile("(?<!\\\\):.*?[^\\\\]:");
    static final Pattern boldPattern = Pattern.compile("(?<!\\\\)\\*\\*.*?[^\\\\]\\*\\*");
    static final Pattern italicPattern = Pattern.compile("(?<!\\\\)\\*.*?[^\\\\]\\*");
    static final Pattern underlinePattern = Pattern.compile("(?<!\\\\)__.*?[^\\\\]__");
    static final Pattern strikethroughPattern = Pattern.compile("(?<!\\\\)~~.*?[^\\\\]~~");
    static final Pattern monospacePattern = Pattern.compile("(?<!\\\\)`.*?[^\\\\]`");
    static final Pattern cursivePattern = Pattern.compile("(?<!\\\\)~.*?[^\\\\]~");
    static final Pattern escapePattern = Pattern.compile("\\\\\\S");

    static ShortcodeConverter shortcodeConverter = new ShortcodeConverter();

    public static String parseMarkdown(String input) {
        input = parsePattern(shortcodePattern, input, shortcodeConverter::convertShortcode, 1);
        input = parsePattern(boldPattern, input, UnicodeConverter::convertToBold, 2);
        input = parsePattern(italicPattern, input, UnicodeConverter::convertToItalic, 1);
        input = parsePattern(underlinePattern, input, UnicodeConverter::convertToUnderline, 2);
        input = parsePattern(strikethroughPattern, input, UnicodeConverter::convertToStrikethrough, 2);
        input = parsePattern(monospacePattern, input, UnicodeConverter::convertToMonospace, 1);
        input = parsePattern(cursivePattern, input, UnicodeConverter::convertToCursive, 1);

        // Remove backslashes here
        input = parsePattern(escapePattern, input, MarkdownParser::escapeCharacters);
        return input;
    }

    /**
     * A function that takes in a String and a Regex and outputs the function applied to all matches.
     * Meant to be used to run a Unicode convertor on each Markdown match.
     * 
     * @param pattern The Regex pattern that the function tries to parse
     * @param input The input string that the function parses
     * @param conversionFunction The function that takes in matched patterns and converts them to special Unicode characters 
     * @param trim The number of characters to trim from the beginning and end of the string
     * @return A new String with the outputs of the conversionFunction applied to each 
     */
    private static String parsePattern(Pattern pattern, String input, UnicodeMethod conversionFunction, int trim) {
        Matcher matcher = pattern.matcher(input);
        StringBuffer result = new StringBuffer(input.length());

        while (matcher.find()) {
            matcher.appendReplacement(result, "");
            result.append(conversionFunction.convert(matcher.group(0).substring(trim, matcher.group(0).length() - trim)));
        }
        matcher.appendTail(result);
        return result.toString();
    }

    /**
     * {@code trim} defaults to {@number 0}.
     *
     * @see MarkdownParser#parsePattern(Pattern, String, int, UnicodeMethod)
     */
    private static String parsePattern(Pattern pattern, String input, UnicodeMethod conversionFunction) {
        return parsePattern(pattern, input, conversionFunction, 0);
    }


    /**
     * Internal method to remove the backslashes from escaped characters.
     * @param text The text with a backslash to be removed "\?"
     * @return The single character "?"
     */
    private static String escapeCharacters(String text) {
        return text.substring(1);
    }
}

interface UnicodeMethod {
    public String convert(String input);
}