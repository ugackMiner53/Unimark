## Interview with Client
`Me:` Alright, so we've already spoken a bit about this project, but just for the documentation, what exactly is your problem again?

`Client:` I find it frustrating that iMessage doesn't support things like bold text that we have on Discord.

`Me:` Right. I think I have a method that we could use in order to fix that. I've already done two projects that are somewhat similar to this already.

`Me:` Essentially, there are some special types of text that look bold, but are actually treated as just normal text by programs. I think that we could use those to trick iMessage into sending bold characters.

`Client:` That sounds interesting! Is there more than just bold text?

`Me:` Yeah, there's also some italics, monospace, cursive, stuff like that.

`Me:` So here's some things that I'm thinking the program could do. I know that you want the normal Discord formatting with bold text, underlined text, etc, but are you thinking that you'd like some of the other things from Discord like changing unicode slugs to characters? *(:sob: -> 😭)*

`Me:` There are also some other possibilities like shortcuts for arrows or exponents.

`Client:` Sounds very cool! Unicode slugs to characters would be a big one. That frustrates me the most when I'm trying to do iMessage vs Discord.

`Me:` What order of importance would you say they'd be if you had to rank them? Also, let me know if you have any more ideas because obviously the program can do more than just those. I just want to see what I should focus working on and what I could cut if time becomes a problem.

`Client:` Well, I would group all of the basic shortcuts into the top priority (like bold, italics, etc.), then emoji conversion, then preventing text from changing with backslashes, and then whatever the exponent arrow things would be. I feel like I've never really needed those.

`Me:` Now the other thing, how do you want to use the program? The things I'm thinking of right now are either in a command prompt, an app, or a background process that automatically does it.

`Client:` I think that just an app or background process would be the best. I feel like a background app is the most convenient, but it would depend on the difficulty to program it compared to a regular app.

`Client:` Another potential way I can think of is a web browser extension like Grammarly.

`Me:` The really funny thing is that I kinda already made it as a browser extension during 8th grade! It doesn't work anymore, and it only worked on Google Meet, but that would be a great way to use the program!

`Me:` Unfortunately, browser extensions can only be written in JavaScript, and the IA requires us to use Java. I think that a background app could work really well though! Are you thinking that it would automatically listen for every keypress and convert automatically or that you would hit a keybind like <kbd>CTRL</kbd>+<kbd>M</kbd> to change it?

`Client:` I like the keybind one a bit more, I think doing a specific command would be very manageable and help to differentiate from when you want to do it or not, y'know?

`Client:` So then you don't have to code one that uses backslashes to indicate that you don't want to consider the asterisks.

`Me:` Yeah, makes sense to me! Thanks a ton for your help!

`Client:` Of course! This is really exciting for me, so thanks for being fun to work with!