## Features / Tasklist

- **Spawn System** ✔️ 
  - Persistent spawn location ✔️
  - Immediate saving on set command ✔️
  - Auto-load on plugin start ✔️

- **General Listeners** ✔️  
  - Event handling for core gameplay mechanics  

- **YAML Configuration** ❌  
  - Base config files ✔️
  - Fully configurable points system, spawn limits, and item settings  

- **Secured Zone (Safe Zone)** ✔️  
  - Players can leave, but can't return ✔️  
  - Void, and region limit ✔️  
  - Void protection applies only to "ingame" players ✔️  

- **Void/Boundary System** ✔️  
  - Instant death below configured Y-level ✔️  
  - Only affects "ingame" players (no creative/spectator) ✔️  
  - Configurable minimum Y per map ✔️

- **Damage / Death System** ❌  
  - Custom damage tracking ✔️  
  - Persistent last-hit tracking (including timed effects)  
  - Supports kill-based perks and rewards  
  - Kill cause handling for stats and coins ✔️  

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
  - Kill rewards ✔️  
  - Void kill scaling (based on victim's HP)  
  - Void suicide penalties  
  - Death cause-based point adjustments  
  - Fully configurable point system  
  - Coins usable for future shops/upgrades  

- **MySQL Integration** ❌  
  - Future migration target for persistence and scaling  

- **Redis Integration** ❌  
  - For external system communication or faster caching (future)

- **Miscellaneous** ❌
  - Spawn position → later database  
  - Safe Zone: cuboid, return-blocking ✔️  
  - Void system: Minimum Y, only affects ingame players ✔️  
  - Point system: Cause-based, configurable gain/loss per event  
  - Item layout: Player-defined and persistent  

- **Non-code / Extra** ❌
  - Create spigot page ❌
  - Create a map auto install system ❌
  - Create public playable maps for download ❌
