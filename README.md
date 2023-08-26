<h1>THIS MOD IS OBSOLETE</h1>
<h2>It likely does not work anymore on the most recent WU version and may cause serious issues if used.</h2>
<h3>If you insist on using it, <b>make frequent backups</b></h3>
<h4>And if you're a developer interested in making it work again, feel free to fork the repository!</h4>

# disbandmod
Automatically and instantly disband inactive deeds

With this mod installed, servers can specify a number of days after which a deed will be automatically disbanded if its mayor has been inactive for that long. This mod (unlike other upkeep/tax mods) will perform the disband instantly after that period of time, rather than just increasing drain on upkeep: if you specify 30 days and the mayor of a village hasn't logged in in 30 days, that deed goes poof.

Tested on a Wurm Unlimited Dedicated Server v1.8.0.3 with ago1024's server modloader version 0.40.

# configuration
The following settings are available:

* autoDisbandInactive - true/false, determines whether deeds will be disbanded after the mayor is inactive for the configured number of days. Default is false.
* autoDisbandInactiveDays - integer, how many days of inactivity for the mayor before disbanding the deed. Default is 30.

# prerequisites
Requires [SinduskLibrary](https://github.com/Sindusk/sindusklibrary/releases).
