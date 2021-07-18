Battle will be turn-based.  
Character attacks before allied soldiers in order of top to bottom. 
Enemies will attack a random opponent each turn via an even uniform distribution.  
Enemies will spawn randomly via an even uniform distribution, amount of spawn enemies will be relative to path size.  
Enemies can exist on the same tile as a building.  
A tile can only hold one building.  
A potion can be consumed by pressing the "H" key.  
The player can hold a maximum of 8 cards before the cards are destroyed.  
The player can hold a maximum of 16 items before the items are destroyed.  
The player can have a maximum of 3 allied soldiers.  
The maximum amount of gold and experience is Integer.MAX_VALUE from java.math.  
When dropping an entity onto an already occupied pane, the overlapped entity will be destroyed.  
If player is given an amount of gold that makes them exceed the gold limit, their gold is set to gold limit  
If player is given an amount of exp that makes them exceed the exp limit, their exp is set to exp limit  
If a common item is obtained with a full inventory player is given 5 gold and 10 exp  
If a rare item is obtained with a full inventory player is given 50 gold and 100 exp  
If a common item is obtained with a full inventory player is given 5 gold and 10 exp  
A bag of gold gives 5 gold to the player  
Character's max health is set to 100  
Assume no enemies can enter hero castle  
Assume no battle will occur during the tick into hero castle  
Assume Progress per time tick is as follow:  
    1. Spawn enemies  
    2. Apply Building Debuffs to enemy  
    3. Apply Building Buffs to character  
    4. Run Battles if character is not on hero castle  
    5. Remove Building buffs from character  
    6. Remove Building Debuffs from enemy (Not used right now)  
Assume enemies can spawn on any structures, except Hero's Castle.  
Assume if the no tiles are available for the spawner to spawn enemies, then that tile is not placable for that type of spawner  
When passing through a village player gains 2 HP  
The radius for towers and campfires will be 5  
Enemies will take 30 dmg from traps  
When the shop is opened, player is forced to exit the shop manually in order for the game to continue  
All shop items are $10.  
All basic items sell for $5  
All rare items sell for $20  
When trying to buy with a full inventory, player is unable to purchase items.  
Assume buildings will not spawn enemies on top of character  
Zombies are spawned when cycle%1 == 0 (except for cycle 0)  
Vampires are spawned when cycle%5 == 0 (except for cycle 0)  
Items in equipped inventory cannot be sold at the shop  
A slug will have an attack of 5, battle radius of 2 and support radius of 2  
A Zombie will have an attack of 10, battle radius of 3 and support radius of 3  
A vampire will have an attack of 15, battle radius of 4 and support radius of 5  
A vampire's crit will deal damage for a random number between 2 and 4 turns
Assume rare items has a 5% chance of spawning and normal items has a 95% chance of spawning, and the number of chances of obtaining any specific type of rare items would be uniformly random among those chances
Assume only basic goals in milestone 2
Assume quantity of goal condition is smaller than Integer.MAX_VALUE

