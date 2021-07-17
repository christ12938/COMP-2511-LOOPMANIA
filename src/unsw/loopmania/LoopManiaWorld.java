package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Buildings.Building;
import unsw.loopmania.Buildings.CampfireBuilding;
import unsw.loopmania.Buildings.Spawner;
import unsw.loopmania.Cards.Card;
import unsw.loopmania.Enemies.Enemy;
import unsw.loopmania.Enemies.Slug;
import unsw.loopmania.Enemies.Vampire;
import unsw.loopmania.Enemies.Zombie;
import unsw.loopmania.Items.*;
import unsw.loopmania.Loaders.BuildingLoader;
import unsw.loopmania.Loaders.CardLoader;
import unsw.loopmania.Loaders.ItemLoader;
import unsw.loopmania.Types.BuildingType;
import unsw.loopmania.Types.ItemType;
import unsw.loopmania.Types.OverlappableEntityType;

/**
 * A backend world.
 *
 * A world can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 */
public class LoopManiaWorld {
    // TODO = add additional backend functionality

    public static final int unequippedInventoryWidth = 4;
    public static final int unequippedInventoryHeight = 4;

    public static final int alliedSoldierNumber = 3;

    /**
     * width of the world in GridPane cells
     */
    private int width;

    /**
     * height of the world in GridPane cells
     */
    private int height;

    /**
     * generic entitites - i.e. those which don't have dedicated fields
     */
    private List<Entity> nonSpecifiedEntities;

    private Character character;
    private HerosCastle herosCastle;

    /**
     * Cycle of the world
     */
    private int cycle = 0;

    // TODO = add more lists for other entities, for equipped inventory items, etc...

    // TODO = expand the range of enemies
    private List<Enemy> enemies;

    // TODO = expand the range of cards
    private List<Card> cardEntities;

    // List of equipped items
    private List<Equipable> equippedItems;

    // TODO = expand the range of items
    private List<Item> unequippedInventoryItems;

    // TODO = expand the range of buildings
    private List<Building> buildingEntities;

    /**
     * list of x,y coordinate pairs in the order by which moving entities traverse them
     */
    private List<Pair<Integer, Integer>> orderedPath;

