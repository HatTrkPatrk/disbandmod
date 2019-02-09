# disbandmod
Automatically and instantly disband inactive deeds

With this mod installed, servers can specify a number of days after which a deed will be automatically disbanded if its mayor has been inactive for that long. This mod (unlike other upkeep/tax mods) will perform the disband instantly after that period of time, rather than just increasing drain on upkeep: if you specify 30 days and the mayor of a village hasn't logged in in 30 days, that deed goes poof.

Tested on a Wurm Unlimited Dedicated Server v1.8.0.3 with ago1024's server modloader version 0.40.

# configuration
Default config file

Default config file:

> # Automatically disband deeds when the mayor has been offline for a certain number of days?
> # The number of days can be configured below; if the mayor has been inactive/offline for
> # that number of days, the village/deed will disband regardless of how much upkeep it has.
> # Default: false
> autoDisbandInactive=true
>
> # If enabled (above), set the number of days here. If the mayor is offline/inactive for this
> # period of time, the village/deed will disband regardless of how much upkeep it has.
> # Default: 30
> autoDisbandInactiveDays=30
>
