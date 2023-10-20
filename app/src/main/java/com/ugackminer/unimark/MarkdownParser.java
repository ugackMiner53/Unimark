package com.ugackminer.unimark;
// import java.util.regex.;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkdownParser {

    static Pattern boldPattern = Pattern.compile("(?!\\\\)\\*\\*.*?[^\\\\]\\*\\*");

    public static String parseMarkdown(String input) {
        input = parsePattern(boldPattern, input, UnicodeConverter::convertToBold);
        return input;
    }

    private static String parsePattern(Pattern pattern, String input, UnicodeMethod conversionFunction) {
        Matcher matcher = pattern.matcher(input);
        StringBuffer result = new StringBuffer(input.length());

        while (matcher.find()) {
            matcher.appendReplacement(result, "");
            result.append(conversionFunction.convert(matcher.group(0)));
        }
        matcher.appendTail(result);
        return result.toString();
    }
}

interface UnicodeMethod {
    public String convert(String input);
}