    /**
     * create the world (constructor)
     *
     * @param width width of world in number of cells
     * @param height height of world in number of cells
     * @param orderedPath ordered list of x, y coordinate pairs representing position of path cells in world
     */
    public LoopManiaWorld(int width, int height, List<Pair<Integer, Integer>> orderedPath) {
        this.width = width;
        this.height = height;
        nonSpecifiedEntities = new ArrayList<>();
        character = null;
        enemies = new ArrayList<>();
        cardEntities = new ArrayList<>();
        equippedItems = new ArrayList<>();
        unequippedInventoryItems = new ArrayList<>();
        this.orderedPath = orderedPath;
        buildingEntities = new ArrayList<>();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Pair<Integer, Integer>> getOrderedPath(){
        return orderedPath;
    }

    public int getGold() {
        return character.getGold();
    }

    public int getExperience() {
        return character.getExperience();
    }

    /**
     * set the character. This is necessary because it is loaded as a special entity out of the file
     * @param character the character
     */
    public void setCharacter(Character character) {
        this.character = character;
    }

    public Character getCharacter(){
        return this.character;
    }

    /**
     * set the heros castle. This is necessary because it is loaded as a special entity out of the file.
     * @param herosCastle the heros castle
     */
    public void setHerosCastle(HerosCastle herosCastle){
        this.herosCastle = herosCastle;
    }
    /**
     * add a generic entity (without it's own dedicated method for adding to the world)
     * @param entity
     */
    public void addEntity(Entity entity) {
        // for adding non-specific entities (ones without another dedicated list)
        // TODO = if more specialised types being added from main menu, add more methods like this with specific input types...
        nonSpecifiedEntities.add(entity);
    }

    /**
     * spawns enemies if the conditions warrant it, adds to world
     * @return list of the enemies to be displayed on screen
     */
    public List<Enemy> possiblySpawnEnemies(){
        List<Enemy> spawningEnemies = new ArrayList<>();
        /* Get Slug Spawn Position */
        Pair<Integer, Integer> slugPos = possiblyGetSlugSpawnPosition();
        if (slugPos != null){
            int indexInPath = orderedPath.indexOf(slugPos);
            Enemy enemy = new Slug(new PathPosition(indexInPath, orderedPath));
            enemies.add(enemy);
            spawningEnemies.add(enemy);
        }

        /* Get Zombie Spawn Position */
        List<Pair<Integer, Integer>> zombiePos = possiblyGetSpawnerSpawnPositions(BuildingType.ZOMBIEPIT_BUILDING);
        for(Pair<Integer, Integer> i : zombiePos){
            int indexInPath = orderedPath.indexOf(i);
            Enemy enemy = new Zombie(new PathPosition(indexInPath, orderedPath));
            enemies.add(enemy);
            spawningEnemies.add(enemy);
        }

        /* Get Vampire Spawn Position */
        List<Pair<Integer, Integer>> vampirePos = possiblyGetSpawnerSpawnPositions(BuildingType.VAMPIRECASTLE_BUILDING);
        for(Pair<Integer, Integer> i : vampirePos){
            int indexInPath = orderedPath.indexOf(i);
            Enemy enemy = new Vampire(new PathPosition(indexInPath, orderedPath));
            enemies.add(enemy);
            spawningEnemies.add(enemy);
        }

        return spawningEnemies;
    }

    /**
     * kill an enemy
     * @param enemy enemy to be killed
     */
    private void killEnemy(Enemy enemy){
        enemy.destroy();
        enemies.remove(enemy);
    }

    /**
     * run the expected battles in the world, based on current world state
     * @return list of enemies which have been killed
     */
    public List<Enemy> runBattles() {
        // TODO- = modify this - currently the character automatically wins all battles without any damage!
        List<Enemy> defeatedEnemies = new ArrayList<Enemy>();
        for (Enemy e: enemies){
            // Pythagoras: a^2+b^2 < radius^2 to see if within radius
            // TODO = you should implement different RHS on this inequality, based on influence radii and battle radii
            if (Math.pow((character.getX()-e.getX()), 2) +  Math.pow((character.getY()-e.getY()), 2) < 4){
                // fight...
                defeatedEnemies.add(e);
            }
        }
        for (Enemy e: defeatedEnemies){
            // IMPORTANT = we kill enemies here, because killEnemy removes the enemy from the enemies list
            // if we killEnemy in prior loop, we get java.util.ConcurrentModificationException
            // due to mutating list we're iterating over
            killEnemy(e);
        }
        return defeatedEnemies;
    }

    /**
     * spawn a card in the world and return the card entity
     * @return a card to be spawned in the controller as a JavaFX node
     */
    public Card loadRandomCard(){
        // if adding more cards than have, remove the first card...
        if (cardEntities.size() >= getWidth()){
            // TODO- = give some cash/experience/item rewards for the discarding of the oldest card
            removeCard(0);
        }
        Card card = CardLoader.loadRandomCard(cardEntities.size());
        cardEntities.add(card);
        return card;
    }

    /**
     * remove card at a particular index of cards (position in gridpane of unplayed cards)
     * @param index the index of the card, from 0 to length-1
     */
    private void removeCard(int index){
        Card c = cardEntities.get(index);
        int x = c.getX();
        c.destroy();
        cardEntities.remove(index);
        shiftCardsDownFromXCoordinate(x);
    }

    /**
     * Load random unequipped item
     * @return
     */
    public Item loadRandomUnenquippedInventoryItem(){
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            removeItemByPositionInUnequippedInventoryItems(0);
            this.character.addExperience(10);
            this.character.addGold(5);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        // now we insert the new sword, as we know we have at least made a slot available...
        Item item = ItemLoader.loadRandomItem(firstAvailableSlot);
        if(item.getItemType() == ItemType.GOLD){
            character.addGold(100);
            return null;
        }
        unequippedInventoryItems.add(item);
        return item;
    }

    /**
     * spawn a sword in the world and return the sword entity
     * @return a sword to be spawned in the controller as a JavaFX node
     */
    public Sword addUnequippedSword(){
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            removeItemByPositionInUnequippedInventoryItems(0);
            this.character.addExperience(10);
            this.character.addGold(5);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        Sword sword = new Sword(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(sword);
        return sword;
    }

    public Stake addUnequippedStake(){
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            removeItemByPositionInUnequippedInventoryItems(0);
            this.character.addExperience(10);
            this.character.addGold(5);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }

        Stake stake = new Stake(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(stake);
        return stake;
    }

    public Staff addUnequippedStaff(){
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            removeItemByPositionInUnequippedInventoryItems(0);
            this.character.addExperience(10);
            this.character.addGold(5);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }

        Staff staff = new Staff(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(staff);
        return staff;
    }

    public Helmet addUnequippedHelmet(){
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            removeItemByPositionInUnequippedInventoryItems(0);
            this.character.addExperience(10);
            this.character.addGold(5);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        Helmet Helmet = new Helmet(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(Helmet);
        return Helmet;
    }

    public Shield addUnequippedShield(){
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            removeItemByPositionInUnequippedInventoryItems(0);
            this.character.addExperience(10);
            this.character.addGold(5);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }

        Shield shield = new Shield(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(shield);
        return shield;
    }

    public Armour addUnequippedArmour(){
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            removeItemByPositionInUnequippedInventoryItems(0);
            this.character.addExperience(10);
            this.character.addGold(5);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }

        Armour armour = new Armour(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(armour);
        return armour;
    }


    public Consumables addUnequippedHealthPotion(){
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            removeItemByPositionInUnequippedInventoryItems(0);
            this.character.addExperience(10);
            this.character.addGold(5);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }

        HealthPotion healthpotion = new HealthPotion(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(healthpotion);
        return healthpotion;
    }

    public TheOneRing addUnequippedTheOneRing(){
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            removeItemByPositionInUnequippedInventoryItems(0);
            this.character.addExperience(100);
            this.character.addGold(50);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }

        TheOneRing theonering = new TheOneRing(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(theonering);
        return theonering;
    }

    /**
     * remove an item by x,y coordinates
     * @param x x coordinate from 0 to width-1
     * @param y y coordinate from 0 to height-1
     */
    public void removeUnequippedInventoryItemByCoordinates(int x, int y){
        Item item = getUnequippedInventoryItemEntityByCoordinates(x, y);
        removeUnequippedInventoryItem(item);
    }

    /**
     * run moves which occur with every tick without needing to spawn anything immediately
     */
    public void runTickMoves(){
        System.err.println(herosCastle.getX());
        character.moveDownPath();

        moveEnemies();
        if(character.getX() == herosCastle.getX() && character.getY() == herosCastle.getY()){
            //TODO-: SHOP
            nextCycle();
        }
    }

    /**
     * remove an item from the unequipped inventory
     * @param item item to be removed
     */
    private void removeUnequippedInventoryItem(Item item){
        int x = item.getX();
        int y = item.getY();
        item.destroy();
        unequippedInventoryItems.remove(item);
        //shiftUnequippedInventoryItemsFromXYCoordinate(x, y);
    }

    /**
     * Add equipped item to list
     * @param item equipped item
     */
    public void addEquippedItem(Equipable item){
        equippedItems.add(item);
    }

    /**
     * remove an item from the equipped item slot
     * @param item item to be removed
     */
    public void removeEquippedItem(Item item){
        item.destroy();
        equippedItems.remove(item);
    }

    /**
     * Add unequipped item to list
     * @param item unequipped item
     */
    public void addUnequippedItem(Equipable item){
        unequippedInventoryItems.add(item);
    }

    /**
     * return an unequipped inventory item by x and y coordinates
     * assumes that no 2 unequipped inventory items share x and y coordinates
     * @param x x index from 0 to width-1
     * @param y y index from 0 to height-1
     * @return unequipped inventory item at the input position
     */
    private Item getUnequippedInventoryItemEntityByCoordinates(int x, int y){
        for (Item e: unequippedInventoryItems){
            if ((e.getX() == x) && (e.getY() == y)){
                return e;
            }
        }
        return null;
    }


    /**
     * Remove the entity that is overlapped on the pane
     * @param x x coordinate
     * @param y y coordinate
     * @param type type of entity being overlapped
     */
    public void RemoveOverlappedEntityByCoordinates(int x, int y, OverlappableEntityType type){
        Entity result = null;
        if(type == OverlappableEntityType.BUILDING){
            for(Entity e : buildingEntities){
                if(e.getX() == x && e.getY() == y){
                    result = e;
                    break;
                }
            }
            if(result != null){
                buildingEntities.remove(result);
                result.destroy();
                return;
            }
        }
        if(type == OverlappableEntityType.EQUIPPED_ITEM){
            for(Entity e : equippedItems){
                if(e.getX() == x && e.getY() == y){
                    result = e;
                    break;
                }
            }
            if(result != null){
                equippedItems.remove(result);
                result.destroy();
                return;
            }
        }
        if(type == OverlappableEntityType.UNEQUIPPED_INVENTORY_ITEM){
            for(Entity e : unequippedInventoryItems){
                if(e.getX() == x && e.getY() == y){
                    result = e;
                    break;
                }
            }
            if(result != null){
                unequippedInventoryItems.remove(result);
                result.destroy();
                return;
            }
        }
    }

    /**
     * Get the equipped item by corrdinates
     * @param x x index from 0 to width-1
     * @param y y index from 0 to height-1
     * @return equipped item
     */
    public Equipable getEquippedItemByCoordinates(int x, int y){
        for (Equipable e: equippedItems){
            if ((e.getX() == x) && (e.getY() == y)){
                return e;
            }
        }
        return null;
    }


    /**
     * Get the unequipped item by corrdinates
     * @param x x index from 0 to width-1
     * @param y y index from 0 to height-1
     * @return unequipped item
     */
    public Equipable getUnequippedItemByCoordinates(int x, int y){
        for (Item e: unequippedInventoryItems){
            if ((e.getX() == x) && (e.getY() == y)){
                //Redundant, just in case
                if(e instanceof Equipable){
                    return (Equipable)e;
                }else{
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * remove item at a particular index in the unequipped inventory items list (this is ordered based on age in the starter code)
     * @param index index from 0 to length-1
     */
    private void removeItemByPositionInUnequippedInventoryItems(int index){
        Item item = unequippedInventoryItems.get(index);
        item.destroy();
        unequippedInventoryItems.remove(index);
    }

    /**
     * get the first pair of x,y coordinates which don't have any items in it in the unequipped inventory
     * @return x,y coordinate pair
     */
    private Pair<Integer, Integer> getFirstAvailableSlotForItem(){
        // first available slot for an item...
        // IMPORTANT - have to check by y then x, since trying to find first available slot defined by looking row by row
        for (int y=0; y<unequippedInventoryHeight; y++){
            for (int x=0; x<unequippedInventoryWidth; x++){
                if (getUnequippedInventoryItemEntityByCoordinates(x, y) == null){
                    return new Pair<Integer, Integer>(x, y);
                }
            }
        }
        return null;
    }

    /**
     * shift card coordinates down starting from x coordinate
     * @param x x coordinate which can range from 0 to width-1
     */
    private void shiftCardsDownFromXCoordinate(int x){
        for (Card c: cardEntities){
            if (c.getX() >= x){
                c.x().set(c.getX()-1);
            }
        }
    }

    //TODO: DEBUG
    /**
     * shift inventory items down starting from x and y coordinates
     * @param x
     * @param y
     */
    private void shiftUnequippedInventoryItemsFromXYCoordinate(int x, int y){
        boolean flag = false;
        for(Item i : unequippedInventoryItems){
            if(!flag && i.getY() >= y && i.getX() >= x){
                flag = true;
            }
            if(!flag) continue;
            int newX = (i.getX() == 0) ? (unequippedInventoryWidth - 1) : i.getX() - 1;
            int newY = (i.getX() == 0) ? i.getY() - 1 : i.getY();
            i.x().set(newX);
            i.y().set(newY);
        }
    }

    /**
     * move all enemies
     */
    private void moveEnemies() {
        // TODO = expand to more types of enemy
        for (Enemy e: enemies){
            e.move();
            if(MovingEntityOnBuilding(e)){
                Building b = movingEntityLocationBuilding(e);
                if(b.getBuildingType()==BuildingType.TRAP_BUILDING){
                    //need enemy functions to be written
                    //e.takeDamage(30);

                    buildingEntities.remove(b);
                }
            }
        }
    }

    /**
     * get a randomly generated position which could be used to spawn a slug
     * @return null if random choice is that wont be spawning an enemy or it isn't possible, or random coordinate pair if should go ahead
     */
    private Pair<Integer, Integer> possiblyGetSlugSpawnPosition(){
        // TODO = modify this

        // has a chance spawning a basic enemy on a tile the character isn't on or immediately before or after (currently space required = 2)...
        Random rand = new Random();
        int choice = rand.nextInt(2); // TODO = change based on spec... currently low value for dev purposes...
        // TODO = change based on spec
        if ((choice == 0) && (enemies.size() < 2)){
            List<Pair<Integer, Integer>> orderedPathSpawnCandidates = new ArrayList<>();
            int indexPosition = orderedPath.indexOf(new Pair<Integer, Integer>(character.getX(), character.getY()));
            // inclusive start and exclusive end of range of positions not allowed
            int startNotAllowed = (indexPosition - 2 + orderedPath.size())%orderedPath.size();
            int endNotAllowed = (indexPosition + 3)%orderedPath.size();
            // note terminating condition has to be != rather than < since wrap around...
            for (int i=endNotAllowed; i!=startNotAllowed; i=(i+1)%orderedPath.size()){
                orderedPathSpawnCandidates.add(orderedPath.get(i));
            }

            // choose random choice
            Pair<Integer, Integer> spawnPosition = orderedPathSpawnCandidates.get(rand.nextInt(orderedPathSpawnCandidates.size()));

            return spawnPosition;
        }
        return null;
    }

    /**
     * Get all spawner possible spawning locations on that cycle
     * @param type Spawner type
     * @return List of possibly spawn locations
     */
    private List<Pair<Integer, Integer>> possiblyGetSpawnerSpawnPositions(BuildingType type){
        List<Pair<Integer, Integer>> result = new ArrayList<>();
        for(Building b : buildingEntities){
            if(cycle != 0 && b.getBuildingType() == type
                && !((Spawner)b).getHasSpawned() && cycle%((Spawner)b).getSpawningCycle() == 0){
                List<Pair<Integer, Integer>> temp = ((Spawner)b).getSpawningTiles();
                result.add(temp.get(new Random().nextInt(temp.size())));
                ((Spawner)b).setHasSpawned(true);
            }
        }
        return result;
    }

    /**
     * remove a card by its x, y coordinates
     * @param cardNodeX x index from 0 to width-1 of card to be removed
     * @param cardNodeY y index from 0 to height-1 of card to be removed
     * @param buildingNodeX x index from 0 to width-1 of building to be added
     * @param buildingNodeY y index from 0 to height-1 of building to be added
     */
    public Building convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX, int buildingNodeY) {
        // start by getting card
        Card card = null;
        for (Card c: cardEntities){
            if ((c.getX() == cardNodeX) && (c.getY() == cardNodeY)){
                card = c;
                break;
            }
        }

        // now spawn building
        Building newBuilding = BuildingLoader.loadBuilding(card.getCardType(), buildingNodeX, buildingNodeY);
        buildingEntities.add(newBuilding);

        // destroy the card
        card.destroy();
        cardEntities.remove(card);
        shiftCardsDownFromXCoordinate(cardNodeX);

        return newBuilding;
    }

    /**
     * Perform actions before heading to next cycle
     */
    public void nextCycle(){
        this.cycle++;
        /* Reset all spawner spawning behaviour */
        /* Kind of observer pattern (DEBUG) */
        for(Building b : buildingEntities){
            if(b instanceof Spawner){
                ((Spawner)b).setHasSpawned(false);
            }
        }
    }

    public boolean MovingEntityOnBuilding(MovingEntity e){
        for (Building b : buildingEntities) {
            if((b.getX() == e.getX()) && (b.getY() == e.getY())){
                return true;

            }
        }
        return false;
    }

    // DO NOT call when the character is not on a building
    public Building movingEntityLocationBuilding(MovingEntity e){
        Building firstb = buildingEntities.get(0);
        for (Building b : buildingEntities) {
            if((b.getX() == e.getX()) && (b.getY() == e.getY())){
                return b;
            }
        }

        //returns first building if none is found (note: shouldn't happpen with correct use)
        return firstb;
    }

    public void subtractCharacterHP(double amount){
        this.character.subtractHealth(amount);
    }

    public double getCharacterHP(){
        return this.character.getHealth();
    }

    //use for battle calculation
    //check at the start of each battle to determine
    //character atk
    public Boolean inRangeOfCampfire(int x, int y){
        for (Building b : buildingEntities) {
            if(b.getBuildingType()==BuildingType.CAMPFIRE_BUILDING){
                if(b.inRange(x, y)){
                    return true;
                }
            }
        }
        return false;
    }

    //use for battle calculation to determine dmg to enemies
    public Boolean inRangeOfTower(int x, int y){
        for (Building b : buildingEntities) {
            if(b.getBuildingType()==BuildingType.TOWER_BUILDING){
                if(b.inRange(x, y)){
                    return true;
                }
            }
        }
        return false;
    }
}
