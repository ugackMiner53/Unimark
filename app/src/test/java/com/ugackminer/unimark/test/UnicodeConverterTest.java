package com.ugackminer.unimark.test;

import com.ugackminer.unimark.UnicodeConverter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class UnicodeConverterTest {
    
    static final String UPPERCASE_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static final String LOWERCASE_STRING = "abcdefghijklmnopqrstuvwxyz";
    static final String NUMERICAL_STRING = "0123456789";
    static final String SYMBOL_STRING = ".?!@#$%^&*()/❤😊";

    @Test void boldConversionTest() {
        assertEquals("𝗔𝗕𝗖𝗗𝗘𝗙𝗚𝗛𝗜𝗝𝗞𝗟𝗠𝗡𝗢𝗣𝗤𝗥𝗦𝗧𝗨𝗩𝗪𝗫𝗬𝗭", UnicodeConverter.convertToBold(UPPERCASE_STRING));
        assertEquals("𝗮𝗯𝗰𝗱𝗲𝗳𝗴𝗵𝗶𝗷𝗸𝗹𝗺𝗻𝗼𝗽𝗾𝗿𝘀𝘁𝘂𝘃𝘄𝘅𝘆𝘇", UnicodeConverter.convertToBold(LOWERCASE_STRING));
        assertEquals("𝟬𝟭𝟮𝟯𝟰𝟱𝟲𝟳𝟴𝟵", UnicodeConverter.convertToBold(NUMERICAL_STRING));
        assertEquals(SYMBOL_STRING, SYMBOL_STRING);
    }

    @Test void italicConversionTest() {
        assertEquals("𝘈𝘉𝘊𝘋𝘌𝘍𝘎𝘏𝘐𝘑𝘒𝘓𝘔𝘕𝘖𝘗𝘘𝘙𝘚𝘛𝘜𝘝𝘞𝘟𝘠𝘡", UnicodeConverter.convertToItalic(UPPERCASE_STRING));
        assertEquals("𝘢𝘣𝘤𝘥𝘦𝘧𝘨𝘩𝘪𝘫𝘬𝘭𝘮𝘯𝘰𝘱𝘲𝘳𝘴𝘵𝘶𝘷𝘸𝘹𝘺𝘻", UnicodeConverter.convertToItalic(LOWERCASE_STRING));
        assertEquals(NUMERICAL_STRING, NUMERICAL_STRING);
        assertEquals(SYMBOL_STRING, SYMBOL_STRING);
    }

    @Test void monospaceConversionTest() {
        assertEquals("𝙰𝙱𝙲𝙳𝙴𝙵𝙶𝙷𝙸𝙹𝙺𝙻𝙼𝙽𝙾𝙿𝚀𝚁𝚂𝚃𝚄𝚅𝚆𝚇𝚈𝚉", UnicodeConverter.convertToMonospace(UPPERCASE_STRING));
        assertEquals("𝚊𝚋𝚌𝚍𝚎𝚏𝚐𝚑𝚒𝚓𝚔𝚕𝚖𝚗𝚘𝚙𝚚𝚛𝚜𝚝𝚞𝚟𝚠𝚡𝚢𝚣", UnicodeConverter.convertToMonospace(LOWERCASE_STRING));
        assertEquals("𝟶𝟷𝟸𝟹𝟺𝟻𝟼𝟽𝟾𝟿", UnicodeConverter.convertToMonospace(NUMERICAL_STRING));
        assertEquals(SYMBOL_STRING, SYMBOL_STRING);
    }

    @Test void cursiveConversionTest() {
        assertEquals(UPPERCASE_STRING, UnicodeConverter.convertToCursive(UPPERCASE_STRING));
    }

    @Test void underlineConversionTest() {
        assertEquals("A͟B͟C͟D͟E͟F͟G͟H͟I͟J͟K͟L͟M͟N͟O͟P͟Q͟R͟S͟T͟U͟V͟W͟X͟Y͟Z͟", UnicodeConverter.convertToUnderline(UPPERCASE_STRING));
        assertEquals("a͟b͟c͟d͟e͟f͟g͟h͟i͟j͟k͟l͟m͟n͟o͟p͟q͟r͟s͟t͟u͟v͟w͟x͟y͟z͟", UnicodeConverter.convertToUnderline(LOWERCASE_STRING));
        assertEquals("0͟1͟2͟3͟4͟5͟6͟7͟8͟9͟", UnicodeConverter.convertToUnderline(NUMERICAL_STRING));
        assertEquals(".͟?͟!͟@͟#͟$͟%͟^͟&͟*͟(͟)͟/͟❤😊", UnicodeConverter.convertToUnderline(SYMBOL_STRING));
    }


}