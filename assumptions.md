Battle will be turn-based.
Slug will spawn randomly via an even uniform distribution, amount of spawn slugs will be relative to path size.
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
A bag of gold gives 5 gold to the player
Character's max health is set to 100
Assume no enemies can enter hero castle
Assume no battle will occur during the tick into hero castle
Assume Progress per time tick is as follow:
1. Move character and enemies
2. Check if character is on a spawned item and add it to the inventory
3. Spawn items on tiles
4. Spawn enemies
5. Apply Traps to enemies
6. Apply static (permanant) Building Buffs to character
7. Run Battles if character is not on hero castle
P.S. Equipped items buff are in real time, so wont be included in the timeline

Assume enemies can spawn on any structures, except Hero's Castle.
Assume if the no tiles are available for the spawner to spawn enemies, then that tile is not placable for that type of spawner
When passing through a village player gains 10 HP
The radius for towers and campfires will be 5
Towers don't stack
Towers do 5 damage
Enemies will take 10 dmg from traps
When the shop is opened, player is forced to exit the shop manually in order for the game to continue
All shop items are $10.
All basic items sell for $5
All rare items sell for $20
When trying to buy with a full inventory, player is unable to purchase items.
Assume buildings will not spawn enemies on top of character
Zombies are spawned when cycle%1 == 0 (except for cycle 0)
Vampires are spawned when cycle%5 == 0 (except for cycle 0)
Items in equipped inventory cannot be sold at the shop
Assume rare items has a 5% chance of spawning and normal items has a 95% chance of spawning, and the number of chances of obtaining any specific type of rare items would be uniformly random among those chances
Assume only basic goals in milestone 2
Assume quantity of goal condition is smaller than Integer.MAX_VALUE
Assume items that can be spawn on tiles have a spawn chance of 5%.
Assume items that can be spawn on tiles will not spawn on any entities
Assume enemies can pass through items
Assume 10 experiece will be added if the inventory is full when picking up the item.
Assume 10 gold will be added if the inventory is full when picking up items.

Battle process:
1. Randomly pick an enemy

Character's turn
2. Character attacks enemy
3. Allied Soldiers attack enemy (If any)
4. Towers attack enemy (If any)

If enemy is defeated, loom for next enemy

Enemy's turn
5. Enemy attack character
6. Enemy attack allied soldiers (if any)

Stats:
Allied Soldiers: Health = 30, Attack =5
Character: Health = 100, Attack = 5, Defense = 0
Slug: Health = 10, Atack = 5, Crit chance = 10%, Crit multiplier = 150%, Battle Radius = 2, Support Radius = 2
Zombie: Health = 10, Atack = 10, Crit chance = 10%, Crit multiplier = N/A, Battle Radius = 3, Support Radius = 3
Vampire: Health = 20, Atack = 20, Crit chance = 20%, Crit multiplier = 100 - 200% (Uniformly random), Battle Radius = 3, Support Radius = 4
Tree stump decreases bossses damage by 70%
Elan Muske has a 20% chance of healing all battling enemies (including self) for 10 health
Doggie has a 20% chance of stunning the character. (This effect will not accumulate)
Experience rewards for each type of enemies:
1. Slug = 10
2. Zombie = 100
3. Vampire = 500
4. Doggie = 1000
5. Elan Muske = 5000
Assume Bosses only spawn once
Assume in confusing mode, a rare item will never get its own additional behaviour.
Assume in confusing mode, if there is only one rare item, there will be no additional behaviour.
Assume in confusing mode, stats buffing additional behaviour will not accumulate. (i.e. 2 Aduril triple damage behaviour will not make it *9, that would be too broken).
The value of doggieCoin fluctuates between 1 and 20 dollars randomly. When Elan Muske appears, the value of it will fluctuates between 40 and 50. This effect will revert when Elan Muske is killed.
Elan Muske has a 0.85% chance of jumping over the character. (Around 0.5% exactly since Elan Muske has a battle radius of 2, so 0.85^4 ~ 0.5, assuming he did not move in those 4 steps)



