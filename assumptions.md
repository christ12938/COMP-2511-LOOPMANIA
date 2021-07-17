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
    1. Apply Building Debuffs to enemy
    2. Apply Building Buffs to character
    3. Run Battles
    4. Remove Building buffs from character
    5. Remove Building Debuffs from enemy (Not used right now)
    6. Spawn enemies
                 