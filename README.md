## Features / Tasklist

- **Spawn System** ❌  
  - Persistent spawn location ("spawn.yml", future database support)  
  - Immediate saving on set command  
  - Auto-load on plugin start  

- **General Listeners** ❌  
  - Event handling for core gameplay mechanics  

- **YAML Configuration** ❌  
  - Base config files  
  - Fully configurable points system, spawn limits, and item settings  

- **Secured Zone (Safe Zone)** ❌  
  - Players can leave, but can't return  
  - Height and radius limits (configurable per map)  
  - Void protection applies only to "ingame" players  
  - Efficient radius check for high player count  

- **Void/Boundary System** ❌  
  - Instant death below configured Y-level  
  - Only affects "ingame" players (no creative/spectator)  
  - Configurable minimum Y per map  

- **Damage / Death System** ❌  
  - Custom damage tracking  
  - Persistent last-hit tracking (including timed effects)  
  - Supports kill-based perks and rewards  
  - Kill cause handling for stats and coins  

- **Item Sorting** ❌  
  - Player-specific item layout  
  - Full inventory sort (not just hotbar)  
  - Supports custom items, weapon levels, and future item types  
  - Persistent per-player inventory layout  

- **Custom PvP Items** ❌  
  - Weapons, potions, buffs, debuffs  
  - Perks, kits, cooldown control  
  - Expandable for new item types  

- **Persistent Item Sorting** ❌  
  - Player-specific, saved and restored between sessions  

- **Stats and Coins** ❌  
  - Kill rewards  
  - Void kill scaling (based on victim's HP)  
  - Void suicide penalties  
  - Death cause-based point adjustments  
  - Fully configurable point system  
  - Coins usable for future shops/upgrades  

- **MySQL Integration** ❌  
  - Future migration target for persistence and scaling  

- **Redis Integration** ❌  
  - For external system communication or faster caching (future)
