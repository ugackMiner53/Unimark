*Breaking [Unicode's best practices](https://www.unicode.org/versions/Unicode15.1.0/ch22.pdf#G15993) since 2024*

# ![Unimark Logo](./docs/resources/Logo.png) Unimark
Convert extended Markdown syntax into special Unicode characters that work anywhere that text does.  
`**text** -> 𝘁𝗲𝘅𝘁`

## Features

| Feature | Markdown | Converted |
|---------|----------|-----------|
| Bold | \*\*text\*\* | 𝘁𝗲𝘅𝘁 |
| Italics | \*text\* | 𝘵𝘦𝘹𝘵 |
| Bold + Italics | \*\*\*text\*\*\* | 𝙩𝙚𝙭𝙩 |
| Monospace | \`text\` | 𝚝𝚎𝚡𝚝 |
| Strikethrough | \~\~text\~\~ | t̶e̶x̶t̶ |
| Underline | \_\_text\_\_ | t͟e͟x͟t͟ | 
| Cursive | \~text\~ | 𝓉ℯ𝓍𝓉 |
| Emoji | :tada: | 🎉 |

Press the global keybind (default <kbd>Ctrl</kbd>+<kbd>M</kbd>) in any application in order to have all text in the current input area converted!

Escaping characters also works! Put backslashes in front of text in order to stop them from being converted!  
`\*text\* -> *text*`

## Building
To build the project for yourself, first, download the project.
```sh
git clone https://github.com/ugackMiner53/Unimark.git
```
Once you have the project, you can run it by calling
```sh
./gradlew run
```
or build it with
```sh
./gradlew shadowJar
```

## Contributing
Contributions are welcome! Submit a pull request [here](https://github.com/ugackMiner53/Unimark/pulls) and it will be reviewed shortly!
