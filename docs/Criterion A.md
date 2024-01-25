# Criterion A

## Description of Scenario (125/175)
The client for my program often uses the iMessage messaging program on their Apple Macbook in order to send messages to friends and family, but is familiar with the way that text can be formatted in another messaging program, Discord. 

Discord implements a version of Markdown syntax, allowing people to type bold, italics, underlines, strikethrough, headers, and links by typing characters like asterisks before and after the desired text section.

iMessage does not currently support formatted text, instead allowing only Unicode characters (such as text and emoji) to be sent to other people. 

The client would like to have formatted text in iMessage, and be able to use the same syntax as Discord in order to type and send this formatted text to other people.

## Proposed Solution  (119/175)
Because iMessage allows for Unicode characters to be sent, we can send special Unicode characters from the Mathematical Alphanumeric Symbols block, which look similar to normal text, but are styled in different fonts and weights, making text look formatted.
This solves the issue of iMessage not supporting formatted text

There are also some special Unicode characters that can "merge" with the character before them in order to create diacritics and other effects. This can be used to create similar effects to strikethrough and underline formatting. 

Using a Markdown parser, the program can use Discord's Markdown syntax in order to find the sections that need to be converted and change them into the needed text before placing it into iMessage.

## Success Criteria
- Run program on global keybind
- Convert bold text
- Convert italic text
- Convert underlined text
- Convert strikethrough text
- Convert emoji shortcodes
- Text converted should be in the desired program
- Should work on MacOS and in iMessage
- Should have some way to quickly turn off the program

## Appendix Items:
[Initial Interview](./appendix/Interview.md)