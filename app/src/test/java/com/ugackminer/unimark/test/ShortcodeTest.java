package com.ugackminer.unimark.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.ugackminer.unimark.unicode.ShortcodeConverter;

public class ShortcodeTest {
    
    ShortcodeConverter converter;

    public ShortcodeTest() {
        this.converter = new ShortcodeConverter();
    }

    @Test void shortcodeConversionTest() {
        assertEquals("😄", converter.convertShortcode("smile"));
        assertEquals("😭", converter.convertShortcode("sob"));
        assertEquals("🏳️‍🌈", converter.convertShortcode("rainbow_flag"));
        assertEquals("⚧", converter.convertShortcode("transgender_symbol"));
    }


}
