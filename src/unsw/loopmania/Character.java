package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;

import unsw.loopmania.Items.DefensiveItems;
import unsw.loopmania.Items.Item;
import unsw.loopmania.Items.OffensiveItems;
import unsw.loopmania.Items.Staff;
import unsw.loopmania.Types.DamageableType;
import unsw.loopmania.Types.EnemyType;
import unsw.loopmania.Types.ItemType;

/**
 * represents the main character in the backend of the game world
 */
public class Character extends MovingEntity implements Damageable{

    private final double maxHealth = 100;
    private double currentHealth;
    private int attack;
    private int defense;
    private int gold;
    private int experience;
    private LoopManiaWorldController observer;
    private LoopManiaWorld world;

    /**
     * List of allied soldiers
     */
    private List<AlliedSoldier> alliedSoldiers;

    /**
     * // List of equipped items
     */
    private List<Item> equippedItems;

    public Character(PathPosition position) {
        super(position);
        this.currentHealth = this.maxHealth;
        this.gold = 0;
        this.experience = 0;
        this.attack = 5;
        this.defense = 0;
        alliedSoldiers = new ArrayList<>();
        equippedItems = new ArrayList<>();
        this.observer = null;
        this.world = null;
    }

    /**
     * Set the observer of the character
     * @param observer
     */
    public void setObserver(LoopManiaWorldController observer){
        this.observer = observer;
    }

    public PathPosition getPathPosition(){
        return super.getPathPosition();
    }

    public double getCurrentHealth(){
        return this.currentHealth;
    }

    public double getMaxHealth(){
        return this.maxHealth;
    }

    public int getGold() {
        return this.gold;
    }

    public int getExperience() {
        return this.experience;
    }

    public List<AlliedSoldier> getAlliedSoldiers(){
        return this.alliedSoldiers;
    }

    public void setWorld(LoopManiaWorld world){
        this.world = world;
    }

    public int getAttack() {
        return this.attack;
    }

    public int getDefense() {
        return this.defense;
    }

    public void setCurrentHealth(double currentHealth){
        this.currentHealth = currentHealth;
    }

    public List<Item> getEquippedItems(){
        return this.equippedItems;
    }

    /**
     * add allied soldier
     */
    public AlliedSoldier addAlliedSoldier(){
        AlliedSoldier alliedSoldier = observer.addAlliedSoldier();
        if(alliedSoldier != null){
            alliedSoldiers.add(alliedSoldier);
        }
        return alliedSoldier;
    }

    /**
     * remove allied soldier
     */
    public void removeAlliedSoldier(AlliedSoldier alliedSoldier){
        alliedSoldiers.remove(alliedSoldier);
        alliedSoldier.destroy();
        observer.removeAlliedSoldier();
    }

    /**
     * add gold to character
     * @param amount amount too be added
     */
    public boolean addGold(int amount){
        if (this.gold == Integer.MAX_VALUE) {
            return false;
        } else if (Long.valueOf(this.gold) + Long.valueOf(amount) >= Integer.MAX_VALUE) {
            this.gold = Integer.MAX_VALUE;
            if (observer != null) {
                observer.updateGold();
                world.notifyHumanPlayer();
            }
            return true;
        } else {
            this.gold += amount;
            if (observer != null) {
                observer.updateGold();
                world.notifyHumanPlayer();
            }
            return true;
        }
    }

    /**
     * decrease gold to character
     * @param amount amount too be decrease
     */
    public boolean minusGold(int amount){
        if (this.gold == 0) {
            return false;
        } else if (this.gold - amount < 0) {
            return false;
        } else {
            this.gold -= amount;
            if (observer != null) {
                observer.updateGold();
            }
            return true;
        }
    }

    /**
     * add experience to character
     * @param amount amount too be added
     */
    public boolean addExperience(int amount){
        if (this.experience == Integer.MAX_VALUE) {
            return false;
        } else if (Long.valueOf(this.experience) + Long.valueOf(amount) >= Integer.MAX_VALUE) {
            this.experience = Integer.MAX_VALUE;
            if (observer != null) {
                observer.updateExperience();
                world.notifyHumanPlayer();
            }
            return true;
        } else {
            this.experience += amount;
            if (observer != null) {
                observer.updateExperience();
                world.notifyHumanPlayer();
            }
            return true;
        }
    }

