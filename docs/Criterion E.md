# Criterion E

## Evaluation of Criteria
- **Run program on global keybind**  
Using the `jnativehook` program, I was able to listen to global keypresses and set a global keybind. The global keybind was also made configurable.

- **Convert bold text**  
Bold text conversion works correctly in all test cases as well as covering bold+italic text.

- **Convert italic text**  
Italic text conversion works correctly in all test cases as well as covering bold+italic text.

- **Convert underlined text**  
Underlined text conversion works correctly in all test cases.

- **Convert strikethrough text**  
Strikethrough text conversion works correctly in all test cases.

- **Convert emoji shortcodes**  
Emoji shortcode conversion works correctly in all test cases.

- **Text converted should be in the desired program**  
The java `Robot` uses the clipboard to make sure that focus does not shift from the desired program's textbox.

- **Should work on MacOS and in iMessage**  
Based on client feedback, the program runs in MacOS and works very well when used with iMessage.

- **Should have some way to quickly turn off the program**  
The program was added to the system tray (and a GUI was created) that both allow for the program to be enabled and disabled in less than three clicks.

## Effectiveness
After the client had been using the program for an extended period of time, I met up with them again to discuss how well the program was working for them.

The client said that they use the program a lot of the time in iMessage to message their family, and explained how it helps them communicate in the same style as their discord messages.

One feature that the client pointed out in particular that they commonly use is the shortcode to emoji feature, which gives them a way to type "so many \[emoji\]" in iMessage, something that was previously fairly difficult when trying to find and type the right emoji. 

## Recommendations for Future Development
Some recommendations that I would have for future development of the program include an automatic updater, which is a feature that I was unable to add in time. I believe that this would help maintainers fix bugs in the program without requiring the client to redownload the program manually with every change.

One of the things that the client would've liked to see added when the GUI was created was a way to convert text without using the keyboard shortcut, instead putting text into some form of textbox in the GUI. This would allow for people who don't want to use the keybind to instead use a simple and understandable textbox, while leaving the keybind for more advanced users.

I also wanted for there to be some form of input where users could create their own custom markdown codes, something that started to spiral out of scope for the project. When consulting with the client, they seemed to believe it to be very unimportant, which is why it was scrapped. However, it would be a good feature for power users, and would help make maintaining the program easier as markdown and unicode change over time.

One final improvement is fixing the problem where the program can glitch out sometimes and replace all of the text in the other application with a single letter (usually A, X, or V). This is likely because of the workaround used where the program delays the keybinds in order to give the other application time to process the keybind. However, if the other application lags or processes the keybinding slower than 100ms, the program will start converting text already on the clipboard instead of the desired text.  
This could be fixed by somehow communicating with the other program to see if the (select/cut/paste) action has finished before trying to read the clipboard.