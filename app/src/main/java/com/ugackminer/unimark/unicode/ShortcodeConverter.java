package com.ugackminer.unimark.unicode;

import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import java.io.InputStream;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import okio.Okio;

public class ShortcodeConverter {
    static final String SHORTCODE_FILE = "/shortcodes.json";
    
    Map<String, String> shortcodeMap; 

    public ShortcodeConverter() {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Map<String, String>> adapter = moshi.adapter(Types.newParameterizedType(Map.class, String.class, String.class));

        InputStream inputStream = ShortcodeConverter.class.getResourceAsStream(SHORTCODE_FILE);
        JsonReader reader = JsonReader.of(Okio.buffer(Okio.source(inputStream)));

        try {
            this.shortcodeMap = adapter.fromJson(reader);
            reader.close();
        } catch (IOException err) {
            this.shortcodeMap = new HashMap<String, String>();
            System.err.println(err);
        }
    }

    /**
     * Converts a shortcode into it's corresponding Unicode character
     * @param shortcode The shortcode to be converted
     * @return A String containing the Unicode character or null if the shortcode is not found
     */
    public String convertShortcode(String shortcode) { 
        if (!shortcodeMap.containsKey(shortcode.toLowerCase())) return String.format(":%s:", shortcode);
        String[] unicodeCodeStrings = shortcodeMap.get(shortcode.toLowerCase()).split("-");
        StringBuilder output = new StringBuilder();
        
        for (String unicodeCodeString : unicodeCodeStrings) {
            int unicodeCode = Integer.parseInt(unicodeCodeString, 16);
            output.append(Character.toChars(unicodeCode));
        }
        return output.toString();
    }
}