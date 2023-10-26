package com.ugackminer.unimark;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class UnicodeConverterTest {
    
    static final String UPPERCASE_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static final String LOWERCASE_STRING = "abcdefghijklmnopqrstuvwxyz";
    static final String NUMERICAL_STRING = "0123456789";

    @Test void boldConversionTest() {
        assertEquals("𝗔𝗕𝗖𝗗𝗘𝗙𝗚𝗛𝗜𝗝𝗞𝗟𝗠𝗡𝗢𝗣𝗤𝗥𝗦𝗧𝗨𝗩𝗪𝗫𝗬𝗭", UnicodeConverter.convertToBold(UPPERCASE_STRING));
        assertEquals("𝗮𝗯𝗰𝗱𝗲𝗳𝗴𝗵𝗶𝗷𝗸𝗹𝗺𝗻𝗼𝗽𝗾𝗿𝘀𝘁𝘂𝘃𝘄𝘅𝘆𝘇", UnicodeConverter.convertToBold(LOWERCASE_STRING));
        assertEquals("𝟬𝟭𝟮𝟯𝟰𝟱𝟲𝟳𝟴𝟵", UnicodeConverter.convertToBold(NUMERICAL_STRING));
    }

    @Test void italicConversionTest() {
        assertEquals("𝘈𝘉𝘊𝘋𝘌𝘍𝘎𝘏𝘐𝘑𝘒𝘓𝘔𝘕𝘖𝘗𝘘𝘙𝘚𝘛𝘜𝘝𝘞𝘟𝘠𝘡", UnicodeConverter.convertToItalic(UPPERCASE_STRING));
        assertEquals("𝘢𝘣𝘤𝘥𝘦𝘧𝘨𝘩𝘪𝘫𝘬𝘭𝘮𝘯𝘰𝘱𝘲𝘳𝘴𝘵𝘶𝘷𝘸𝘹𝘺𝘻", UnicodeConverter.convertToItalic(LOWERCASE_STRING));
    }

}
