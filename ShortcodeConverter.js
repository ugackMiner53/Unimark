const shortcodes = {};

let outputObject = {};
for (let [key, value] of Object.entries(shortcodes)) {
    if (typeof value != "string") {
        value.forEach((arrValue) => {
            outputObject[arrValue] = key;
        });
    } else {
        outputObject[value] = key;
    }
}