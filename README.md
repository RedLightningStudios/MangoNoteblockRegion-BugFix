# CYTNoteblockRegion-BugFix
### Useful plugin, silly name

This is a fix for a plugin created by lukemango fixing the music not playing while standing in multiple regions, it adds priority support, and allows reloading the config via a command.

Known Bugs:
Songs do not stop playing after reloading the config if the song was changed (Even if you leave the region). Planning to address this soon.

Original Description (modified to support the changes):
This plugin was originally made by me for my server because I couldn't find anything similar on Spigot and it was fairly easy to make, therefore here it is for you to use

https://www.youtube.com/watch?v=90DSvk1PF5A

Features
Play .nbs files in WorldGuard regions
Ability to queue multiple songs for one region
Ability to loop
Ability to shuffle
Ability to customise volume
Runs asynchronously (no server lag!)
Open source, feel free to contribute​

Requirements
WorldGuard (7.0.7 or above)
NoteBlockAPI (1.6.1 or above) (Click Here)​

Commands:
/cytnoteblockregion reload or /cyt reload for short.

Permissions: cytnoteblockregion.reload

Configuration
A default configuration can be found here.
Put songs (must be .nbs files) in the /plugins/CYTNoteblockRegion/music folder (run the plugin to generate this folder).​

Support
Please make an issue on GitHub if you find an issue or would like something added.
I can't guarantee suggestions will be added but I'll try and keep this up to date should something break as we also use it.
I've only tested this on 1.19 with the versions of plugins listed under Requirements, this may work on older versions (good luck?).​
