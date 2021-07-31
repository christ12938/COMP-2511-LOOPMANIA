package unsw.loopmania;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.javatuples.Pair;
import org.junit.Test.None;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import unsw.loopmania.Buildings.Building;
import unsw.loopmania.Buildings.Spawner;
import unsw.loopmania.Buildings.TowerBuilding;
import unsw.loopmania.Buildings.TrapBuilding;
import unsw.loopmania.Buildings.VillageBuilding;
import unsw.loopmania.Cards.Card;
import unsw.loopmania.Enemies.Doggie;
import unsw.loopmania.Enemies.ElanMuske;
import unsw.loopmania.Enemies.Enemy;
import unsw.loopmania.Enemies.Slug;
import unsw.loopmania.Enemies.Vampire;
import unsw.loopmania.Enemies.Zombie;
import unsw.loopmania.Items.*;
import unsw.loopmania.Loaders.BuildingLoader;
import unsw.loopmania.Loaders.CardLoader;
import unsw.loopmania.Loaders.ItemLoader;
import unsw.loopmania.Types.BuildingType;
import unsw.loopmania.Types.CardType;
import unsw.loopmania.Types.EnemyType;
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
    public static HerosCastle herosCastle;

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

    private LoopManiaWorldController controller = null;

    private HumanPlayer humanPlayer;
    private Character character;

    //for testing
    private boolean toweractivated = false;

    /**
     * Cycle of the world
     */
    private volatile int cycle = 0;

    // TODO = add more lists for other entities, for equipped inventory items, etc...

    // TODO = expand the range of enemies
    private List<Enemy> enemies;

    // TODO = expand the range of cards
    private List<Card> cardEntities;

    // TODO = expand the range of items
    private List<Item> unequippedInventoryItems;

    // TODO = expand the range of buildings
    private List<Building> buildingEntities;

    /**
     * Items that spawned on tiles
     */
    private List<Item> spawnedItems;

    //Rare items that specified in json
    private List <ItemType> rareItemsAvailable;

    /**
     * list of x,y coordinate pairs in the order by which moving entities traverse them
     */
    private List<Pair<Integer, Integer>> orderedPath;

    public static Random rand = new Random();

    private MediaPlayer moneyPickupAudioPlayer;
    private MediaPlayer activateTrapAudioPlayer;
    
    

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
        unequippedInventoryItems = new ArrayList<>();
        this.orderedPath = orderedPath;
        buildingEntities = new ArrayList<>();
        rareItemsAvailable = new ArrayList<>();
        spawnedItems = new ArrayList<>();
        
        String pickupMoney = new File("src/Music/money_pickup.mp3").toURI().toString();
        String activateTrapAudio = new File("src/Music/TrapActivate.mp3").toURI().toString();

        activateTrapAudioPlayer = new MediaPlayer(new Media(activateTrapAudio));
        moneyPickupAudioPlayer = new MediaPlayer(new Media(pickupMoney));
        moneyPickupAudioPlayer.setVolume(0.03);
        activateTrapAudioPlayer.setVolume(0.03);
    }

    public void setController(LoopManiaWorldController controller){
        this.controller = controller;
        character.setObserver(controller);
        humanPlayer.setController(controller);
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

    public void addGold(int amount) {
        character.addGold(amount);
    }

    public void minusGold(int amount) {
        character.minusGold(amount);
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

    /**
     * Set human player in world as an observer
     * @param humanPlayer
     */
    public void setHumanPlayer(HumanPlayer humanPlayer){
        this.humanPlayer = humanPlayer;
    }

    public int getCharacterAttack() {
        return this.character.getAttack();
    }

    public int getCharacterDefense() {
        return this.character.getDefense();
    }

    public double getCharacterCurrentHp() {
        return this.character.getCurrentHealth();
    }

    public void decreaseCharacterHp(long amount) {
        this.character.minusHealth(amount);
        return;
    }

    public void increaseCharacterHp(double amount) {
        this.character.addHealth(amount);
        return;
    }

    public Character getCharacter(){
        return this.character;
    }

    public void notifyHumanPlayer(){
        humanPlayer.updateGoalState();
    }

    public boolean hasHumanPlayerWon(){
        return humanPlayer.hasWon();
    }

    public void addRareItemsAvailable(String rareItem){
        //Add more rare items in milestone 3
        switch(rareItem){
            case "the_one_ring":
                rareItemsAvailable.add(ItemType.THE_ONE_RING);
                break;
            case "anduril_flame_of_the_west":
                rareItemsAvailable.add(ItemType.ANDURIL);
                break;
            case "tree_stump":
                rareItemsAvailable.add(ItemType.TREE_STUMP);
                break;
            default:
                return;
        }
    }


    /**
     * Add allied Soldier in the backend and return to the front end
     * @return
     */
    public AlliedSoldier addAlliedSoldier(){
        int firstAvailableSlot = getFirstAvailableSlotForAlliedSoldier();
        if(firstAvailableSlot < alliedSoldierNumber){
            /**
             * Get a reference to the character since allied soldiers always move with character
             */
            AlliedSoldier alliedSoldier = new AlliedSoldier(character.getPathPosition(), firstAvailableSlot);
            return alliedSoldier;
        }
        return null;
    }

    /**
     * Backend code for removing allied soldier
     * @param alliedSoldier
     */
    public void removeAlliedSoldier(AlliedSoldier alliedSoldier){
        if(alliedSoldier == null) return;
        character.removeAlliedSoldier(alliedSoldier);
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
     * Get Location of spawning items
     * @return
     */
    public List<Item> possiblySpawnItems(){
        if(rand.nextDouble() >= 0.05) return new ArrayList<>();
        List<Item> spawningItems = new ArrayList<>();
        /* Get gold spawn position */
        Pair<Integer, Integer> goldPos = possiblyGetItemSpawnPosition();
        if (goldPos != null){
            Item gold = new Gold(new SimpleIntegerProperty(goldPos.getValue0()), new SimpleIntegerProperty(goldPos.getValue1()));
            spawnedItems.add(gold);
            spawningItems.add(gold);
        }
        /* Get health Potion spawn position */
        Pair<Integer, Integer> healthPotionPos = possiblyGetItemSpawnPosition();
        if (healthPotionPos != null){
            Item healthPotion = new HealthPotion(new SimpleIntegerProperty(healthPotionPos.getValue0()), new SimpleIntegerProperty(healthPotionPos.getValue1()));
            spawnedItems.add(healthPotion);
            spawningItems.add(healthPotion);
        }
        return spawningItems;
    }

    /**
     * spawns enemies if the conditions warrant it, adds to world
     * @return list of the enemies to be displayed on screen
     */
    //DEBUG!!!! WONT SPAWN WHEN TILES ARE FULL
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

        /* Get Bosses Spawn Position */

        /* Spawn Doggie */
        if(cycle != 0 && cycle%20 == 0 && !hasDoggie()){
            Pair<Integer, Integer> doggiePos = possiblyGetBossSpawnPosition();
            if (doggiePos != null){
                int indexInPath = orderedPath.indexOf(doggiePos);
                Enemy enemy = new Doggie(new PathPosition(indexInPath, orderedPath));
                enemies.add(enemy);
                spawningEnemies.add(enemy);
            }
        }

        /* Spawn Elon Musk */
        if(cycle != 0 && cycle%40 == 0 && !hasElanMuske() && character.getExperience() >= 10000){
            Pair<Integer, Integer> elonMuskPos = possiblyGetBossSpawnPosition();
            if (elonMuskPos != null){
                int indexInPath = orderedPath.indexOf(elonMuskPos);
                Enemy enemy = new ElanMuske(new PathPosition(indexInPath, orderedPath));
                enemies.add(enemy);
                spawningEnemies.add(enemy);
            }
        }

        return spawningEnemies;
    }

    private boolean hasDoggie(){
        for(Enemy e : enemies){
            if(e.getEnemyType() == EnemyType.DOGGIE) return true;
        }
        return false;
    }

    private boolean hasElanMuske(){
        for(Enemy e : enemies){
            if(e.getEnemyType() == EnemyType.ELAN_MUSKE) return true;
        }
        return false;
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
        List<Enemy> defeatedEnemies = new ArrayList<Enemy>();
        List<Enemy> battleEnemies = new ArrayList<Enemy>();
        List<Enemy> supportEnemies = new ArrayList<Enemy>();
        List<Enemy> trancedEnemies = new ArrayList<Enemy>();
        List<AlliedSoldier> trancedSoldiers = new ArrayList<AlliedSoldier>();

        /* First determine if character is in battle radius of enemies  */
        for (Enemy e: enemies){
            // Pythagoras: a^2+b^2 < radius^2 to see if within radius
            if(e.inBattleRadius(character)){
                battleEnemies.add(e);
            }
        }

        /* Then determine all the supporting enemies */
        for(Enemy bEnemy : battleEnemies){
            for(Enemy e : enemies){
                if(!battleEnemies.contains(e) && bEnemy.inSupportRadius(e)){
                    supportEnemies.add(e);
                }
            }
        }

        /* Combine both list together */
        battleEnemies.addAll(supportEnemies);

        /* Account for campfire */
        applyCampfireBuffToCharacter();

        /* Fight */
        while (!battleEnemies.isEmpty()) {
            /* Random Choice when selecting enemies */
            int choice = rand.nextInt(battleEnemies.size());
            Enemy attackedEnemy = battleEnemies.get(choice);
            System.out.println("Enemy fighting: " + attackedEnemy);
            /**
             * Always starts with attacks from character side first, 
             * character -> allied soldiers -> tower
             */
            /* Fight until character or enemy is dead */
            while(true){
                boolean isCharacterStunned = false;
                /* Character's turn */
                if(!isCharacterStunned){
                    if(attackedEnemy.getEnemyType() != EnemyType.ELAN_MUSKE
                        && attackedEnemy.getEnemyType() != EnemyType.DOGGIE
                        && character.isNextAttackTrance()){
                        AlliedSoldier trancedAlliedSoldier = addAlliedSoldier();
                        if(trancedAlliedSoldier == null){
                            character.dealDamage(attackedEnemy);
                        }else{
                            battleEnemies.remove(attackedEnemy);
                            trancedEnemies.add(attackedEnemy);
                            trancedSoldiers.add(trancedAlliedSoldier);
                            trancedAlliedSoldier.startTranceTurn();
                            character.getAlliedSoldiers().add(trancedAlliedSoldier);
                            break;
                        }
                    }else{
                        character.dealDamage(attackedEnemy);
                    }
                }
                for(AlliedSoldier alliedSoldier : character.getAlliedSoldiers()){
                    alliedSoldier.dealDamage(attackedEnemy);
                }
                /* Account for tower */
                applyTowerDamageToEnemy(attackedEnemy);

                /* Check if enemy is defeated */
                if(attackedEnemy.isDefeated()){
                    defeatedEnemies.add(attackedEnemy);
                    battleEnemies.remove(attackedEnemy);
                    break;
                }
                /* Enemy's turn */
                if(attackedEnemy.getEnemyType() == EnemyType.DOGGIE){
                    if(isCharacterStunned){
                        isCharacterStunned = false;
                    }else if(rand.nextDouble() < 0.2){
                        isCharacterStunned = true;
                    }
                }else if(attackedEnemy.getEnemyType() == EnemyType.ELAN_MUSKE){
                    ((ElanMuske)attackedEnemy).healEnemies(battleEnemies);
                }
                attackedEnemy.dealDamage(character);
                if(character.isDefeated()) break;
                for(AlliedSoldier alliedSoldier : new ArrayList<AlliedSoldier>(character.getAlliedSoldiers())){
                    if(attackedEnemy.getEnemyType() == EnemyType.ZOMBIE && ((Zombie)attackedEnemy).isNextAttackCritical()){
                        battleEnemies.add(alliedSoldier.transformToZombie());
                        removeAlliedSoldier(alliedSoldier);
                    }else{
                        attackedEnemy.dealDamage(alliedSoldier);
                        if(alliedSoldier.isDefeated()) removeAlliedSoldier(alliedSoldier);
                    }
                }
            }
            
            List<AlliedSoldier> temp = new ArrayList<>();
            for(int i = 0; i < trancedSoldiers.size(); i++){
                trancedSoldiers.get(i).nextTranceTurn();
                /* if tranced soldier is killed */
                if(trancedSoldiers.get(i).shouldExist().get() && trancedSoldiers.get(i).getTranceTurn() < 0){
                    battleEnemies.add(trancedEnemies.get(i));
                    temp.add(trancedSoldiers.get(i));
                }
            }

            for(AlliedSoldier trancedSoldier : temp){
                int index = trancedSoldiers.indexOf(trancedSoldier);
                trancedEnemies.remove(index);
                character.getAlliedSoldiers().remove(trancedSoldiers.get(index));
                trancedSoldiers.remove(index);
            }
        }

        removeCampfireBuffFromCharacter();

        for(int i = 0; i < trancedEnemies.size(); i++){
            defeatedEnemies.add(trancedEnemies.get(i));
            if(character.getAlliedSoldiers().contains(trancedSoldiers.get(i))){
                character.getAlliedSoldiers().remove(trancedSoldiers.get(i));
            }
        }
        
        for (Enemy e: defeatedEnemies){
            // IMPORTANT = we kill enemies here, because killEnemy removes the enemy from the enemies list
            // if we killEnemy in prior loop, we get java.util.ConcurrentModificationException
            // due to mutating list we're iterating over
            System.out.println("Killed enemy: " + e.getEnemyType());
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
        if (cardEntities.size() >= getWidth() && controller != null){
            // TODO- = give some cash/experience/item rewards for the discarding of the oldest card
            controller.loadRandomItem();
            this.character.addExperience(10);
            this.character.addGold(5);
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
            Item item = ItemLoader.loadRandomItem(new Pair<Integer, Integer>(unequippedInventoryItems.get(0).getX(), unequippedInventoryItems.get(0).getY()), rareItemsAvailable);
            if(item.getItemType() == ItemType.GOLD){
                character.addGold(5);
                return null;
            }
            removeItemByPositionInUnequippedInventoryItems(0);
            this.character.addExperience(10);
            this.character.addGold(5);
            addUnequippedItem(item);
            return item;
        }else{
            Item item = ItemLoader.loadRandomItem(firstAvailableSlot, rareItemsAvailable);
            if(item.getItemType() == ItemType.GOLD){
                character.addGold(5);
                return null;
            }
            addUnequippedItem(item);
            return item;
        }
    }

    /**
     * Get all the buildings that contains the entity within the building radius
     * @param e the entiy being passed
     * @return
     */
    public List<Building> getBuildingsWithinRadiusOfEntity(Entity e){
        List<Building> result = new ArrayList<>();
        for(Building b : buildingEntities){
            if (Math.pow((b.getX()-e.getX()), 2) +  Math.pow((b.getY()-e.getY()), 2) < Math.pow(b.getBuildingRadius(), 2)){
                result.add(b);
            }
        }
        return result;
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

    /**
     * spawn a stake in the world and return the stake entity
     * @return a stake to be spawned in the controller as a JavaFX node
     */
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

    /**
     * spawn a staff in the world and return the staff entity
     * @return a staff to be spawned in the controller as a JavaFX node
     */
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

    /**
     * spawn a helmet in the world and return the helmet entity
     * @return a helmet to be spawned in the controller as a JavaFX node
     */
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

    /**
     * spawn a shield in the world and return the shield entity
     * @return a shield to be spawned in the controller as a JavaFX node
     */
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

    /**
     * spawn a armour in the world and return the armour entity
     * @return a armour to be spawned in the controller as a JavaFX node
     */
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

    /**
     * spawn a health potion in the world and return the health potion entity
     * @return a health potion to be spawned in the controller as a JavaFX node
     */
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

    /**
     * spawn a shield in the world and return the shield entity
     * @return a shield to be spawned in the controller as a JavaFX node
     */
    public TheOneRing addUnequippedTheOneRing(){
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            removeItemByPositionInUnequippedInventoryItems(0);
            this.character.addExperience(10);
            this.character.addGold(5);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }

        TheOneRing theonering = new TheOneRing(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(theonering);
        return theonering;
    }

    /**
     * spawn an anduril in the world and return the sword entity
     * @return an anduril to be spawned in the controller as a JavaFX node
     */
    public Anduril addUnequippedAnduril(){
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            removeItemByPositionInUnequippedInventoryItems(0);
            this.character.addExperience(10);
            this.character.addGold(5);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        Anduril anduril = new Anduril(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(anduril);
        return anduril;
    }

    /**
     * spawn an anduril in the world and return the sword entity
     * @return an anduril to be spawned in the controller as a JavaFX node
     */
    public TreeStump addUnequippedTreeStump(){
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            removeItemByPositionInUnequippedInventoryItems(0);
            this.character.addExperience(10);
            this.character.addGold(5);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        TreeStump treeStump = new TreeStump(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(treeStump);
        return treeStump;
    }

    /**
     * equip an unequipped item
     */
    public Item equipItem(int nodeX, int nodeY, int x, int y) {
        Item item = getUnequippedItemByCoordinates(nodeX, nodeY);
        item.destroy();
        unequippedInventoryItems.remove(item);
        item = item.copyItem(x, y);
        character.equip(item);
        return item;
    }

    /**
     * unequip an equipped item
     */
    public Item unequipItem(int nodeX, int nodeY, int x, int y) {
        Item item = getEquippedItemByCoordinates(nodeX, nodeY);
        item.destroy();
        character.unequip(item);
        item = item.copyItem(x, y);
        addUnequippedItem(item);
        return item;
    }

    public ItemType getUnequippedItemTypeByCoordinates(int x, int y){
        for(Item item : unequippedInventoryItems){
            if(item.getX() == x && item.getY() ==y){
                return item.getItemType();
            }
        }
        return null;
    }

    public CardType getCardTypeByCoordinates(int x, int y){
        for(Card card : cardEntities){
            if(card.getX() == x && card.getY() == y){
                return card.getCardType();
            }
        }
        return null;
    }

    public boolean isOnHeroCastle(int x, int y){
        if(herosCastle.getX() == x && herosCastle.getY() == y) return true;
        return false;
    }

    /**
     * increase character health if character has a health potion
     */
    public void useHealthPotion() {
        for (Item item : this.unequippedInventoryItems) {
            if (item.getItemType() == ItemType.HEALTH_POTION) {
                increaseCharacterHp(HealthPotion.healingHealth);
                removeUnequippedInventoryItemByCoordinates(item.getX(), item.getY());
                return;
            }
        }
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
        character.moveDownPath();
        moveEnemies();
    }

    /**
     * determine if the character is on hero castle
     * @return
     */
    public boolean characterIsOnHeroCastle(){
        if(character.getX() == herosCastle.getX() && character.getY() == herosCastle.getY()){
            return true;
        }
        return false;
    }

    /**
     * Check if inventory is full
     * @return
     */
    public boolean isUnequippedInventoryFull(){
        if(getFirstAvailableSlotForItem() == null) return true;
        return false;
    }

    /**
     * remove an item from the unequipped inventory
     * @param item item to be removed
     */
    public void removeUnequippedInventoryItem(Item item){
        int x = item.getX();
        int y = item.getY();
        item.destroy();
        unequippedInventoryItems.remove(item);
        //shiftUnequippedInventoryItemsFromXYCoordinate(x, y);
    }

    /**
     * Add unequipped item to list
     * @param item unequipped item
     */
    public void addUnequippedItem(Item item){
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
    //DEBUG
    public void removeOverlappedEntityByCoordinates(int x, int y, OverlappableEntityType type){
        Entity result = null;
        if(type == OverlappableEntityType.BUILDING){
            for(Entity e : buildingEntities){
                if(e.getX() == x && e.getY() == y){
                    result = e;
                    break;
                }
            }
            for(Entity e : spawnedItems){
                if(e.getX() == x && e.getY() == y){
                    result = e;
                    break;
                }
            }
            if(result != null){
                //TODO: Should also add removeDebuffFromEnemy, but at this stage not required
                //DEBUG??? MIGHT CAUSE ERROR? ADD FLAG
                if(result instanceof Building){
                    buildingEntities.remove((Building)result);
                }else{
                    spawnedItems.remove((Item)result);
                }
                result.destroy();
                return;
            }
        }
        if(type == OverlappableEntityType.EQUIPPED_ITEM){
            for(Entity e : character.getEquippedItems()){
                if(e.getX() == x && e.getY() == y){
                    result = e;
                    break;
                }
            }
            if(result != null){
                character.unequip((Item)result);
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
                removeUnequippedInventoryItem((Item)result);
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
    public Item getEquippedItemByCoordinates(int x, int y){
        for (Item e: character.getEquippedItems()){
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
    public Item getUnequippedItemByCoordinates(int x, int y){
        for (Item e: unequippedInventoryItems){
            if ((e.getX() == x) && (e.getY() == y)){
                //Redundant, just in case
                if(e.isEquipable()){
                    return e;
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
    public Pair<Integer, Integer> getFirstAvailableSlotForItem(){
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
     * get the first available slot for allied soldier
     * @return
     */
    private int getFirstAvailableSlotForAlliedSoldier(){
        return character.getAlliedSoldiers().size();
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
        }
    }

    /**
     * Get the possible spawn position for an item
     * @return
     */
    private Pair<Integer, Integer> possiblyGetItemSpawnPosition(){
        List<Pair<Integer, Integer>> orderedPathSpawnCandidates = new ArrayList<>();
        int indexPosition = orderedPath.indexOf(new Pair<Integer, Integer>(character.getX(), character.getY()));
        // inclusive start and exclusive end of range of positions not allowed
        int startNotAllowed = (indexPosition - 2 + orderedPath.size())%orderedPath.size();
        int endNotAllowed = (indexPosition + 3)%orderedPath.size();
        // note terminating condition has to be != rather than < since wrap around...
        for (int i=endNotAllowed; i!=startNotAllowed; i=(i+1)%orderedPath.size()){
            // If on hero castle, dont spawn
            if(orderedPath.get(i).getValue0() == herosCastle.getX()
                && orderedPath.get(i).getValue1() == herosCastle.getY()){
                    continue;
            }else{
                // If on other building entities or items, dont spawn
                boolean flag = false;
                for(Building building : buildingEntities){
                    if(orderedPath.get(i).getValue0() == building.getX()
                        && orderedPath.get(i).getValue1() == building.getY()){
                            flag = true;
                            break;
                    }
                }
                //If there is already an enemy on that tile, dont spwan
                for(Enemy enemy : enemies){
                    if(orderedPath.get(i).getValue0() == enemy.getX()
                        && orderedPath.get(i).getValue1() == enemy.getY()){
                            flag = true;
                            break;
                    }
                }
                //If there is already a spawned item on that tile, dont spwan
                for(Item item : spawnedItems){
                    if(orderedPath.get(i).getValue0() == item.getX()
                        && orderedPath.get(i).getValue1() == item.getY()){
                            flag = true;
                            break;
                    }
                }
                if(flag) continue;
            }
            orderedPathSpawnCandidates.add(orderedPath.get(i));
        }

        // choose random choice
        Pair<Integer, Integer> spawnPosition = null;
        if(orderedPathSpawnCandidates.size() != 0){
            spawnPosition = orderedPathSpawnCandidates.get(rand.nextInt(orderedPathSpawnCandidates.size()));
        }
        return spawnPosition;
    }

    /**
     * get a randomly generated position which could be used to spawn a slug
     * @return null if random choice is that wont be spawning an enemy or it isn't possible, or random coordinate pair if should go ahead
     */
    private Pair<Integer, Integer> possiblyGetSlugSpawnPosition(){
        // TODO = modify this

        // has a chance spawning a basic enemy on a tile the character isn't on or immediately before or after (currently space required = 2)...
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
                // If on hero castle, dont spawn
                if(orderedPath.get(i).getValue0() == herosCastle.getX()
                    && orderedPath.get(i).getValue1() == herosCastle.getY()){
                        continue;
                }
                orderedPathSpawnCandidates.add(orderedPath.get(i));
            }

            // choose random choice
            Pair<Integer, Integer> spawnPosition = null;
            if(orderedPathSpawnCandidates.size() != 0){
                spawnPosition = orderedPathSpawnCandidates.get(rand.nextInt(orderedPathSpawnCandidates.size()));
            }
            return spawnPosition;
        }
        return null;
    }

    private Pair<Integer, Integer> possiblyGetBossSpawnPosition(){
        List<Pair<Integer, Integer>> orderedPathSpawnCandidates = new ArrayList<>();
        int indexPosition = orderedPath.indexOf(new Pair<Integer, Integer>(character.getX(), character.getY()));
        // inclusive start and exclusive end of range of positions not allowed
        int startNotAllowed = (indexPosition - 2 + orderedPath.size())%orderedPath.size();
        int endNotAllowed = (indexPosition + 3)%orderedPath.size();
        // note terminating condition has to be != rather than < since wrap around...
        for (int i=endNotAllowed; i!=startNotAllowed; i=(i+1)%orderedPath.size()){
            // If on hero castle, dont spawn
            if(orderedPath.get(i).getValue0() == herosCastle.getX()
                && orderedPath.get(i).getValue1() == herosCastle.getY()){
                    continue;
            }
            orderedPathSpawnCandidates.add(orderedPath.get(i));
        }

        // choose random choice
        Pair<Integer, Integer> spawnPosition = null;
        if(orderedPathSpawnCandidates.size() != 0){
            spawnPosition = orderedPathSpawnCandidates.get(rand.nextInt(orderedPathSpawnCandidates.size()));
        }
        return spawnPosition;
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
                List<Pair<Integer, Integer>> spawningTiles = ((Spawner)b).getSpawningTiles();
                for(Pair<Integer, Integer> i : spawningTiles){
                    if(i.getValue0() == character.getX() && i.getValue1() == character.getY()){
                        spawningTiles.remove(i);
                    }
                }
                if(spawningTiles.size() == 0) continue;
                result.add(spawningTiles.get(rand.nextInt(spawningTiles.size())));
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

    //added for testing
    public Building spawnBuilding(CardType cardType, int buildingNodeX, int buildingNodeY){
        Building newBuilding = BuildingLoader.loadBuilding(cardType, buildingNodeX, buildingNodeY);
        buildingEntities.add(newBuilding);

        return newBuilding;
   }

    public void applyStaticBuildingBuffsToCharacter(){
        List<Building> buffingBuildings = getBuildingsWithinRadiusOfEntity(character);
        for(Building b : buffingBuildings){
            switch(b.getBuildingType()){
                case VILLAGE_BUILDING:
                    character.addHealth(VillageBuilding.healValue);
                    break;
                case BARRACKS_BUILDING:
                    character.addAlliedSoldier();
                    break;
                default:
                    continue;
            }
        }
    }

    public void applyTrapsToEnemies(){
        List<Enemy> enemiesToBeRemoved = new ArrayList<>();
        for(Enemy enemy : enemies){
            List<Building> buildingsInRadius = getBuildingsWithinRadiusOfEntity(enemy);
            for(Building b : buildingsInRadius){
                if(b.getBuildingType() == BuildingType.TRAP_BUILDING){
                    b.destroy();
                    enemy.takeDamage(TrapBuilding.attack, character);
                    activateTrapAudioPlayer.play();
                    activateTrapAudioPlayer.seek(Duration.ZERO);
                    buildingEntities.remove(b);
                    if(enemy.isDefeated()){
                        enemiesToBeRemoved.add(enemy);
                        break;
                    }
                }
            }
        }
        for(Enemy e : enemiesToBeRemoved){
            killEnemy(e);
        }
    }

    /**
     * Perform actions before heading to next cycle
     */
    public void nextCycle(){
        this.cycle++;
        // For testing
        if(controller != null){
            controller.updateCycle();
            notifyHumanPlayer();
        }
        /* Reset all spawner spawning behaviour */
        /* Kind of observer pattern (DEBUG) */
        for(Building b : buildingEntities){
            if(b instanceof Spawner){
                ((Spawner)b).setHasSpawned(false);
            }
        }
    }

    public int getCycle(){
        return this.cycle;
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
        this.character.minusHealth(amount);
    }

    public double getCharacterHP(){
        return this.character.getCurrentHealth();
    }

    //use for battle calculation
    //check at the start of each battle to determine
    //character atk
    public Boolean inRangeOfCampfire(Entity e){
        for (Building b : getBuildingsWithinRadiusOfEntity(e)) {
            if(b.getBuildingType()==BuildingType.CAMPFIRE_BUILDING){
                return true;
            }
        }
        return false;
    }

    //use for battle calculation to determine dmg to enemies
    public Boolean inRangeOfTower(Entity e){
        for (Building b : getBuildingsWithinRadiusOfEntity(e)) {
            if(b.getBuildingType()==BuildingType.TOWER_BUILDING){
                return true;
            }
        }
        return false;
    }

    //for testing trap
    public Enemy getFirstEnemy(){
        return enemies.get(0);
    }
    public Building getFirstB(){
        return buildingEntities.get(0);
    }
    public boolean enemiesAlive(){
        return !enemies.isEmpty();
    }
    public boolean trapsCleared(){
        for (Building b : buildingEntities) {
            if((b.getBuildingType() == BuildingType.TRAP_BUILDING)){
                return false;
            }
        }
        return true;
    }

    //for testing tower
    public boolean towerUsed(){
        return toweractivated;
    }

    public List<Item> getUnequippedInventoryItems(){
        return this.unequippedInventoryItems;
    }
    /**
     * Add item to uequipped inventory when encountered
     */
    public Item checkAndAddSpawnedItem(){
        Item newItem = null;
        Item itemToBeDestroyed = null;
        for(Item item : spawnedItems){
            if(item.getX() == character.getX() && item.getY() == character.getY()){
                itemToBeDestroyed = item;
                //Assume no 2 spawned items on the same tile
                Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
                if (firstAvailableSlot == null){
                    newItem = ItemLoader.loadSpawnableItems(item.getItemType(), unequippedInventoryItems.get(0).getX(), unequippedInventoryItems.get(0).getY());
                    if(item.getItemType() == ItemType.GOLD){
                        character.addGold(5);
                        moneyPickupAudioPlayer.play();
                        moneyPickupAudioPlayer.seek(Duration.ZERO);
                        newItem = null;
                        break;
                    }
                    removeItemByPositionInUnequippedInventoryItems(0);
                    this.character.addExperience(10);
                    addUnequippedItem(newItem);
                    break;
                }else{
                    newItem = ItemLoader.loadSpawnableItems(item.getItemType(), firstAvailableSlot.getValue0(), firstAvailableSlot.getValue1());
                    if(item.getItemType() == ItemType.GOLD){
                        character.addGold(5);
                        moneyPickupAudioPlayer.play();
                        moneyPickupAudioPlayer.seek(Duration.ZERO);
                        newItem = null;
                        break;
                    }
                    addUnequippedItem(newItem);
                    break;
                }
            }
        }
        if(itemToBeDestroyed == null) return null;
        itemToBeDestroyed.destroy();
        spawnedItems.remove(itemToBeDestroyed);
        return newItem;
    }

    public void applyTowerDamageToEnemy(Enemy enemy){
        for(Building b : getBuildingsWithinRadiusOfEntity(enemy)){
            if(b.getBuildingType() == BuildingType.TOWER_BUILDING){
                enemy.takeDamage(TowerBuilding.attack, character);
                toweractivated = true; // For testing
            }
        }
    }

    public void applyCampfireBuffToCharacter(){
        for(Building b : getBuildingsWithinRadiusOfEntity(character)){
            if(b.getBuildingType() == BuildingType.CAMPFIRE_BUILDING){
                character.setAttack(character.getAttack()*2);
            }
        }
    }

    private void removeCampfireBuffFromCharacter(){
        for(Building b : getBuildingsWithinRadiusOfEntity(character)){
            if(b.getBuildingType() == BuildingType.CAMPFIRE_BUILDING){
                character.setAttack(character.getAttack()/2);
            }
        }
    }

}