    /**
     * decrease hp based on damage taken by character
     * @param damage amount of hp too be decreased
     */
    @Override
    public void takeDamage(double damage, Damageable from) {
        if(from.getDamageableType() == DamageableType.VAMPIRE
            && hasShieldEquipped() && damage > (double)EnemyType.VAMPIRE.getAttack()){
            damage = LoopManiaWorld.rand.nextDouble() < 0.6 ? EnemyType.VAMPIRE.getAttack() : damage;
        }else if((from.getDamageableType() == DamageableType.DOGGIE 
                || from.getDamageableType() == DamageableType.ELAN_MUSKE)
                    && hasTreeStumpEquipped()){
            damage = damage*0.7;
        }
        if(hasArmourEquipped()){
            damage = damage/2;
        }
        damage = (damage - defense <= 0 ? 0 : damage - defense);
        minusHealth(damage);
    }

    /**
     * deal damage too an damageable entity
     * @param damageable entity to be damaged
     */
    public void dealDamage(Damageable damageable) {
        System.out.println("Current Attack: " + attack);
        if(damageable.getDamageableType() == DamageableType.VAMPIRE && hasStakeEquipped()){
            damageable.takeDamage(attack*2, this);
        }else if((damageable.getDamageableType() == DamageableType.DOGGIE
                || damageable.getDamageableType() == DamageableType.ELAN_MUSKE)
                    && hasAndurilEquipped()){
            damageable.takeDamage(attack*3, this);
        }else{
            damageable.takeDamage(attack, this);
        }
    }

    public boolean isNextAttackTrance(){
        if(hasStaffEquipped()){
            return LoopManiaWorld.rand.nextDouble() < Staff.tranceChance ? true : false;
        }
        return false;
    }

    public DamageableType getDamageableType(){
        return DamageableType.CHARACTER;
    }

    public boolean isDefeated(){
        return this.currentHealth <= 0 ? true : false;
    }

    private boolean hasStakeEquipped(){
        for(Item item : equippedItems){
            if(item.getItemType() == ItemType.STAKE) return true;
        }
        return false;
    }

    private boolean hasStaffEquipped(){
        for(Item item : equippedItems){
            if(item.getItemType() == ItemType.STAFF) return true;
        }
        return false;
    }

    private boolean hasArmourEquipped(){
        for(Item item : equippedItems){
            if(item.getItemType() == ItemType.ARMOUR) return true;
        }
        return false;
    }

    private boolean hasShieldEquipped(){
        for(Item item : equippedItems){
            if(item.getItemType() == ItemType.SHIELD) return true;
        }
        return false;
    }

    private boolean hasAndurilEquipped(){
        for(Item item : equippedItems){
            if(item.getItemType() == ItemType.ANDURIL) return true;
        }
        return false;
    }

    private boolean hasTreeStumpEquipped(){
        for(Item item : equippedItems){
            if(item.getItemType() == ItemType.TREE_STUMP) return true;
        }
        return false;
    }

    /**
     * add health to current health of character
     * @param health amount of health to be added
     */
    public void addHealth(double health){
        currentHealth = currentHealth + health;
        if(currentHealth >= maxHealth){
            currentHealth = maxHealth;
        }
        if (observer != null) {
            observer.updateHealth(this.currentHealth, this.maxHealth);
        }
    }

    /**
     * decrease health of current health of character
     * @param health amount of health to be deacreased
     */
    public void minusHealth(double health){
        if(currentHealth == 0) return;
        currentHealth = currentHealth - health;
        if(currentHealth <= 0){
            currentHealth = 0;
            /* Apply effect of the one ring */
            Item destroyedRareItem = null;
            for(Item i : world.getUnequippedInventoryItems()){
                if(i.getItemType() == ItemType.THE_ONE_RING){
                    currentHealth = maxHealth;
                    destroyedRareItem = i;
                    break;
                }
            }

            if (observer != null) {
                observer.updateHealth(this.currentHealth, this.maxHealth);
            }

            if(destroyedRareItem != null){
                world.removeUnequippedInventoryItem(destroyedRareItem);
            }else{
                observer.displayDefeatMessage();
            }
        }else{
            if (observer != null) {
                observer.updateHealth(this.currentHealth, this.maxHealth);
            }
        }
    }

    public void setAttack(int attack){
        this.attack = attack;
    }

    public void setDefense(int defense){
        this.defense = defense;
    }

    /**
     * equip the character with an equipable item
     * @param item equipable item to be equipped
     */
    public void equip(Item item){
        if(item.isOffensive()){
            setAttack(attack + ((OffensiveItems)item).getAttack());
        }else if(item.isDefensive()){
            setDefense(defense + ((DefensiveItems)item).getDefense());
        }
        equippedItems.add(item);
    }

    /**
     * unequip item from character
     * @param item item to be unequipped
     */
    public void unequip(Item item){
        if(item.isOffensive()){
            setAttack(attack - ((OffensiveItems)item).getAttack());
        }else if(item.isDefensive()){
            setDefense(defense - ((DefensiveItems)item).getDefense());
        }
        equippedItems.remove(item);
    }

    
}
